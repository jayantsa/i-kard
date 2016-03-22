package com.example.gautamb.datafrombarcode;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by JAYANT on 22-03-16.
 */
public class PaymentAsyncClass  extends AsyncTask<String,Void,String>{


    PaymentInterface listener;
    ProgressDialog progress;
    Context context;
    public PaymentAsyncClass(Context context)
    {
        this.context=context;
    }
    @Override
    protected String doInBackground(String... params) {
        String urlString=params[0];
        Log.i("st",urlString);
        try {
            Log.i("h","h");
            URL url=new URL(urlString);
            HttpsURLConnection urlConnection=(HttpsURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream data=urlConnection.getInputStream();
            Scanner s=new Scanner(data);
            StringBuffer output=new StringBuffer();
            while(s.hasNext())
            {
                output.append(s.nextLine());
            }
            s.close();
            urlConnection.disconnect();
            Log.i("out",output.toString());
            return parsePaymentInfoJson(output.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPreExecute() {

        progress=new ProgressDialog(context);
        progress.setTitle("Payment in progress!!!");
        progress.setMessage("Wait!");
        progress.show();

    }

    @Override
    protected void onPostExecute(String s) {
        if(listener!=null)
        {
            listener.PaymentTaskOnComplete(s,progress);

        }
    }

    private String parsePaymentInfoJson(String s) {

        String answer="";
        JSONArray js= null;
        try {
            js = new JSONArray(s);
            JSONObject scode=js.getJSONObject(0);
            int x=scode.getInt("code");
            if(x==200)
            {
                JSONObject pinfoJsonObject=js.getJSONObject(1);
                PaymentInfo pinfo=new PaymentInfo();
                pinfo.destination_accountno=pinfoJsonObject.getString("destination_accountno");
                pinfo.transaction_date=pinfoJsonObject.getString("transaction_date");
                pinfo.reference_no=Integer.parseInt(pinfoJsonObject.getString("referance_no"));
                pinfo.Transaction_amount=Double.parseDouble(pinfoJsonObject.getString("transaction_amount"));
                pinfo.Payee_name=pinfoJsonObject.getString("payee_name");
                pinfo.payee_id=Integer.parseInt(pinfoJsonObject.getString("payee_id"));
                pinfo.status=pinfoJsonObject.getString("status");
                answer+="destination_accountno:"+pinfo.destination_accountno+" ";
                answer+="transaction_date:"+pinfo.transaction_date+" ";
                answer+="reference_no:"+pinfo.reference_no+" ";
                answer+="Transaction_amount:"+pinfo.Transaction_amount+" ";
                answer+="Payee_name:"+pinfo.Payee_name+" ";
                answer+="payee_id:"+pinfo.payee_id+" ";
                answer+="status:"+pinfo.status;

            }
            Log.i("answer",answer);

         return answer;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
