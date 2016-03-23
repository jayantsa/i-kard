package com.android4dev.navigationview;

/**
 * Created by gautamb on 3/23/2016.
 */
public class PaymentInfo {
    String destination_accountno;
    String transaction_date;
    int reference_no;
    Double Transaction_amount;
    String Payee_name;
    int payee_id;
    String status;
    public PaymentInfo()
    {

    }
    public PaymentInfo(String a,String b,int c,Double d,String e,int f,String g)
    {
        this.destination_accountno=a;
        this.transaction_date=b;
        this.reference_no=c;
        this.Transaction_amount=d;
        this.Payee_name=e;
        this.payee_id=f;
        this.status=g;
    }
    public PaymentInfo(String a,String b,int c,Double d,String g)
    {
        this.destination_accountno=a;
        this.transaction_date=b;
        this.reference_no=c;
        this.Transaction_amount=d;
        this.Payee_name="";
        this.payee_id=0;
        this.status=g;
    }
}

