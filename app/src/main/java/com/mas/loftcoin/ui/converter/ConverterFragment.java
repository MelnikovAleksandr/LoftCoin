package com.mas.loftcoin.ui.converter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.mas.loftcoin.BaseComponent;
import com.mas.loftcoin.BuildConfig;
import com.mas.loftcoin.R;
import com.mas.loftcoin.databinding.FragmentConverterBinding;
import com.mas.loftcoin.util.ImageLoader;
import com.mas.loftcoin.widget.OutlineCircle;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import kotlin.Unit;

public class ConverterFragment extends Fragment {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final ConverterComponent component;

    private final ImageLoader imageLoader;

    private FragmentConverterBinding binding;

    private ConverterViewModel viewModel;

    @Inject
    public ConverterFragment(BaseComponent baseComponent, ImageLoader imageLoader) {
        component = DaggerConverterComponent.builder()
                .baseComponent(baseComponent)
                .build();
        this.imageLoader = imageLoader;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireParentFragment(), component.viewModelFactory())
                .get(ConverterViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentConverterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentConverterBinding.bind(view);

        final NavController navController = NavHostFragment.findNavController(this);

        OutlineCircle.apply(binding.logo1);
        OutlineCircle.apply(binding.logo2);

        disposable.add(viewModel.topCoins().subscribe());

        Observable<Unit> observable1 = RxView.clicks(binding.fromCoin);
        Observable<Unit> observable2 = RxView.clicks(binding.logo1);

        Observable<Unit> observable3 = RxView.clicks(binding.toCoin);
        Observable<Unit> observable4 = RxView.clicks(binding.logo2);

        Observable.merge(observable1, observable2).subscribe(v -> {
            final Bundle args = new Bundle();
            args.putInt(CoinsSheet.KEY_MODE, CoinsSheet.MODE_FROM);
            navController.navigate(R.id.coins_sheet, args);
        });

        Observable.merge(observable3, observable4).subscribe(v -> {
            final Bundle args = new Bundle();
            args.putInt(CoinsSheet.KEY_MODE, CoinsSheet.MODE_TO);
            navController.navigate(R.id.coins_sheet, args);
        });

        disposable.add(viewModel.fromCoin().subscribe(coin -> {
            binding.fromCoin.setText(coin.symbol());
            imageLoader.load(BuildConfig.IMG_ENDPOINT + coin.id() + ".png")
                    .into(binding.logo1);
        }));
        disposable.add(viewModel.toCoin().subscribe(coin -> {
            binding.toCoin.setText(coin.symbol());
            imageLoader.load(BuildConfig.IMG_ENDPOINT + coin.id() + ".png")
                    .into(binding.logo2);
        }));

        disposable.add(RxTextView.textChanges(binding.from).skipInitialValue().subscribe(viewModel::fromValue));

        disposable.add(RxTextView.textChanges(binding.to).subscribe(viewModel::toValue));

        disposable.add(viewModel.fromValue()
                .distinctUntilChanged()
                .subscribe(text -> {
                    binding.from.setText(text);
                    binding.from.setSelection(text.length());
                }));
        disposable.add(viewModel.toValue()
                .distinctUntilChanged()
                .subscribe(text -> {
                    binding.to.setText(text);
                }));
    }

    @Override
    public void onDestroyView() {
        disposable.clear();
        super.onDestroyView();
    }
}


