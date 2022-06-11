package com.mas.loftcoin.ui.rates;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mas.loftcoin.data.CmcCoinsRepo;
import com.mas.loftcoin.data.Coin;
import com.mas.loftcoin.data.CoinsRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import timber.log.Timber;

public class RatesViewModel extends ViewModel {

    private final MutableLiveData<List<Coin>> coins = new MutableLiveData<>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final CoinsRepo repo;

    private Future<?> future;

    public RatesViewModel() {
        repo = new CmcCoinsRepo();
        refresh();
    }

    @NonNull
    LiveData<List<Coin>> coins() {
        return coins;
    }

    final void refresh() {
        future = executor.submit(() -> {
            try {
                coins.postValue(new ArrayList<>(repo.listing("USD")));
            } catch (IOException e) {
                Timber.d(e, "Error Message");
            }
        });
    }

    @Override
    protected void onCleared() {
        if (future != null) {
            future.cancel(true);
        }
    }
}
