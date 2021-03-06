package com.mas.loftcoin.util;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.NumberFormat;
import java.util.Locale;

@RunWith(AndroidJUnit4.class)
public class PriceFormatterTest {

    private Context context;

    private PriceFormatter formatter;

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        formatter = new PriceFormatter(context);
    }

    @Test
    public void format_EUR() {
        Truth.assertThat(formatter
                .format("EUR", 1.23)).isEqualTo(NumberFormat
                .getCurrencyInstance(Locale.GERMANY).format(1.23));
    }

    @Test
    public void format_RUB() {
        Truth.assertThat(formatter
                .format("RUB", 1.23)).isEqualTo(NumberFormat
                .getCurrencyInstance(new Locale("ru", "RU")).format(1.23));
    }
}