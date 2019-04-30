package com.mobile.mtrader.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.mobile.mtrader.data.AllTablesStructures.Customers;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.ui.BankActivity;
import com.mobile.mtrader.ui.DeliveryMapActivity;
import com.mobile.mtrader.ui.DepotClockoutActivity;
import com.mobile.mtrader.ui.DepotClokingActivity;
import com.mobile.mtrader.viewmodels.CustomerFragmentViewModel;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder> {

    Intent intent;

    Context context;

    CustomerFragmentViewModel cFViewModel;

    List<Customers> customers = new ArrayList<>();

    int REQUEST_FOR_ACTIVITY_CODE = 1;

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public CustomerListAdapter(Context context, CustomerFragmentViewModel cFViewModel) {
        this.context = context;
        this.cFViewModel = cFViewModel;
    }

    @Override
    public CustomerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customers_recycler_layout, parent, false);
        return new ViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(CustomerListAdapter.ViewHolder holder, int position) {

        if (customers != null) {

            Customers rs = customers.get(position);
            holder.setListener(position);

            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            int color1 = generator.getRandomColor();

            TextDrawable drawable1 = TextDrawable.builder()
                    .buildRoundRect(rs.outletname.substring(0, 1), color1, 100);
            holder.icon_image.setImageDrawable(drawable1);
            holder.modulesnames.setText(rs.outletname);
            holder.titles.setText(rs.notice);
            holder.times.setText(rs.rostertime);
        }

    }

    @Override
    public int getItemCount() {
        if (customers != null && !customers.isEmpty()) {
            return customers.size();
        } else {
            return 0;
        }
    }

    public void setModulesAdapter(List<Customers> customers) {
        this.customers = customers;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pst)
        CoordinatorLayout pst;

        @BindView(R.id.modulesnames)
        TextView modulesnames;

        @BindView(R.id.titles)
        TextView titles;

        @BindView(R.id.times)
        TextView times;

        @BindView(R.id.icon_image)
        ImageView icon_image;

        public ViewHolder(View mview) {
            super(mview);
            ButterKnife.bind(this, mview);
        }

        public void setListener(int position) {
            pst.setOnClickListener(view ->
                    //get the position where the user is coming from.
                    swichUI(customers.get(position).sort,
                            customers.get(position).token,
                            customers.get(position).id,
                            customers.get(position).outletname,
                            customers.get(position).outlet_waiver,
                            customers.get(position).lat,
                            customers.get(position).lng

                    )
            );
        }
    }

    private void swichUI(int chg, String customerKeys, int id, String outletnames, String outletwaiver, String outletlat, String outletlng ) {

        switch (chg) {
            case 1:
                intent = new Intent(context, DepotClokingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ((Activity) context).startActivityForResult(intent,REQUEST_FOR_ACTIVITY_CODE);
                break;
            case 2:
                mCompositeDisposable.add(cFViewModel.LastGeolocation()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                data -> {
                                    myLocation(customerKeys, id, outletnames, outletwaiver,
                                            outletlat, outletlng, data.lat, data.lng);
                                },
                                Throwable -> {
                                    myLocation(customerKeys, id, outletnames, outletwaiver,
                                            outletlat, outletlng, "0.0", "0.0");
                                })
                );
                break;
            case 3:
                intent = new Intent(context, BankActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            case 4:
                intent = new Intent(context, DepotClockoutActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
        }
    }

    public void myLocation(String customerKeys, int id, String outletnames, String outletwaiver,
                           String outletlat, String outletlng, String depotlat, String depotlng){

        intent = new Intent(context, DeliveryMapActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("CUSTOMERS_ACCESS_KEYS",customerKeys);
        intent.putExtra("CUSTOMER_ID", Integer.toString(id));
        intent.putExtra("CUSTOMER_NAME",outletnames);
        intent.putExtra("OUTLET_WAIVER",outletwaiver);
        intent.putExtra("OUTLET_LAT",outletlat);
        intent.putExtra("OUTLET_LNG",outletlng);
        intent.putExtra("DEPOT_LAT",depotlat);
        intent.putExtra("DEPOT_LNG",depotlng);
        context.startActivity(intent);
    }
}
