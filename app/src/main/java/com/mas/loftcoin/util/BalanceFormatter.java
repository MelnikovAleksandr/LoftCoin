package com.mas.loftcoin.util;

import androidx.annotation.NonNull;

import com.mas.loftcoin.data.Transaction;
import com.mas.loftcoin.data.Wallet;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BalanceFormatter implements Formatter<Wallet> {

    @Inject
    BalanceFormatter() {

    }

    @NonNull
    @Override
    public String format(@NonNull Wallet value) {
        final DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance();
        final DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
        symbols.setCurrencySymbol(value.coin().symbol());
        format.setDecimalFormatSymbols(symbols);
        return format.format(value.balance());

    }

    @NonNull
    public String format(@NonNull Transaction transaction) {
        final DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance();
        final DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
        symbols.setCurrencySymbol(transaction.coin().symbol());
        format.setDecimalFormatSymbols(symbols);
        return format.format(transaction.amount());

    }
}
