package com.mobile.mtrader.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.mtrader.data.AllTablesStructures.AllRepCustomers;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.ui.CustomerProfile;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AllRepCustomersAdapter extends RecyclerView.Adapter<AllRepCustomersAdapter.ViewHolder>{

    List<AllRepCustomers> list = new ArrayList<>();

    Context context;

    public AllRepCustomersAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AllRepCustomersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allrescustomer_layout, parent, false);
        return new ViewHolder(mview);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AllRepCustomersAdapter.ViewHolder holder, int position) {
        if (list != null) {
            AllRepCustomers rs = list.get(holder.getAdapterPosition());
            holder.modulesnames.setText(rs.outletname);
            holder.titles.setText("Cust No: "+rs.customerno);
            holder.setListener(holder.getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        if (list != null && !list.isEmpty()) {
            return list.size();
        } else {
            return 0;
        }
    }

    public void setModulesAdapter(List<AllRepCustomers> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.modulesnames)
        TextView modulesnames;

        @BindView(R.id.titles)
        TextView titles;

        @BindView(R.id.pst)
        LinearLayout pst;

        public void setListener(int position) {
            pst.setOnClickListener(view -> changeModule(list.get(position).id));
        }

        public ViewHolder(View mview) {
            super(mview);
            ButterKnife.bind(this, mview);
        }
    }

    private void changeModule(int chg) {
        //Toast.makeText(context, Integer.toString(chg), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context,CustomerProfile.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("CUSTOMERS_ID_INFO_APPS", chg);
        context.startActivity(intent);
    }
}
