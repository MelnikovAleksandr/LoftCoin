package com.mas.loftcoin.ui.converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mas.loftcoin.databinding.FragmentConverterBinding;

public class ConverterFragment extends Fragment {

    public ConverterFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        final FragmentConverterBinding binding = FragmentConverterBinding
                .inflate(inflater, container, false);
        return binding.getRoot();
    }
}
