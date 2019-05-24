package com.example.administrator.androidncck.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.androidncck.CTMonHoc;
import com.example.administrator.androidncck.ListMonHoc;
import com.example.administrator.androidncck.ProductModel;
import com.example.administrator.androidncck.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdapterMH extends RecyclerView.Adapter<AdapterMH.ViewHolder>{
    List<ProductModel> models;
    int mResource;
    Context mContext;
    private String TAG = "FireBase-Log";
    public AdapterMH(List<ProductModel> models, int mResource, Context mContext) {
        this.models = models;
        this.mResource = mResource;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(mResource,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final ProductModel model = models.get(i);
        viewHolder.txt_ID.setText(model.getId()+"");
        viewHolder.txt_TenMonHoc.setText(model.getProduct_name()+"");
        viewHolder.txt_MaMonHoc.setText(model.getProducer()+"");
        viewHolder.txt_MieuTa.setText(model.getDescription()+"");
        viewHolder.txt_TinChi.setText(model.getPrice()+"");

        //delete product
       viewHolder.btn_Delete.setOnClickListener(new View.OnClickListener() {
           String ProductName;
           @Override
           public void onClick(View view) {
               ProductName = viewHolder.txt_TenMonHoc.getText().toString();
               DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AdvancedAndroidFinalTest");
               Query PDQuery = ref.orderByChild("product_name").equalTo(ProductName);
               PDQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       for (DataSnapshot ds: dataSnapshot.getChildren()) {
                           ds.getRef().removeValue();
                       }
                       ListMonHoc.models.remove(i);
                       ListMonHoc.adapter.notifyDataSetChanged();
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {
                       Log.e(TAG, "onCancelled", databaseError.toException());
                   }
               });
           }
       });

       //Edit product
        viewHolder.btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CTMonHoc.class);
                intent.putExtra("item_product",model);
                mContext.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_TenMonHoc, txt_MaMonHoc, txt_MieuTa, txt_TinChi,txt_ID;
        private Button  btn_Delete, btn_Edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txt_ID = itemView.findViewById(R.id.txt_ID);
            this.txt_TenMonHoc = itemView.findViewById(R.id.txt_TenMonHoc);
            this.txt_MaMonHoc = itemView.findViewById(R.id.txt_MaMonHoc);
            this.txt_MieuTa = itemView.findViewById(R.id.txt_MieuTa);
            this.txt_TinChi = itemView.findViewById(R.id.txt_TinChi);
            this.btn_Edit = itemView.findViewById(R.id.btn_Edit);
            this.btn_Delete = itemView.findViewById(R.id.btn_delete);
        }
    }

//    private void onstart(){
//        ListMonHoc.models = new ArrayList<>();
//        ListMonHoc.MHRef = FirebaseDatabase.getInstance().getReference("AdvancedAndroidFinalTest");
//        ListMonHoc.MHRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                        ProductModel p = dataSnapshot1.getValue(ProductModel.class);
//                        ListMonHoc.models.add(p);
//                    }
//                    ListMonHoc.adapter = new AdapterMH(models,R.layout.item_subject,mContext);
//                    ListMonHoc.recyclerView.setAdapter(ListMonHoc.adapter);
//                    ListMonHoc.adapter.notifyDataSetChanged();
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//    }
}
