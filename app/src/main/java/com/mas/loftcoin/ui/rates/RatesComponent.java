package com.mas.loftcoin.ui.rates;

import androidx.lifecycle.ViewModelProvider;

import com.mas.loftcoin.BaseComponent;
import com.mas.loftcoin.util.ViewModelModule;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        RatesModule.class,
        ViewModelModule.class
}, dependencies = {
        BaseComponent.class
})
abstract class RatesComponent {

    abstract ViewModelProvider.Factory viewModelFactory();

    abstract RatesAdapter ratesAdapter();
}
