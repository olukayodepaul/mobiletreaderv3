package com.mobile.mtrader.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.mobiletreaderv3.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.ViewHolder> {

    Context context;
    List<Sales> clocking = new ArrayList<>();

    public BankAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BankAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.back_recycler_view_activity, parent, false);
        return new ViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull BankAdapter.ViewHolder holder, int position) {

        if(clocking!=null){

            Sales rs = clocking.get(position);
            holder.items.setText(rs.productname);
            holder.qty.setText(rs.order);

            DecimalFormat formatter = new DecimalFormat("#,###.00");
            double packPrice = Double.parseDouble(rs.packprice);
            double packQty = Double.parseDouble(rs.packqty);
            double rollPrice = Double.parseDouble(rs.rollprice);
            double rollQty = Double.parseDouble(rs.rollqty);

            holder.order.setText(formatter.format((rollPrice*rollQty)+(packPrice+packQty)));
        }
    }

    @Override
    public int getItemCount() {
        if (clocking != null && !clocking.isEmpty()) {
            return clocking.size();
        } else {
            return 0;
        }
    }

    public void setModulesAdapter(List<Sales> clocking) {
        this.clocking = clocking;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.items)
        TextView items;

        @BindView(R.id.qty)
        TextView qty;

        @BindView(R.id.order)
        TextView order;

        public ViewHolder(View mview) {
            super(mview);
            ButterKnife.bind(this, mview);
        }
    }
}
