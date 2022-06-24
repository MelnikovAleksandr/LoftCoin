package com.mas.loftcoin;

import android.content.Context;

import com.mas.loftcoin.data.CoinsRepo;
import com.mas.loftcoin.data.CurrencyRepo;
import com.mas.loftcoin.data.WalletsRepo;
import com.mas.loftcoin.util.ImageLoader;
import com.mas.loftcoin.util.Notifier;
import com.mas.loftcoin.util.RxSchedulers;

public interface BaseComponent {

    Context context();

    CoinsRepo coinsRepo();

    CurrencyRepo currencyRepo();

    WalletsRepo walletRepo();

    ImageLoader imageLoader();

    RxSchedulers schedulers();

    Notifier notifier();
}
