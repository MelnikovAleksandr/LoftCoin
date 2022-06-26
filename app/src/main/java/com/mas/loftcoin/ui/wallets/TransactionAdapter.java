package com.mas.loftcoin.ui.wallets;

import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mas.loftcoin.R;
import com.mas.loftcoin.data.Transaction;
import com.mas.loftcoin.databinding.TransactionBinding;
import com.mas.loftcoin.util.BalanceFormatter;
import com.mas.loftcoin.util.PriceFormatter;

import javax.inject.Inject;

class TransactionAdapter extends ListAdapter<Transaction, TransactionAdapter.ViewHolder> {

    private final PriceFormatter priceFormatter;

    private final BalanceFormatter balanceFormatter;

    private LayoutInflater inflater;

    @Inject
    TransactionAdapter(PriceFormatter priceFormatter, BalanceFormatter balanceFormatter) {
        super(new DiffUtil.ItemCallback<Transaction>() {
            @Override
            public boolean areItemsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
                return false;
            }
        });
        this.priceFormatter = priceFormatter;
        this.balanceFormatter = balanceFormatter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(TransactionBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Transaction transaction = getItem(position);
        holder.binding.amount1.setText(balanceFormatter.format(transaction));
        final double fiatAmount = transaction.amount() * transaction.coin().price();
        if (getItem(position).amount() > 0) {
            int upColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.weird_green);
            holder.binding.amount2.setTextColor(upColor);
        } else {
            int downColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.watermelon);
            holder.binding.amount2.setTextColor(downColor);
        }
        if (transaction.amount() < 0) {
            holder.binding.transactionIcon.setImageResource(R.drawable.ic_shape_down);
        } else {
            holder.binding.transactionIcon.setImageResource(R.drawable.ic_shape_up);
        }

        holder.binding.amount2.setText(priceFormatter.format(transaction.coin().currencyCode(), fiatAmount));
        holder.binding.date.setText(DateFormat.getDateFormat(inflater.getContext()).format(transaction.createAt()));

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TransactionBinding binding;

        ViewHolder(@NonNull TransactionBinding binding) {
            super(binding.getRoot());
            binding.getRoot().setClipToOutline(true);
            this.binding = binding;
        }
    }
}
