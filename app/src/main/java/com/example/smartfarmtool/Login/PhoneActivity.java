package com.example.smartfarmtool.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartfarmtool.NavigationDMenu;
import com.example.smartfarmtool.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import static com.example.smartfarmtool.R.id.logout;
import static com.example.smartfarmtool.R.id.phone_next_btn_id;
public class PhoneActivity extends AppCompatActivity {

    private static final String TAG = "PhoneLogin";
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistner;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    TextView phn, otp ;
    Button phn_btn, otp_btn;
    ProgressBar progressBar,progressBar2;
    String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        final LinearLayout phn_lyt;
        final LinearLayout otp_lyt;

        phn_lyt = findViewById(R.id.phone_layout);
        otp_lyt = findViewById(R.id.otp_layout);
        phn = findViewById(R.id.enter_phone_id);
        phn_btn = findViewById(phone_next_btn_id);
        progressBar = findViewById(R.id.progress_id);
        progressBar2=findViewById(R.id.progress_id2);
        otp = findViewById(R.id.otp_id);
        otp_btn = findViewById(R.id.otp_continue);

         phn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = "+91";
                String number = phn.getText().toString();
                phoneNumber = code + number;
                if (phn.getText().toString().isEmpty()) {
                    phn.setError("Phone numbe required :");
                    phn.requestFocus();
                    return;
                }
                progressBar2.setVisibility(View.VISIBLE);
                getOtp(phn.getText().toString());


            }
        });

        otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = otp.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    otp.setError("Cannot be empty.");
                    return;
                }
                verifyPhoneNumberWithCode(mVerificationId, code);
                progressBar.setVisibility(View.VISIBLE);

            }
        });



        mAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d("", "onVerificationCompleted:" + credential);
                String code = credential.getSmsCode();
                if (code != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    otp.setText(code);
                    signInWithPhoneAuthCredential(credential);

                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w("", "onVerificationFailed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    phn.setError("Invalid phone number.");
                    progressBar2.setVisibility(View.GONE);
                } else if (e instanceof FirebaseTooManyRequestsException) {

                }
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                Log.d("", "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;
                phn_lyt.setVisibility(View.GONE);
                progressBar2.setVisibility(View.GONE);
                otp_lyt.setVisibility(View.VISIBLE);
            }
        };

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:success");
                            if (task.isSuccessful()) {

                                DatabaseReference UserDb = FirebaseDatabase.getInstance().getReference().child("Farmer");
                               // Toast.makeText(PhoneActivity.this,UserDb.orderByChild("Fphone").toString(),Toast.LENGTH_SHORT).show();
                                UserDb.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        int  flag=0;
                                      // Log.d("Datasnapshot", dataSnapshot.getValue().toString());
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            Log.d("Datasnapshot", snapshot.child("Fphone").getValue().toString());

                                            if (snapshot.child("Fphone").getValue().toString().equals(phoneNumber)){
//                                              Toast.makeText(PhoneActivity.this,snapshot.child("Fphone").getValue().toString(),Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(PhoneActivity.this, NavigationDMenu.class)
                                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                                flag=1;
                                                finish();
                                            }
                                        }

                                        if (flag==0){
                                            startActivity(new Intent(PhoneActivity.this, RegisterAcitivity.class)
                                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                                    .putExtra("phoneNumber", phoneNumber));
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            else {


                            }


                    }
                });
    }

    private void getOtp(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(PhoneActivity.this, NavigationDMenu.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }

    }
}