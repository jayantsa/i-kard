package com.android4dev.navigationview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gautamb on 3/23/2016.
 */
public class AddProducts extends Activity {
    public ArrayList<String> products = new ArrayList<String>();
    public ArrayList<String> amountList = new ArrayList<String>();
    public ArrayList<String> product_id = new ArrayList<String>();
    public String cost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
        Intent get_products = getIntent();
        TextView total_cost = (TextView) findViewById(R.id.total);
        products = get_products.getStringArrayListExtra("list");
        amountList = get_products.getStringArrayListExtra("amount");
        product_id = get_products.getStringArrayListExtra("productId");
        cost = get_products.getStringExtra("total_cost");
        int len=products.size();
        int i=0;
        while(len>0){
            products.set(i,products.get(i) + " Rs."+amountList.get(i));
            i++;
            len--;
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, products);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        Button scan = (Button) findViewById(R.id.scan);
        total_cost.setText("Total: Rs."+cost);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scn = new Intent(getApplicationContext() , ScanPay.class);
                scn.putExtra("back_intent","1");
                startActivity(scn);
            }
        });
        Button paynow=(Button)findViewById(R.id.paynow);
        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(getApplicationContext(), Payment.class);
                x.putExtra("paise", cost);
                String list="";
                for(int i=0;i<product_id.size();i++)
                {
                    list+=product_id.get(i);
                    if(i != product_id.size()-1)
                    {
                        list += ",";
                    }
                }
                //list+="1023656455,56464687979,123456789,9876543965,1236454897,4587556232156";
                x.putExtra("list",list);
                Log.i("cost", "1000");
                startActivity(x);
            }
        });
    }
}

