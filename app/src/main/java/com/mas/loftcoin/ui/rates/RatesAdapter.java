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
import com.mas.loftcoin.util.ImageLoader;
import com.mas.loftcoin.util.PercentFormatter;
import com.mas.loftcoin.util.PriceFormatter;
import com.mas.loftcoin.widget.OutlineCircle;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class RatesAdapter extends ListAdapter<Coin, RatesAdapter.ViewHolder> {

    private static final int ODD_POSITION = 1;

    private static final int EVEN_POSITION = 0;

    private LayoutInflater inflater;

    private final PriceFormatter priceFormatter;

    private final ImageLoader imageLoader;

    private final PercentFormatter percentFormatter;

    @Inject
    RatesAdapter(PriceFormatter priceFormatter, PercentFormatter percentFormatter, ImageLoader imageLoader) {
        super(new DiffUtil.ItemCallback<Coin>() {
            @Override
            public boolean areItemsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return oldItem.id() == newItem.id();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return Objects.equals(oldItem, newItem);
            }

            @Override
            public Object getChangePayload(@NonNull Coin oldItem, @NonNull Coin newItem) {
                return newItem;
            }
        });
        this.percentFormatter = percentFormatter;
        this.priceFormatter = priceFormatter;
        this.imageLoader = imageLoader;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).id();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RateBinding.inflate(inflater, parent, false));
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Coin coin = getItem(position);
        holder.binding.symbol.setText(coin.symbol());
        holder.binding.price.setText(priceFormatter.format(coin.currencyCode(), coin.price()));
        holder.binding.change.setText(percentFormatter.format(coin.change24()));
        if (getItem(position).change24() > 0) {
            int upColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.weird_green);
            holder.binding.change.setTextColor(upColor);
        } else {
            int downColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.watermelon);
            holder.binding.change.setTextColor(downColor);
        }
        if (getItemViewType(position) == ODD_POSITION) {
            holder.itemView.setBackgroundResource(R.color.dark_three);
        } else if (getItemViewType(position) == EVEN_POSITION) {
            holder.itemView.setBackgroundResource(R.color.dark_two);
        }
        imageLoader
                .load(BuildConfig.IMG_ENDPOINT + coin.id() + ".png")
                .into(holder.binding.logo);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            final Coin coin = (Coin) payloads.get(0);
            holder.binding.price.setText(priceFormatter.format(coin.currencyCode(), coin.price()));
            holder.binding.change.setText(percentFormatter.format(coin.change24()));
        }
        super.onBindViewHolder(holder, position, payloads);
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
            OutlineCircle.apply(binding.logo);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return EVEN_POSITION;
        } else {
            return ODD_POSITION;
        }
    }
}
