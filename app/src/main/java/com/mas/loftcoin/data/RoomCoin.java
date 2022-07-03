package com.mas.loftcoin.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.auto.value.AutoValue;

@Entity
@AutoValue
abstract class RoomCoin implements Coin {

    static RoomCoin create(String name,
                           String symbol,
                           double change24,
                           int rank,
                           double price,
                           String currencyCode,
                           int id) {
        return new AutoValue_RoomCoin(name, symbol, change24, rank, price, currencyCode, id);
    }

    @Override
    @PrimaryKey
    @AutoValue.CopyAnnotations
    public abstract int id();
}
