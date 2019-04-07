package com.mobile.mtrader.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.mtrader.data.AllTablesStructures.Sales;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.ui.Customer_Sales_History;
import com.mobile.mtrader.viewmodels.BankViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {

    Context context;

    List<Sales> hostory = new ArrayList<>();

    Intent intent;

    BankViewModel bankViewModel;

    CompositeDisposable mDis = new CompositeDisposable();

    public OrderHistoryAdapter(Context context, BankViewModel bankViewModel) {
        this.context = context;
        this.bankViewModel = bankViewModel;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_group, parent, false);
        return new ViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.ViewHolder holder, int position) {
        if (hostory != null) {

            Sales rs = hostory.get(position);
            holder.modulesnames.setText(rs.cutomersname);
            holder.times.setText(rs.salestime);

            holder.trigger_attendant.setOnClickListener(v -> {
                intent = new Intent(context, Customer_Sales_History.class);
                intent.putExtra("CUSTOMER_ID", rs.customerno);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });

            mDis.add(bankViewModel.trackUnPushDataToServer(rs.customerno,"2")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            data->{
                                if(data==0){
                                    holder.indicator_red.setVisibility(View.GONE);
                                    holder.indicator_green.setVisibility(View.VISIBLE);
                                }else{
                                    holder.indicator_red.setVisibility(View.VISIBLE);
                                    holder.indicator_green.setVisibility(View.GONE);
                                }

                            })
            );

        }
    }

    @Override
    public int getItemCount() {
        if (hostory != null && !hostory.isEmpty()) {
            return hostory.size();
        } else {
            return 0;
        }
    }

    public void setSalesHiatory(List<Sales> clocking) {
        this.hostory = clocking;
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
