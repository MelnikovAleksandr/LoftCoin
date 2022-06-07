package com.mas.loftcoin.ui.wallets;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mas.loftcoin.databinding.WalletBinding;

public class WalletsAdapter extends RecyclerView.Adapter<WalletsAdapter.ViewHolder> {

    private LayoutInflater inflater;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(WalletBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        inflater = LayoutInflater.from(recyclerView.getContext());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(@NonNull WalletBinding walletBinding) {
            super(walletBinding.getRoot());
        }
    }
}
