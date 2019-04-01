package com.mobile.mtrader.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.mobiletreaderv3.R;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;


public class SkuAdapter extends RecyclerView.Adapter<SkuAdapter.ViewHolder> {

    List<Products> products = new ArrayList<>();
    Context context;
    String customer_no;

    @Inject
    public SkuAdapter(Context context, String customer_no) {

        this.context = context;
        this.customer_no = customer_no;
    }

    @NonNull
    @Override
    public SkuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales_recycler_layout, parent, false);
        return new ViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull SkuAdapter.ViewHolder holder, int position) {

        if(products!=null){

            Products rs = products.get(position);
            holder.setListener(position, holder);
            holder.skus.setText(rs.productname);
            holder.soq.setText(rs.soq);

            if (rs.separator.equals("1")) {
                //own product
                holder.skus.setTextColor(Color.parseColor("#FFFFFF"));
                holder.skus.setBackgroundColor(Color.parseColor("#fa8100"));
            } else if (rs.separator.equals("2")) {
                //promo
                holder.skus.setTextColor(Color.parseColor("#FFFFFF"));
                holder.soq.setText("");
                holder.skus.setBackgroundColor(Color.parseColor("#1769AA"));
            } else if (rs.separator.equals("3")) {
                //competition product
                holder.order.setInputType(InputType.TYPE_NULL);
                holder.order.setText("0.0");
                holder.skus.setBackgroundColor(Color.parseColor("#ECEFF1"));
            }
        }
    }


    @Override
    public int getItemCount() {
        if(products!=null && !products.isEmpty()){
            return products.size();
        }else{
            return 0;
        }
    }

    public void setModulesAdapter(List<Products> products) {
        this.products = products;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.editTextq)
        EditText inventory;

        @BindView(R.id.editText2q)
        EditText pricings;

        @BindView(R.id.editText3q)
        EditText order;

        @BindView(R.id.textView6q)
        TextView soq;

        @BindView(R.id.textView4q)
        TextView skus;

        @BindView(R.id.cbg)
        ConstraintLayout cbg;


        public ViewHolder(View mview) {
            super(mview);
            ButterKnife.bind(this, mview);
        }

        public void setListener(int position, SkuAdapter.ViewHolder holder) {

        }
    }

}
