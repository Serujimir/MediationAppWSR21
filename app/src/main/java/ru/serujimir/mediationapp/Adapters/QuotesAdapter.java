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

import ru.serujimir.mediationapp.Parsing.Quote;
import ru.serujimir.mediationapp.R;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder>{

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Quote> quoteArrayList;

    public QuotesAdapter(Context context, ArrayList<Quote> quoteArrayList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.quoteArrayList = quoteArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.quote_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Quote quote = quoteArrayList.get(holder.getAdapterPosition());
        Glide.with(context).load(quote.getQuoteIcon()).into(holder.ivQuoteIcon);
        holder.tvQuoteHeader.setText(quote.getQuoteHeader());
        holder.tvQuoteDescription.setText(quote.getQuoteDescription());
    }

    @Override
    public int getItemCount() {
        return quoteArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvQuoteHeader;
        final TextView tvQuoteDescription;
        final ImageView ivQuoteIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuoteHeader = itemView.findViewById(R.id.tvQuoteHeader);
            tvQuoteDescription = itemView.findViewById(R.id.tvQuoteDescription);
            ivQuoteIcon = itemView.findViewById(R.id.ivQuoteIcon);
        }
    }
    public void update() {
        notifyDataSetChanged();
    }
}
