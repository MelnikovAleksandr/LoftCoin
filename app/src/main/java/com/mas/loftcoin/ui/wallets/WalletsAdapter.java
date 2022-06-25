package com.mas.loftcoin.ui.wallets;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mas.loftcoin.BuildConfig;
import com.mas.loftcoin.data.Wallet;
import com.mas.loftcoin.databinding.WalletBinding;
import com.mas.loftcoin.util.BalanceFormatter;
import com.mas.loftcoin.util.ImageLoader;
import com.mas.loftcoin.util.PriceFormatter;
import com.mas.loftcoin.widget.OutlineCircle;

import java.util.Objects;

import javax.inject.Inject;

class WalletsAdapter extends ListAdapter<Wallet, WalletsAdapter.ViewHolder> {

    private final PriceFormatter priceFormatter;

    private LayoutInflater inflater;

    private final BalanceFormatter balanceFormatter;

    private final ImageLoader imageLoader;

    @Inject
    WalletsAdapter(PriceFormatter priceFormatter, BalanceFormatter balanceFormatter, ImageLoader imageLoader) {
        super(new DiffUtil.ItemCallback<Wallet>() {
            @Override
            public boolean areItemsTheSame(@NonNull Wallet oldItem, @NonNull Wallet newItem) {
                return Objects.equals(oldItem.uid(), newItem.uid());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Wallet oldItem, @NonNull Wallet newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
        this.priceFormatter = priceFormatter;
        this.balanceFormatter = balanceFormatter;
        this.imageLoader = imageLoader;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(WalletBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Wallet wallet = getItem(position);
        holder.binding.symbol.setText(wallet.coin().symbol());
        holder.binding.balance1Wallet.setText(balanceFormatter.format(wallet));
        final double balance = wallet.balance() * wallet.coin().price();
        holder.binding.balance2Wallet.setText(priceFormatter.format(wallet.coin().currencyCode(), balance));
        imageLoader
                .load(BuildConfig.IMG_ENDPOINT + wallet.coin().id() + ".png")
                .into(holder.binding.logoWallet);


    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final WalletBinding binding;

        ViewHolder(@NonNull WalletBinding binding) {
            super(binding.getRoot());
            binding.getRoot().setClipToOutline(true);
            this.binding = binding;
            OutlineCircle.apply(binding.logoWallet);
        }
    }
}
