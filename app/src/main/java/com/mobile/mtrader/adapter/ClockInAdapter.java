package com.mobile.mtrader.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.mtrader.data.AllTablesStructures.Modules;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.model.RealmConverterRepBasketList;
import com.mobile.mtrader.repo.RealmService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockInAdapter extends RecyclerView.Adapter<ClockInAdapter.ViewHolder> {

    Context context;
    List<Products> clocking = new ArrayList<>();

    public ClockInAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ClockInAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clockout_recycler_layout, parent, false);
        return new ViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull ClockInAdapter.ViewHolder holder, int position) {
        Products rs = clocking.get(position);
        holder.items.setText(rs.productname);
        holder.qty.setText(rs.qty);
    }

    @Override
    public int getItemCount() {
        return clocking.size();
    }

    public void setModulesAdapter(List<Products> clocking) {
        this.clocking = clocking;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.items)
        TextView items;

        @BindView(R.id.qty)
        TextView qty;

        public ViewHolder(View mview) {
            super(mview);
            ButterKnife.bind(this, mview);
        }
    }
}

