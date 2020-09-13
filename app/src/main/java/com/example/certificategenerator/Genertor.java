package com.example.certificategenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class Genertor extends AppCompatActivity {

    EditText name,email,mobile,domain,date,duration;
    Button generate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genertor);

        name=findViewById(R.id.name);
        mobile=findViewById(R.id.mobile);
        email=findViewById(R.id.email);
        domain=findViewById(R.id.domain);
        date=findViewById(R.id.date);
        generate=findViewById(R.id.generate);
        duration=findViewById(R.id.duration);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dp =
                        new DatePickerDialog(Genertor.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker,
                                                  int year, int month, int day) {

                                date.setText(day+"/"+(month+1)+"/"+year);
                            }
                        }, 2020 , 8 , 1);

                dp.show();
            }

        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Genertor.this,Cert.class);
                intent.putExtra("name",name.getText().toString());

                intent.putExtra("mobile",mobile.getText().toString());
                intent.putExtra("email",email.getText().toString());
                intent.putExtra("domain",domain.getText().toString());
                intent.putExtra("date",date.getText().toString());
                intent.putExtra("duration",duration.getText().toString());
                startActivity(intent);
            }
        });
    }
}
