package ru.serujimir.mediationapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ru.serujimir.mediationapp.Parsing.Feeling;
import ru.serujimir.mediationapp.R;

public class FeelingsAdapter extends RecyclerView.Adapter<FeelingsAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    ArrayList<Feeling> feelingArrayList;
    Context context;

    public FeelingsAdapter(Context context, ArrayList<Feeling> feelingArrayList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.feelingArrayList = feelingArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.feeling_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feeling feeling = feelingArrayList.get(holder.getAdapterPosition());
//        Glide.with(context).load(context.getDrawable(R.drawable.calm___icon)).into(holder.ivFeelingIcon);
        Glide.with(context).load(feeling.getFeelingIcon()).into(holder.ivFeelingIcon);
        holder.tvFeelingText.setText(feeling.getFeelingText());
//        holder.ivFeelingIcon.setImageDrawable(context.getDrawable(R.drawable.calm___icon));
    }

    @Override
    public int getItemCount() {
        return feelingArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivFeelingIcon;
        final TextView tvFeelingText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFeelingIcon = itemView.findViewById(R.id.ivFeelingIcon);
            tvFeelingText = itemView.findViewById(R.id.tvFeelingText);
        }
    }
    public void update() {
        notifyDataSetChanged();
    }
}
