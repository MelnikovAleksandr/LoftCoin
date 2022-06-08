package com.mas.loftcoin.data;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import java.io.IOException;
import java.util.List;

public interface CoinsRepo {

    @NonNull
    @WorkerThread
    List<? extends Coin> listing(@NonNull String currency) throws IOException;
}
