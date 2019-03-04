package com.example.smartfarmtool.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartfarmtool.HomeActivity;
import com.example.smartfarmtool.R;

public class Login_Signup extends AppCompatActivity {

    Button login;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__signup);
      

        TextView txt=findViewById(R.id.login_signup_button_id);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login_Signup.this, PhoneActivity.class);
                startActivity(intent);
            }
        });

    }
}
