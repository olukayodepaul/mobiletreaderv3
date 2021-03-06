package com.mobile.mtrader.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mobile.mtrader.data.AllTablesStructures.Products;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.viewmodels.BankViewModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.ViewHolder> {

    Context context;

    List<Products> clocking = new ArrayList<>();

    BankViewModel bankViewModel;

    CompositeDisposable mDis = new CompositeDisposable();

    public BankAdapter(Context context, BankViewModel bankViewModel) {
        this.context = context;
        this.bankViewModel = bankViewModel;
    }

    @NonNull
    @Override
    public BankAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clockouts_recycler_view_activity, parent, false);
        return new ViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull BankAdapter.ViewHolder holder, int position) {
        if (clocking != null) {

            Products rs = clocking.get(holder.getAdapterPosition());
            holder.items.setText(rs.productname);
            DecimalFormat formatter = new DecimalFormat("#,###.0");

            mDis.add(bankViewModel.skuTotalSum(rs.productcode)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            data -> {

                                mDis.add(bankViewModel.sumAllOrder(rs.productcode)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                mdata -> {
                                                    holder.qty.setText(formatter.format(mdata));
                                                },
                                                Throwable -> {
                                                    holder.qty.setText("0.0");
                                                })
                                );

                                mDis.add(bankViewModel.sumAllSalesCommission(rs.productcode)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                mdata -> {
                                                    holder.order.setText(formatter.format(mdata));
                                                },
                                                Throwable -> {
                                                    holder.order.setText("0.0");
                                                })
                                );

                                holder.basket.setText(formatter.format(data));
                            },
                            Throwable -> {
                                holder.basket.setText("0.0");
                            })
            );
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setModulesAdapter(List<Products> clocking) {
        this.clocking = clocking;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.items)
        TextView items;

        @BindView(R.id.basket)
        TextView basket;

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