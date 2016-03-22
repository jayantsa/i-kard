package com.example.gautamb.datafrombarcode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class Payment extends ActionBarActivity implements PaymentInterface{

    String s;
    private static final String CLIENT_ID="";
    private static final String TOKEN="";
    private static final String ACCOUNT="5555666677770655";
    private static final String DESACCOUNT="5555666677770656";
    private static final String CUSTOMER_ID="";

    ImageView v;
    ImageLoader imgLoader;

    //ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        String DestAccountNo="555566667777066";
        String payeeDescription="payedesc";
        String payeeId="1";
        String type_of_transaction="Groceries";
        Intent i=getIntent();
        Bundle b=i.getExtras();
        String cost=b.getString("paise");
        String list=b.getString("list");
        String url=makeUrl(CLIENT_ID,TOKEN,ACCOUNT,DESACCOUNT,cost,payeeDescription);

        PaymentAsyncClass task=new PaymentAsyncClass(this,list);
        task.listener=this;
        task.execute(url);

    }

    private String makeUrl(String customerId,String accessToken,String srcaccount,String destinationAccount,String amount,String Descriptionofpayee) {
        String url="https://retailbanking.mybluemix.net/banking/icicibank/fundTransfer?client_id="+customerId+
                "&token="+accessToken+"&srcAccount="+srcaccount+
                "&destAccount="+destinationAccount+"&amt="+amount+"&payeeDesc="+Descriptionofpayee+"&payeeId=1&type_of_transaction=Groceries";

        return url;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
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
    public void PaymentTaskOnComplete(String s,ProgressDialog d) {

        String BASE_QR_URL = "http://chart.apis.google.com/chart?cht=qr&chs=400x400&chld=M&choe=UTF-8&chl="+s;
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        imgLoader = ImageLoader.getInstance();
        imgLoader.init(config);

        v=(ImageView)findViewById(R.id.imageview);
        imgLoader.displayImage(BASE_QR_URL,v);
        d.dismiss();


    }
}
