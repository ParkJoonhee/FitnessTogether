package com.kosmo.fitnesstogether.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kosmo.fitnesstogether.R;
import com.kosmo.fitnesstogether.service.I2790;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;
    private I2790 items;
    private int itemLayoutResId;

    public SearchAdapter(Context context, I2790 items, int itemLayoutResId) {
        this.context = context;
        this.items = items;
        this.itemLayoutResId = itemLayoutResId;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(itemLayoutResId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemName.setText(items.getRow().get(position).getDESC_KOR());
        holder.itemOne.setText(items.getRow().get(position).getSERVING_SIZE());
        holder.itemKcal.setText(items.getRow().get(position).getDESC_KOR());
        holder.itemTan.setText(items.getRow().get(position).getDESC_KOR());
        holder.itemDan.setText(items.getRow().get(position).getDESC_KOR());
        holder.itemGi.setText(items.getRow().get(position).getDESC_KOR());
        holder.itemName.setText(items.getRow().get(position).getDESC_KOR());

    }

    @Override
    public int getItemCount() {
        return items.getRow().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;
        private TextView itemOne;
        private TextView itemKcal;
        private TextView itemTan;
        private TextView itemDan;
        private TextView itemGi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemOne = itemView.findViewById(R.id.itemOne);
            itemKcal = itemView.findViewById(R.id.itemKcal);
            itemTan = itemView.findViewById(R.id.itemTan);
            itemDan = itemView.findViewById(R.id.itemDan);
            itemGi = itemView.findViewById(R.id.itemGi);
        }

    }
}
