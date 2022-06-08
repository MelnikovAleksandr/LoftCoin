package com.mas.loftcoin.ui.rates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mas.loftcoin.R;
import com.mas.loftcoin.databinding.FragmentRateBinding;
import com.mas.loftcoin.ui.main.MainActivity;

public class RatesFragment extends Fragment {

    private FragmentRateBinding binding;

    private RatesAdapter adapter;

    private RatesViewModel viewModel;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RatesViewModel.class);
        adapter = new RatesAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_item_rate_text2);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_rate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentRateBinding.bind(view);
        binding.recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.recycler.swapAdapter(adapter, false);
        binding.recycler.setHasFixedSize(true);
        viewModel.coins().observe(getViewLifecycleOwner(), (coins) -> {
            adapter.submitList(coins);
        });
        swipeRefreshLayout = view.findViewById(R.id.rateSwipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refresh();
            binding.recycler.setAdapter(adapter);
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onDestroy() {
        binding.recycler.swapAdapter(null, false);
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_rates, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
