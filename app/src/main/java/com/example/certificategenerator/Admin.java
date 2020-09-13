package com.example.certificategenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {
ListView lst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        lst=findViewById(R.id.lst);
        final ArrayList<Stud>arrayList=new ArrayList<>();

        String url="http://192.168.43.68:8080/Cert/ShowAll.jsp";

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i=0;i<response.length();i++) {
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("name");
                                Stud stud = new Stud();
                                stud.setId(id);
                                stud.setName(name);
                                arrayList.add(stud);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ArrayAdapter<Stud> a=new ArrayAdapter<>(Admin.this,android.R.layout.simple_list_item_1,arrayList);
                        lst.setAdapter(a);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Admin.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(Admin.this);
        requestQueue.add(jsonArrayRequest);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                Intent intent=new Intent(Admin.this,AdminCert.class);
                intent.putExtra("id",tv.getText().toString());
                startActivity(intent);
            }
        });
    }

}


