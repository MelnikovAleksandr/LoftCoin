package com.mas.loftcoin;

import android.content.Context;

import com.mas.loftcoin.data.CoinsRepo;
import com.mas.loftcoin.data.CurrencyRepo;
import com.mas.loftcoin.util.ImageLoader;

public interface BaseComponent {

    Context context();

    CoinsRepo coinsRepo();

    CurrencyRepo currencyRepo();

    ImageLoader imageLoader();
}
