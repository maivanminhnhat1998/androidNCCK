package com.example.administrator.androidncck;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CTMonHoc extends AppCompatActivity {
    EditText edt_Product, edt_Producer, edt_MieuTa, edt_Gia;
    Button btn_save;
    String sProduct_Name, sProducer, sDescription;
    long Price;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctmon_hoc);
        onIt();
        onGetIntent();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onValuedayForm()){
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AdvancedAndroidFinalTest");
                    Query PD = ref.orderByChild("id").equalTo(id);
                    PD.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds: dataSnapshot.getChildren()) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("product_name", sProduct_Name);
                                map.put("price", Price);
                                map.put("producer", sProducer);
                                map.put("description", sDescription);
                                ds.getRef().updateChildren(map);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Intent intent = new Intent(CTMonHoc.this,ListMonHoc.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private boolean onValuedayForm() {
        String erro ="This is can not blank";
        sProduct_Name = edt_Product.getText().toString();
        sProducer = edt_Producer.getText().toString();
        sDescription = edt_MieuTa.getText().toString();
        try {
            Price = Long.parseLong(edt_Gia.getText().toString());
        }
        catch (Exception e){
            edt_Gia.setError("Price is number");
            return false;
        }
        if (sProduct_Name.length() < 1){
            edt_Product.setText(erro);
            return false;
        }
        if (sProducer.length() < 1){
            edt_Producer.setText(erro);
            return false;
        }
        if (sDescription.length() < 1){
            edt_MieuTa.setText(erro);
            return false;
        }

        return true;
    }

    private void onGetIntent() {
        Intent intent = getIntent();
        ProductModel item = (ProductModel) intent.getSerializableExtra("item_product");
        id = item.getId();
        edt_Product.setText(item.getProduct_name()+"");
        edt_Producer.setText(item.getProducer()+"");
        edt_MieuTa.setText(item.getDescription()+"");
        edt_Gia.setText(item.getPrice()+"");

    }

    private void onIt() {
        edt_Producer = (EditText)findViewById(R.id.edt_Producer);
        edt_Product = (EditText)findViewById(R.id.edt_Product);
        edt_MieuTa = (EditText)findViewById(R.id.edt_MieuTa);
        edt_Gia = (EditText)findViewById(R.id.edt_Gia);
        btn_save = (Button)findViewById(R.id.btn_save_Product);
    }
}
