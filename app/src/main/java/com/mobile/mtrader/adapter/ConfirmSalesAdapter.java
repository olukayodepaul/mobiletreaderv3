package com.mobile.mtrader.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mobile.mtrader.data.AllTablesStructures.SalesEntries;
import com.mobile.mtrader.mobiletreaderv3.R;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmSalesAdapter extends RecyclerView.Adapter<ConfirmSalesAdapter.ViewHolder> {

    List<SalesEntries> products = new ArrayList<>();

    Context context;

    public ConfirmSalesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ConfirmSalesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.confirm_recycler_activity, parent, false);
        return new ViewHolder(mview);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ConfirmSalesAdapter.ViewHolder holder, int position) {
        if(products!=null){

            SalesEntries rs = products.get(position);
            holder.sku.setText(rs.productname);
            holder.order.setText(rs.inventory);
            holder.app_price.setText(rs.pricing);
            holder.invent.setText(Double.toString(rs.orders));

            double packPrice = Double.parseDouble(rs.packprice);
            double rollPrice = Double.parseDouble(rs.rollprice);

            String[] transDouble =  Double.toString(rs.orders).split("\\.");
            holder.tv_price.setText(Double.toString(rollPrice*Integer.parseInt(transDouble[0])+packPrice*Integer.parseInt(transDouble[1])));

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

    public void setModulesAdapter(List<SalesEntries> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sku)
        TextView sku;

        @BindView(R.id.order)
        TextView order;

        @BindView(R.id.app_price)
        TextView app_price;

        @BindView(R.id.invent)
        TextView invent;

        @BindView(R.id.tv_price)
        TextView tv_price;

        public ViewHolder(View mview){
            super(mview);
            ButterKnife.bind(this, mview);
        }
    }
}