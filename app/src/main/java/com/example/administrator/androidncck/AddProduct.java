package com.example.administrator.androidncck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddProduct extends AppCompatActivity {
    EditText edt_AddProduct,edt_AddProducer,edt_AddMieuTa,edt_AddGia,edt_AddId;
    Button btn_AddProduct;
    String sProduct_Name, sProducer, sDescription;
    long Price;
    int Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        onIt();
        btn_AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onValuedayForm()){
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("AdvancedAndroidFinalTest").push();
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", Id);
                    map.put("product_name", sProduct_Name);
                    map.put("price", Price);
                    map.put("producer", sProducer);
                    map.put("description", sDescription);
                    rootRef.updateChildren(map);

                    Intent intent = new Intent(AddProduct.this,ListMonHoc.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean onValuedayForm() {
        String erro ="This is can not blank";
        sProduct_Name = edt_AddProduct.getText().toString();
        sProducer = edt_AddProducer.getText().toString();
        sDescription = edt_AddMieuTa.getText().toString();
        try {
            Id = Integer.parseInt(edt_AddId.getText().toString());
        }
        catch (Exception e){
            edt_AddId.setError("Id is number");
            return false;
        }
        try {
            Price = Long.parseLong(edt_AddGia.getText().toString());
        }
        catch (Exception e){
            edt_AddGia.setError("Price is number");
            return false;
        }
        if (sProduct_Name.length() < 1){
            edt_AddProduct.setText(erro);
            return false;
        }
        if (sProducer.length() < 1){
            edt_AddProducer.setText(erro);
            return false;
        }
        if (sDescription.length() < 1){
            edt_AddMieuTa.setText(erro);
            return false;
        }

        return true;
    }

    private void onIt() {
        edt_AddId = (EditText)findViewById(R.id.edt_AddId);
        edt_AddProduct = (EditText)findViewById(R.id.edt_AddProduct);
        edt_AddProducer = (EditText)findViewById(R.id.edt_AddProducer);
        edt_AddMieuTa = (EditText)findViewById(R.id.edt_AddMieuTa);
        edt_AddGia = (EditText)findViewById(R.id.edt_AddGia);
        btn_AddProduct = (Button)findViewById(R.id.btn_AddProduct);
    }
}
