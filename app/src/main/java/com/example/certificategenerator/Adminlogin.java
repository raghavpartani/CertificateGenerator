package com.example.certificategenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Adminlogin extends AppCompatActivity {

    EditText username,password;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        username=findViewById(R.id.username);

        password=findViewById(R.id.password);
        login=findViewById(R.id.button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("admin")&&password.getText().toString().equals("admin"))
                {
                    Toast.makeText(Adminlogin.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Adminlogin.this,Admin.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(Adminlogin.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
