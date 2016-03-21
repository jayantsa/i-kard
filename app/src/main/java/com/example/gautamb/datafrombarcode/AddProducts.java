package com.example.gautamb.datafrombarcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gautamb on 3/22/2016.
 */
public class AddProducts extends Activity {
    public ArrayList<String> products = new ArrayList<String>();
    public ArrayList<String> amountList = new ArrayList<String>();
    public ArrayList<String> concatenate = new ArrayList<String>();
    public String cost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
        Intent get_products = getIntent();
        TextView total_cost = (TextView) findViewById(R.id.total);
        products = get_products.getStringArrayListExtra("list");
        amountList = get_products.getStringArrayListExtra("amount");
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
                Intent scn = new Intent(getApplicationContext() , MainActivity.class);
                scn.putExtra("back_intent","1");
                startActivity(scn);
            }
        });
    }
}
