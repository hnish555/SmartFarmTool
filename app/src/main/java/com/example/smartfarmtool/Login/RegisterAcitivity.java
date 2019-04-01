package com.example.smartfarmtool.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.smartfarmtool.NavigationDMenu;
import com.example.smartfarmtool.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterAcitivity extends AppCompatActivity {

    EditText fname;
    TextView fphone;
    Button fButton;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_acitivity);


        mAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Farmer");

        fname=findViewById(R.id.fname_id);
        fphone=findViewById(R.id.fphone_id);
        fButton=findViewById(R.id.fenter_id);

        final String phoneNumber=getIntent().getStringExtra("phoneNumber");
        final String Fname=getIntent().getStringExtra("Fname");
        fname.setText(Fname);
        fphone.setText(phoneNumber);
        fphone.setKeyListener(null);

        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=fname.getText().toString();
                String userid=mAuth.getCurrentUser().getUid();

                DatabaseReference currentUserDb=databaseReference.child(userid);
                currentUserDb.child("Fphone").setValue(phoneNumber);
                currentUserDb.child("Fname").setValue(name);
                startActivity(new Intent(RegisterAcitivity.this, NavigationDMenu.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        });

    }
}
