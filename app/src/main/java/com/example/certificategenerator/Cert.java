package com.example.certificategenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.core.widget.NestedScrollView;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class Cert extends AppCompatActivity {

    ConstraintLayout rcv;
    TextView name,domain,date;
    ImageView download;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cert);

        name = findViewById(R.id.name);
        domain = findViewById(R.id.domain);
        date = findViewById(R.id.date);

        rcv=findViewById(R.id.scrool);
        download = findViewById(R.id.download);

        Intent intent = getIntent();
        final String name1 = intent.getStringExtra("name");
        String domain1 = intent.getStringExtra("domain");
        String duration1 = intent.getStringExtra("duration");
        String email1 = intent.getStringExtra("email");
        String mobile1 = intent.getStringExtra("mobile");
        String date1 = intent.getStringExtra("date");

        name.setText(name1);
        domain.setText(domain1);
        date.setText(date1);

        String url="http://192.168.43.68:8080/Cert/insert.jsp?name="+name1+"&email="+email1+"&domain="+domain1+"&duration="+duration1+"&date="+date1+"&mobile="+mobile1+"";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(Cert.this, "Certificate generated successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Cert.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(Cert.this);
        requestQueue.add(stringRequest);


        //Toast.makeText(this, ""+url, Toast.LENGTH_SHORT).show();
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(Cert.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PermissionChecker.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(Cert.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
                }
                Log.d("size"," "+rcv.getWidth() +"  "+rcv.getWidth());
                bitmap = loadBitmapFromView(rcv, rcv.getWidth(), rcv.getHeight());
                createPdf();

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

        // write the document content
        String targetPdf = "/storage/emulated/0/Download/certification.pdf";
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
        Toast.makeText(this, "PDF is downloaded", Toast.LENGTH_SHORT).show();



    }
}



