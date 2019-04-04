package com.mobile.mtrader.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.mobiletreaderv3.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {

    Context context;
    Intent intent;
    List<Sales> orders = new ArrayList<>();


    public OrderHistoryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_group, parent, false);
        return new ViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(OrderHistoryAdapter.ViewHolder holder, int position) {

        if(orders!=null){

            Sales rs = orders.get(position);

        }
    }

    @Override
    public int getItemCount() {
        if (orders != null && !orders.isEmpty()) {
            return orders.size();
        } else {
            return 0;
        }
    }


    public void setSalesHistory(List<Sales> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.modulesnames)
        TextView modulesnames;

        @BindView(R.id.times)
        TextView times;

        @BindView(R.id.indicator_green)
        ImageView indicator_green;

        @BindView(R.id.indicator_red)
        ImageView indicator_red;

        @BindView(R.id.trigger_attendant)
        CardView trigger_attendant;

        public ViewHolder(View mview) {
            super(mview);
            ButterKnife.bind(this, mview);
        }
    }

}
