package com.mobile.mtrader.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.model.ExposeSalesData;
import com.mobile.mtrader.repo.RealmService;
import com.mobile.mtrader.util.AppUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SkuAdapter extends RecyclerView.Adapter<SkuAdapter.ViewHolder> {

    List<ExposeSalesData> list;
    Context context;
    RealmService realmService;
    int custId;
    String custName;

    public SkuAdapter(Context context, List<ExposeSalesData> list, RealmService realmService,
                      int custId, String custName
    ) {

        this.list = list;
        this.context = context;
        this.realmService = realmService;
        this.custId = custId;
        this.custName = custName;
    }

    @NonNull
    @Override
    public SkuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales_recycler_layout, parent, false);
        return new ViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull SkuAdapter.ViewHolder holder, int position) {

        ExposeSalesData rs = list.get(position);
        holder.skus.setText(rs.product_name);
        holder.soq.setText(rs.soq);

        if (rs.separator.equals("1")) {
            //own product
            holder.skus.setTextColor(Color.parseColor("#FFFFFF"));
            holder.skus.setBackgroundColor(Color.parseColor("#fa8100"));
        } else if (rs.separator.equals("2")) {
            //promo
            holder.skus.setTextColor(Color.parseColor("#FFFFFF"));
            holder.soq.setText("");
            holder.skus.setBackgroundColor(Color.parseColor("#1769AA"));
        } else if (rs.separator.equals("3")) {
            //competition product
            holder.order.setInputType(InputType.TYPE_NULL);
            holder.order.setText("0.0");
            holder.skus.setBackgroundColor(Color.parseColor("#ECEFF1"));
        }


        holder.inventory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.get(holder.getAdapterPosition()).setCustomer_id(Integer.toString(custId));
                list.get(holder.getAdapterPosition()).setCustomer_name(custName);
                list.get(holder.getAdapterPosition()).setOrder(holder.order.getText().toString());
                list.get(holder.getAdapterPosition()).setInventory(holder.inventory.getText().toString());
                list.get(holder.getAdapterPosition()).setPricing(holder.pricings.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        holder.pricings.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list.get(holder.getAdapterPosition()).setCustomer_id(Integer.toString(custId));
                list.get(holder.getAdapterPosition()).setCustomer_name(custName);
                list.get(holder.getAdapterPosition()).setOrder(holder.order.getText().toString());
                list.get(holder.getAdapterPosition()).setInventory(holder.inventory.getText().toString());
                list.get(holder.getAdapterPosition()).setPricing(holder.pricings.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        holder.order.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!holder.order.getText().toString().equals(".")) {
                    if (!holder.order.getText().toString().equals("")) {
                        if (Double.parseDouble(realmService.skuQtyBalance(rs.product_code)) < Double.parseDouble(holder.order.getText().toString())) {
                            holder.order.setText("");
                            AppUtil.showAlertDialog(context, "Notification", realmService.skuQtyBalance(rs.product_code)
                                    + " " + rs.product_name + " quantity exceeded" + list.get(holder.getAdapterPosition()).getOrder(), "Close");
                        } else {
                            list.get(holder.getAdapterPosition()).setCustomer_id(Integer.toString(custId));
                            list.get(holder.getAdapterPosition()).setCustomer_name(custName);
                            list.get(holder.getAdapterPosition()).setOrder(holder.order.getText().toString());
                            list.get(holder.getAdapterPosition()).setInventory(holder.inventory.getText().toString());
                            list.get(holder.getAdapterPosition()).setPricing(holder.pricings.getText().toString());
                        }
                    }
                } else {
                    holder.order.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.editTextq)
        EditText inventory;

        @BindView(R.id.editText2q)
        EditText pricings;

        @BindView(R.id.editText3q)
        EditText order;

        @BindView(R.id.textView6q)
        TextView soq;

        @BindView(R.id.textView4q)
        TextView skus;

        @BindView(R.id.cbg)
        ConstraintLayout cbg;


        public ViewHolder(View mview) {
            super(mview);
            ButterKnife.bind(this, mview);
        }
    }

}
