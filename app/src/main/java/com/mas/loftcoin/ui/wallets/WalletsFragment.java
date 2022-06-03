package com.mas.loftcoin.ui.wallets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mas.loftcoin.databinding.FragmentWalletsBinding;

public class WalletsFragment extends Fragment {

    public WalletsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentWalletsBinding binding = FragmentWalletsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
