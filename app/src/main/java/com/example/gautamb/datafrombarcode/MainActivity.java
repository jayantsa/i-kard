package com.example.gautamb.datafrombarcode;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private Button scanBtn;
    private TextView formatTxt, contentTxt;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    // Create the object of JsonParser class

    EditText inputName;
    TextView inputId;
    EditText price;

    // url to create send data. This contains the ip address of my machine on which the local server is running. You will write the IP address of your machine
    private static String url = "http://192.168.1.9:80/barcode/data.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanBtn = (Button)findViewById(R.id.scan_button);
        inputId = (TextView) findViewById(R.id.inputId);
        scanBtn.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.scan_button){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            inputId.setText(scanContent);
            Log.d("++++", scanContent);
            getData(scanContent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void getData(String v1){
        //Log.d("_____",v1);
        StrictMode.setThreadPolicy(policy);
        String result = null;
        InputStream is = null;
        TextView editText1 = (TextView)findViewById(R.id.e2);

        TextView editText2 = (TextView)findViewById(R.id.e3);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("barcode_id",v1));
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://collegeguide.online/iciciappathon/barcode/select.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

            Log.e("log_tag", "connection success ");
            Log.d("____", v1);

        }
        catch(Exception e)
        {
            Log.e("log_tag", "Error in http connection "+e.toString());
            Toast.makeText(getApplicationContext(), "Connection fail", Toast.LENGTH_SHORT).show();

        }
        //convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");

            }
            is.close();

            result=sb.toString();
        }
        catch(Exception e)
        {
            Log.e("log_tag", "Error converting result "+e.toString());

            Toast.makeText(getApplicationContext(), " Input reading fail", Toast.LENGTH_SHORT).show();

        }

        //parse json data
        try{

            Log.d("result:",result);
            JSONObject object = new JSONObject(result);
            String ch=object.getString("re");
            if(ch.equals("success"))
            {

                JSONObject no = object.getJSONObject("0");

                //long q=object.getLong("f1");
                String w= no.getString("product_name");
                long e=no.getLong("price");

                editText1.setText(w);
                String myString = NumberFormat.getInstance().format(e);


                editText2.setText("Rs."+myString);

            }


            else
            {

                Toast.makeText(getApplicationContext(), "Record is not available.. Enter valid number", Toast.LENGTH_SHORT).show();

            }


        }
        catch(JSONException e)
        {
            Log.e("log_tag", "Error parsing data "+e.toString());
            Toast.makeText(getApplicationContext(), "JsonArray fail", Toast.LENGTH_SHORT).show();
        }



        }

}
