package com.mas.loftcoin.ui.currency;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mas.loftcoin.R;
import com.mas.loftcoin.data.CurrencyRepo;
import com.mas.loftcoin.data.CurrencyRepoImpl;
import com.mas.loftcoin.databinding.DialogCurrencyBinding;
import com.mas.loftcoin.ui.main.MainActivity;

public class CurrencyDialog extends AppCompatDialogFragment {

    private DialogCurrencyBinding binding;

    private CurrencyRepo currencyRepo;

    private CurrencyAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currencyRepo = new CurrencyRepoImpl(requireContext());
        adapter = new CurrencyAdapter();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogCurrencyBinding.inflate(getLayoutInflater());

        return new MaterialAlertDialogBuilder(requireActivity(), R.style.AppTheme_DialogWindow)
                .setTitle(R.string.choose_currency)
                .setView(binding.getRoot())
                .create();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.recycler.setAdapter(adapter);
        currencyRepo.availableCurrencies().observe(this, adapter::submitList);
    }


    @Override
    public void onDestroy() {
        binding.recycler.setAdapter(null);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_item_rate_text2);
        super.onDestroy();
    }
}
