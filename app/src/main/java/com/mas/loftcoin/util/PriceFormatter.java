package com.mas.loftcoin.util;

import android.os.Build;

import androidx.annotation.NonNull;

import java.util.Locale;


public class PriceFormatter implements Formatter<Double> {

    @NonNull
    @Override
    public String format(@NonNull Double value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return android.icu.text.NumberFormat.getCurrencyInstance(Locale.US).format(value);
        } else {
            return java.text.NumberFormat.getCurrencyInstance(Locale.US).format(value);
        }
    }
}
