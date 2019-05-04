package com.mobile.mtrader.adapter;



import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mobile.mtrader.data.AllTablesStructures.Modules;
import com.mobile.mtrader.mobiletreaderv3.R;
import com.mobile.mtrader.ui.CustomerActivity;
import com.mobile.mtrader.ui.SalesActivity;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ModuleAdapter  extends RecyclerView.Adapter<ModuleAdapter.ViewHolder> {

    List<Modules> modules = new ArrayList<>();

    Context context;

    Intent intent;

    public ModuleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ModuleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.module_recycler_layout, parent, false);
        return new ViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(ModuleAdapter.ViewHolder holder, int position) {

        if(modules!=null) {
            Modules rs = modules.get(position);
            holder.modulesnames.setText(rs.name);
            holder.titles.setText("");
            holder.setListener(position);
        }
    }

    @Override
    public int getItemCount() {
        if(modules!=null && !modules.isEmpty()){
            return modules.size();
        }else{
            return 0;
        }
    }

    public void setModulesAdapter(List<Modules> modules) {
        this.modules = modules;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pst)
        LinearLayout pst;

        @BindView(R.id.modulesnames)
        TextView modulesnames;

        @BindView(R.id.icon_image)
        ImageView icon_image;

        @BindView(R.id.titles)
        TextView titles;

        public ViewHolder(View mview){
            super(mview);
            ButterKnife.bind(this, mview);
        }

        public void setListener(int position) {
            pst.setOnClickListener(view -> changeModule(modules.get(position).nav));
        }
    }

    private void changeModule(String chg) {

        switch (chg) {
            case "1":
                intent = new Intent(context,SalesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            case "2":
                intent = new Intent(context,CustomerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
        }
    }

}
