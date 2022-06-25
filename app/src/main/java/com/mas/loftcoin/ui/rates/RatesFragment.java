package com.mas.loftcoin.ui.rates;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.mas.loftcoin.BaseComponent;
import com.mas.loftcoin.R;
import com.mas.loftcoin.databinding.FragmentRateBinding;
import com.mas.loftcoin.ui.main.MainActivity;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class RatesFragment extends Fragment {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final RatesComponent component;

    private FragmentRateBinding binding;

    private RatesAdapter adapter;

    private RatesViewModel viewModel;


    @Inject
    public RatesFragment(BaseComponent baseComponent) {
        component = DaggerRatesComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, component.viewModelFactory()).get(RatesViewModel.class);
        adapter = component.ratesAdapter();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_item_rate_text2);
        return inflater.inflate(R.layout.fragment_rate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        binding = FragmentRateBinding.bind(view);
        binding.recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.recycler.swapAdapter(adapter, false);
        binding.recycler.setHasFixedSize(true);
        binding.rateSwipeRefresh.setOnRefreshListener(viewModel::refresh);
        binding.rateSwipeRefresh.setColorSchemeColors(Color.MAGENTA, Color.CYAN, Color.GREEN);
        disposable.add(viewModel.coins().subscribe(adapter::submitList));
        disposable.add(viewModel.onError().subscribe(e ->
                Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", v -> viewModel.retry())
                        .show()));
        disposable.add(viewModel.isRefreshing().subscribe(binding.rateSwipeRefresh::setRefreshing));

    }

    @Override
    public void onDestroy() {
        binding.recycler.swapAdapter(null, false);
        disposable.clear();
        super.onDestroy();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.currencyDialog == item.getItemId()) {
            NavHostFragment
                    .findNavController(this)
                    .navigate(R.id.currencyDialog);
            return true;
        } else if (R.id.menu_sort == item.getItemId()) {
            viewModel.switchSortingOrder();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_rates, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
