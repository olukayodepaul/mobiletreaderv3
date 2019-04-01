package com.mobile.mtrader.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.model.RealmConverterAddProducts;
import com.mobile.mtrader.repo.RealmService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalesEntryHistoryAdapter  extends RecyclerView.Adapter<SalesEntryHistoryAdapter.ViewHolder>{

    Context context;
    RealmService realmService;
    List<RealmConverterAddProducts> list;

    public SalesEntryHistoryAdapter(Context context, RealmService realmService, String customerno) {
        this.context = context;
        this.realmService = realmService;
        this.list = realmService.getSalesItemsEntries(customerno);
    }

    @NonNull
    @Override
    public SalesEntryHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_sales_history, parent, false);
        return new ViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesEntryHistoryAdapter.ViewHolder holder, int position) {

        RealmConverterAddProducts rs = list.get(position);
        holder.product_name.setText(rs.product_name);
        holder.times_image_items.setText(Double.toString(rs.order));
        if(rs.s_status == 2 || rs.status == 2 ) {
            holder.indicator_red.setVisibility(View.GONE);
        }
        String orders[] = Double.toString(rs.order).split("\\.");
        double rollPrice = rs.rollprice*Integer.parseInt(orders[0]);
        double packPrice = rs.packprice*Integer.parseInt(orders[0]);
        holder.times_image_qty.setText(Double.toString(rollPrice+packPrice));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.modulesnames)
        TextView product_name;

        @BindView(R.id.times)
        TextView times;

        @BindView(R.id.indicator_red)
        ImageView indicator_red;

        @BindView(R.id.times_image_items)
        TextView times_image_items;

        @BindView(R.id.times_image_qty)
        TextView times_image_qty;


        public ViewHolder(View mview) {
            super(mview);
            ButterKnife.bind(this, mview);
        }
    }

}
