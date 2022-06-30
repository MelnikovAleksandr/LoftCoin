package com.mas.loftcoin.util;

import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Test;

public class PercentFormatterTest {

    private PercentFormatter formatter;

    @Before
    public void setUp() throws Exception {
        formatter = new PercentFormatter();
    }

    @Test
    public void string_contains_exact_two_fractional_digits() {
        Truth.assertThat(formatter.format(1d)).isEqualTo("+1.0000%");
        Truth.assertThat(formatter.format(1.23456)).isEqualTo("+1.2346%");
        Truth.assertThat(formatter.format(1.2)).isEqualTo("+1.2000%");
    }
}