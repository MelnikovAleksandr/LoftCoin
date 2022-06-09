package com.mas.loftcoin.ui.rates;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mas.loftcoin.BuildConfig;
import com.mas.loftcoin.R;
import com.mas.loftcoin.data.Coin;
import com.mas.loftcoin.databinding.RateBinding;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class RatesAdapter extends ListAdapter<Coin, RatesAdapter.ViewHolder> {

    private LayoutInflater inflater;

    RatesAdapter() {
        super(new DiffUtil.ItemCallback<Coin>() {
            @Override
            public boolean areItemsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return oldItem.id() == newItem.id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RateBinding.inflate(inflater, parent, false));
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.symbol.setText(getItem(position).symbol());
        holder.binding.price.setText(String.format("%,.6f", getItem(position).price()));
        holder.binding.change.setText(String.format("%+.4f%%", getItem(position).change24()));
        if (getItem(position).change24() > 0) {
            int upColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.weird_green);
            holder.binding.change.setTextColor(upColor);
        } else {
            int downColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.watermelon);
            holder.binding.change.setTextColor(downColor);
        }
        if (position % 2 == 1) {
            holder.itemView.setBackgroundResource(R.color.dark_three);

        } else {
            holder.itemView.setBackgroundResource(R.color.dark_two);
        }
        Picasso.get().load(BuildConfig.IMG_ENDPOINT + getItem(position).id() + ".png")
                .placeholder(R.drawable.ic_baseline_circle_24)
                .transform(new CircularTransformation())
                .error(R.drawable.ic_round_error_24)
                .into(holder.binding.logo);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final RateBinding binding;

        public ViewHolder(@NonNull RateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
