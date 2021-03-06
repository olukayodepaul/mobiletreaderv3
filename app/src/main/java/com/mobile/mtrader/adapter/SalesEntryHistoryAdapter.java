package com.mobile.mtrader.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.mobiletreaderv3.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


public class SalesEntryHistoryAdapter extends RecyclerView.Adapter<SalesEntryHistoryAdapter.ViewHolder> {

    List<Sales> hostory = new ArrayList<>();

    public SalesEntryHistoryAdapter() {
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
        if (hostory != null) {

            Sales rs = hostory.get(position);
            holder.modulesnames.setText(rs.productname);
            holder.times_image_items.setText(rs.orders);
            holder.times_image_qty_int.setText(rs.inventory);
            holder.times_image_qty_pricing.setText(rs.pricing);

            DecimalFormat formatter = new DecimalFormat("#,###.00");

            double total = (Double.parseDouble(rs.rollprice) + Double.parseDouble(rs.packprice));
            holder.times_image_qty.setText(formatter.format(total));

            if (rs.localstatus.equals("1")) {
                holder.indicator_green.setVisibility(View.VISIBLE);

            }else{
                holder.indicator_red.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return hostory.size();
    }

    public void setSalesHistory(List<Sales> hostory) {
        this.hostory = hostory;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.modulesnames)
        TextView modulesnames;

        @BindView(R.id.times_image_items)
        TextView times_image_items;

        @BindView(R.id.times_image_qty)
        TextView times_image_qty;

        @BindView(R.id.indicator_red)
        ImageView indicator_red;

        @BindView(R.id.indicator_green)
        ImageView indicator_green;

        @BindView(R.id.times_image_qty_int)
        TextView  times_image_qty_int;

        @BindView(R.id.times_image_qty_pricing)
        TextView times_image_qty_pricing;

        public ViewHolder(View mview) {
            super(mview);
            ButterKnife.bind(this, mview);
        }
    }

}
