package com.example.administrator.androidncck;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.administrator.androidncck.Adapter.AdapterMH;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListMonHoc extends AppCompatActivity {
    Button btnAddNewSP;
    public static RecyclerView recyclerView;
    public static List<ProductModel> models;
    public static AdapterMH adapter;
    FirebaseDatabase database ;
    public static DatabaseReference MHRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mon_hoc);
        onIt();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        database = FirebaseDatabase.getInstance();
        MHRef = database.getReference("AdvancedAndroidFinalTest");

        Addata();
        btnAddNewSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListMonHoc.this,AddProduct.class);
                startActivity(intent);
            }
        });

    }

    private void Addata() {
        models = new ArrayList<>();

        MHRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ProductModel p = dataSnapshot1.getValue(ProductModel.class);
                        models.add(p);
                    }
                    adapter = new AdapterMH(models,R.layout.item_subject,ListMonHoc.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

    }

    private void onIt() {
        btnAddNewSP = (Button)findViewById(R.id.btn_List_Add);
        recyclerView =(RecyclerView)findViewById(R.id.recyclerView_MH);
    }
}
