package com.mas.loftcoin.ui.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import com.mas.loftcoin.ui.converter.CoinsSheet;
import com.mas.loftcoin.ui.converter.ConverterFragment;
import com.mas.loftcoin.ui.currency.CurrencyDialog;
import com.mas.loftcoin.ui.rates.RatesFragment;
import com.mas.loftcoin.ui.wallets.WalletsFragment;
import com.mas.loftcoin.util.LoftFragmentFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainModule {

    @Binds
    abstract FragmentFactory fragmentFactory(LoftFragmentFactory impl);

    @Binds
    @IntoMap
    @ClassKey(RatesFragment.class)
    abstract Fragment ratesFragment(RatesFragment impl);

    @Binds
    @IntoMap
    @ClassKey(WalletsFragment.class)
    abstract Fragment walletFragment(WalletsFragment impl);

    @Binds
    @IntoMap
    @ClassKey(ConverterFragment.class)
    abstract Fragment converterFragment(ConverterFragment impl);

    @Binds
    @IntoMap
    @ClassKey(CurrencyDialog.class)
    abstract Fragment currencyDialog(CurrencyDialog impl);

    @Binds
    @IntoMap
    @ClassKey(CoinsSheet.class)
    abstract Fragment coinsSheet(CoinsSheet impl);

}
