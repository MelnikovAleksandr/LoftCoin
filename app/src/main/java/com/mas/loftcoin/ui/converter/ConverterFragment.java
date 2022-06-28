package com.mas.loftcoin.ui.converter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mas.loftcoin.R;
import com.mas.loftcoin.databinding.FragmentConverterBinding;
import com.mas.loftcoin.ui.main.MainActivity;

import javax.inject.Inject;

public class ConverterFragment extends Fragment {

    @Inject
    public ConverterFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentConverterBinding binding = FragmentConverterBinding.inflate(inflater, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_item_converter_text);
        return binding.getRoot();
    }
}
