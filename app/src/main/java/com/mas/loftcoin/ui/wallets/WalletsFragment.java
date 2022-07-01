package com.mas.loftcoin.ui.wallets;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.mas.loftcoin.BaseComponent;
import com.mas.loftcoin.R;
import com.mas.loftcoin.databinding.FragmentWalletsBinding;
import com.mas.loftcoin.widget.RecyclerViews;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class WalletsFragment extends Fragment {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final WalletsComponent component;

    private FragmentWalletsBinding binding;

    private WalletsViewModel viewModel;

    private SnapHelper walletsSnapHelper;

    private WalletsAdapter adapter;

    private TransactionAdapter transactionAdapter;

    @Inject
    public WalletsFragment(BaseComponent baseComponent) {
        component = DaggerWalletsComponent.builder()
                .baseComponent(baseComponent)
                .build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, component.viewModelFactory())
                .get(WalletsViewModel.class);
        adapter = component.walletsAdapter();
        transactionAdapter = component.transactionAdapter();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        binding = FragmentWalletsBinding.bind(view);
        walletsSnapHelper = new PagerSnapHelper();
        walletsSnapHelper.attachToRecyclerView(binding.recyclerWallets);

        final TypedValue value = new TypedValue();
        view.getContext().getTheme().resolveAttribute(R.attr.walletCardWidth, value, true);
        final DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
        final int padding = (int) (displayMetrics.widthPixels - value.getDimension(displayMetrics)) / 2;
        binding.recyclerWallets.setPadding(padding, 0, padding, 0);
        binding.recyclerWallets.setClipToPadding(false);
        binding.recyclerWallets.setHasFixedSize(true);

        binding.recyclerWallets.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.HORIZONTAL, false));
        binding.recyclerWallets.addOnScrollListener(new CarouselScroller());

        disposable.add(RecyclerViews
                .onSnap(binding.recyclerWallets, walletsSnapHelper)
                .subscribe(position -> viewModel.changeWallet(position)));

        binding.recyclerWallets.setAdapter(adapter);

        disposable.add(viewModel.wallets().subscribe(adapter::submitList));
        disposable.add(viewModel.wallets().map(List::isEmpty).subscribe((isEmpty) -> {
            binding.walletCardInFragment.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            binding.recyclerWallets.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        }));

        binding.transactions.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.transactions.setAdapter(transactionAdapter);
        binding.transactions.setHasFixedSize(true);

        disposable.add(viewModel.transactions().subscribe(transactionAdapter::submitList));


    }

    @Override
    public void onDestroyView() {
        walletsSnapHelper.attachToRecyclerView(null);
        binding.recyclerWallets.setAdapter(null);
        binding.transactions.setAdapter(null);
        disposable.clear();
        super.onDestroyView();
    }

    private static class CarouselScroller extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            final int centerX = (recyclerView.getLeft() + recyclerView.getRight()) / 2;
            for (int i = 0; i < recyclerView.getChildCount(); ++i) {
                final View child = recyclerView.getChildAt(i);
                final int childCenterX = (child.getLeft() + child.getRight()) / 2;
                final float childOffset = Math.abs(centerX - childCenterX) / (float) centerX;
                float factor = (float) (Math.pow(0.8, childOffset));
                child.setScaleX(factor);
                child.setScaleY(factor);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_wallets, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.menu_add_wallet == item.getItemId()) {
            disposable.add(viewModel.addWallet().subscribe());

        } else if (R.id.menu_del_wallet == item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }
}













