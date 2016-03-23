package com.android4dev.navigationview;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by gautamb on 3/23/2016.
 */
public class PaymentAsyncClass  extends AsyncTask<String,Void,String> {


    PaymentInterface listener;
    ProgressDialog progress;
    Context context;
    String list;

    public PaymentAsyncClass(Context context, String list) {
        this.context = context;
        this.list = list;
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];
        Log.i("st", urlString);
        try {
            Log.i("h", "h");
            URL url = new URL(urlString);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream data = urlConnection.getInputStream();
            Scanner s = new Scanner(data);
            StringBuffer output = new StringBuffer();
            while (s.hasNext()) {
                output.append(s.nextLine());
            }
            s.close();
            urlConnection.disconnect();
            Log.i("out", output.toString());
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

        progress = new ProgressDialog(context);
        progress.setTitle("Payment in progress!!!");
        progress.setMessage("Wait!");
        progress.show();

    }

    @Override
    protected void onPostExecute(String s) {
        if (listener != null) {
            //progress.dismiss();
            listener.PaymentTaskOnComplete(s, progress);

        }
    }

    private String parsePaymentInfoJson(String s) {

        String answer = "";
        JSONArray js = null;
        try {
            js = new JSONArray(s);
            JSONObject scode = js.getJSONObject(0);
            int x = scode.getInt("code");
            if (x == 200) {
                JSONObject pinfoJsonObject = js.getJSONObject(1);
                PaymentInfo pinfo = new PaymentInfo();
                pinfo.destination_accountno = pinfoJsonObject.getString("destination_accountno");
                pinfo.transaction_date = pinfoJsonObject.getString("transaction_date");
                pinfo.reference_no = Integer.parseInt(pinfoJsonObject.getString("referance_no"));
                pinfo.Transaction_amount = Double.parseDouble(pinfoJsonObject.getString("transaction_amount"));
                pinfo.Payee_name = pinfoJsonObject.getString("payee_name");
                pinfo.payee_id = Integer.parseInt(pinfoJsonObject.getString("payee_id"));
                pinfo.status = pinfoJsonObject.getString("status");
                answer += "destination_accountno:" + pinfo.destination_accountno + " ";
                answer += "transaction_date:" + pinfo.transaction_date + " ";
                answer += "reference_no:" + pinfo.reference_no + " ";
                answer += "Transaction_amount:" + pinfo.Transaction_amount + " ";
                answer += "Payee_name:" + pinfo.Payee_name + " ";
                answer += "payee_id:" + pinfo.payee_id + " ";
                answer += "status:" + pinfo.status;
                String billSave = "http://collegeguide.online/iciciappathon/barcode/saveBillinfo.php";
                String result = null;
                InputStream is = null;
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

                nameValuePairs.add(new BasicNameValuePair("ReferanceNo", String.valueOf(pinfo.reference_no)));
                nameValuePairs.add(new BasicNameValuePair("Cost", String.valueOf(pinfo.Transaction_amount)));
                nameValuePairs.add(new BasicNameValuePair("ReceiverAccount", pinfo.destination_accountno));
                nameValuePairs.add(new BasicNameValuePair("ProductList", list));
                //nameValuePairs.add(new BasicNameValuePair("",""));


                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(billSave);
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    //is = entity.getContent();

                    Log.e("log_tag", entity.toString());
                    //Log.d("____", v1);

                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection " + e.toString());


                }


            }
            Log.i("answer", answer);

            return answer;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;

        }
    }
}
//dont delete this i have to see why this doesnot work......if you know please tell me!!!!!!!!!!!!!
/*  HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("POST");
                Uri.Builder builder=new Uri.Builder().appendQueryParameter("ReferanceNo", String.valueOf(pinfo.reference_no))
                        .appendQueryParameter("Cost", String.valueOf(pinfo.Transaction_amount))
                        .appendQueryParameter("ReceiverAccount", pinfo.destination_accountno)
                        .appendQueryParameter("ProductList", list);
                String query=builder.build().getEncodedQuery();
                OutputStream os= urlConnection.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                Log.i("os",os.toString());
                urlConnection.disconnect();
             */
