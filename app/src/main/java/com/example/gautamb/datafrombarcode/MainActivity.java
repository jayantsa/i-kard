package com.example.gautamb.datafrombarcode;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private Button scanBtn;
    public static int flag=0;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();


    TextView inputId;
    Button addbtn;
    Button cancelbtn;
    TextView editText1;
    TextView editText2;
    Button viewCart;
    public static long total_cost = 0;
    public static ArrayList<String> product_list = new ArrayList<>();
    public static ArrayList<String> amount = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String scanIntent;
        scanBtn = (Button)findViewById(R.id.scan_button);
        inputId = (TextView) findViewById(R.id.inputId);
        addbtn= (Button) findViewById(R.id.add_button);
        cancelbtn= (Button) findViewById(R.id.cancel_button);
        editText1 = (TextView)findViewById(R.id.e2);
        editText2 = (TextView)findViewById(R.id.e3);
        viewCart = (Button) findViewById(R.id.view_cart);
        Intent scn = getIntent();
        scanIntent = scn.getStringExtra("back_intent");
        if(Objects.equals(scanIntent, "1")){
            flag = flag - 1;
        }
        if(flag==0) {
            addbtn.setVisibility(View.GONE);
            cancelbtn.setVisibility(View.GONE);
        }
        else if(flag==1)
        {
            scanBtn.setVisibility(View.GONE);
        }
        scanBtn.setOnClickListener(this);
        cancelbtn.setOnClickListener(this);
        Log.d("scanintent", scanIntent + "----" + flag);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(getApplicationContext(), AddProducts.class);
                add.putExtra("total_cost", String.valueOf(total_cost));
                add.putExtra("amount", amount);
                add.putExtra("list", product_list);
                Log.d("____", total_cost + "====" + product_list);
                startActivity(add);
            }
        });

        viewCart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(getApplicationContext(), AddProducts.class);
                cart.putExtra("total_cost", String.valueOf(total_cost));
                cart.putExtra("amount",amount);
                cart.putExtra("list", product_list);
                startActivity(cart);
            }
        });
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
        if(v.getId()== R.id.scan_button){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
        else if(v.getId() == R.id.cancel_button){
            scanBtn.setVisibility(View.VISIBLE);
            addbtn.setVisibility(View.GONE);
            cancelbtn.setVisibility(View.GONE);
            editText1.setText(null);
            editText2.setText(null);
            inputId.setText(null);
            flag = flag - 1;
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

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

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
                product_list.add(w);
                amount.add(myString);
                total_cost = total_cost + e;
                flag = flag + 1;
                if(flag==1) {
                    addbtn.setVisibility(View.VISIBLE);
                    cancelbtn.setVisibility(View.VISIBLE);
                }

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
