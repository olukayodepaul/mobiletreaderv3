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
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.model.RealmConverterAddProducts;
import com.mobile.mtrader.repo.RealmService;
import com.mobile.mtrader.ui.Customer_Sales_History;
import com.mobile.mtrader.ui.DepotClokingActivity;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {

    List<RealmConverterAddProducts> list;
    Context context;
    RealmService realmService;
    Intent intent;


    public OrderHistoryAdapter(Context context, RealmService realmService) {
        this.context = context;
        this.realmService = realmService;
        this.list = realmService.getSalesEntryGroup();
    }

    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_group, parent, false);
        return new ViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(OrderHistoryAdapter.ViewHolder holder, int position) {
        RealmConverterAddProducts rs = list.get(position);
        holder.modulesnames.setText(rs.customer_name);
        holder.times.setText(rs.entrydate);
        if(realmService.getUnPostSales(rs.customer_id, 2,1) == 0) {
            holder.indicator_green.setVisibility(View.VISIBLE);
        }else{
            holder.indicator_red.setVisibility(View.VISIBLE);
        }

        holder.trigger_attendant.setOnClickListener(v->{
            intent = new Intent(context, Customer_Sales_History.class);
            intent.putExtra("CUSTOMER_ID", rs.customer_id);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        if(list==null)
            return 0;
        else
            return list.size();
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
