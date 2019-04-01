package com.mobile.mtrader.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.widget.TextView;


import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.model.RealmConverterAddProducts;
import com.mobile.mtrader.repo.RealmService;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmSalesAdapter extends RecyclerView.Adapter<ConfirmSalesAdapter.ViewHolder> {

    Context context;
    List<RealmConverterAddProducts> list;
    RealmService realmService;

    public ConfirmSalesAdapter(Context context, RealmService realmService) {
        this.context = context;
        this.realmService = realmService;
        this.list = realmService.getSalesEntry();
    }

    @NonNull
    @Override
    public ConfirmSalesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.confirm_recycler_activity, parent, false);
        return new ViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmSalesAdapter.ViewHolder holder, int position) {

        RealmConverterAddProducts rs = list.get(position);

        DecimalFormat myFormatter = new DecimalFormat("#,###.00");
        holder.sku.setText(rs.product_name);
        holder.order.setText(Double.toString(rs.inventory));
        holder.app_price.setText(Double.toString(rs.pricing));
        holder.invent.setText(Double.toString(rs.order));

        String[] amount = (Double.toString(rs.order)).split("\\.");
        double rollPrices = Integer.parseInt(amount[0])*rs.rollprice;
        double packPrices = Integer.parseInt(amount[1])*rs.packprice;

        long totalPrice = Math.round((int) rollPrices+packPrices);
        holder.tv_price.setText(myFormatter.format(totalPrice));

    }

    @Override
    public int getItemCount() {
        return list.size();
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
        public ViewHolder(View mview) {
            super(mview);
            ButterKnife.bind(this, mview);
        }
    }}
