package com.example.certificategenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AdminCert extends AppCompatActivity {

    String mobile;String name;String domain;String duration;String date;String email;
    int id1;
    Bitmap bitmap;
    ConstraintLayout rcv;
    TextView name1,domain1,date1;
    ImageView sendemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cert);

        final Intent intent=getIntent();
        String id=intent.getStringExtra("id");
        name1 = findViewById(R.id.name);
        domain1 = findViewById(R.id.domain);
        rcv=findViewById(R.id.scrool);
        date1 = findViewById(R.id.date);
        sendemail=findViewById(R.id.sendemail);
        String url="http://192.168.43.68:8080/Cert/one.jsp?id="+id;

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                    //        Toast.makeText(AdminCert.this, ""+response, Toast.LENGTH_SHORT).show();
                                JSONObject jsonObject = (JSONObject) response.get(0);
                                id1 = jsonObject.getInt("id");
                            name = jsonObject.getString("name");

                            mobile = jsonObject.getString("mobile");

                            domain = jsonObject.getString("domain");

                            duration = jsonObject.getString("duration");

                            date = jsonObject.getString("date");

                            email = jsonObject.getString("email");

                            name1.setText(name);
                            date1.setText(date);
                            domain1.setText(domain);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminCert.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(AdminCert.this);
        requestQueue.add(jsonArrayRequest);

        ImageView d=findViewById(R.id.download);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(AdminCert.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PermissionChecker.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(AdminCert.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
                }
                Log.d("size"," "+rcv.getWidth() +"  "+rcv.getWidth());
                bitmap = loadBitmapFromView(rcv, rcv.getWidth(), rcv.getHeight());
                createPdf();

            }
        });

        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(AdminCert.this, ""+email, Toast.LENGTH_SHORT).show();
                send(email);
            }

            private void send(String email) {

                String[] TO = {""+email};

                Intent intent1=new Intent(Intent.ACTION_SEND);
                intent1.setData(Uri.parse("mailto:"));
                intent1.setType("text/plain");
                intent1.putExtra(Intent.EXTRA_EMAIL,TO);

                intent1.setType("message/rfc882");
                startActivity(Intent.createChooser(intent1,"choose gmail client"));

            }
        });
    }
    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        String string="/storage/emulated/0/Download/cert"+id1+".pdf";
        // write the document content
        String targetPdf = string;
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF is downloaded!!!", Toast.LENGTH_SHORT).show();

        //openGeneratedPDF();

    }



    }


