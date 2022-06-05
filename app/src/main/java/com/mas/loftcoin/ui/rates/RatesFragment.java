package com.mas.loftcoin.ui.rates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mas.loftcoin.R;
import com.mas.loftcoin.databinding.FragmentRateBinding;
import com.mas.loftcoin.ui.main.MainActivity;

public class RatesFragment extends Fragment {

    public RatesFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentRateBinding binding = FragmentRateBinding.inflate(inflater, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_item_rate_text2);
        return binding.getRoot();
    }
}
