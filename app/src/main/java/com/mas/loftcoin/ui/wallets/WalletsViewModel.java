package com.mas.loftcoin.ui.wallets;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.mas.loftcoin.data.Coin;
import com.mas.loftcoin.data.CurrencyRepo;
import com.mas.loftcoin.data.Transaction;
import com.mas.loftcoin.data.Wallet;
import com.mas.loftcoin.data.WalletsRepo;
import com.mas.loftcoin.util.RxSchedulers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

class WalletsViewModel extends ViewModel {

    private final Subject<Integer> walletPosition = BehaviorSubject.createDefault(0);

    private final Observable<List<Wallet>> wallets;

    private final Observable<List<Transaction>> transactions;

    private final RxSchedulers schedulers;

    private final WalletsRepo walletsRepo;

    private final CurrencyRepo currencyRepo;

    @Inject
    WalletsViewModel(WalletsRepo walletsRepo, CurrencyRepo currencyRepo, RxSchedulers schedulers) {
        this.schedulers = schedulers;
        wallets = currencyRepo.currency()
                .switchMap(walletsRepo::wallets)
                .replay(1)
                .autoConnect();

        transactions = wallets
                .switchMap(wallets -> walletPosition
                        .map(wallets::get))
                .switchMap(walletsRepo::transactions)
                .replay(1)
                .autoConnect();
        this.walletsRepo = walletsRepo;
        this.currencyRepo = currencyRepo;
    }

    @NonNull
    Observable<List<Wallet>> wallets() {
        return wallets.observeOn(schedulers
                .main());
    }

    @NonNull
    Observable<List<Transaction>> transactions() {
        return transactions.observeOn(schedulers.main());
    }

    @NonNull
    Completable addWallet() {
        return wallets
                .firstOrError()
                .flatMap((list) -> Observable
                        .fromIterable(list)
                        .map(Wallet::coin)
                        .map(Coin::id)
                        .toList()
                )
                .flatMapCompletable((ids) -> currencyRepo
                        .currency()
                        .flatMapCompletable((c) -> walletsRepo.addWallet(c, ids))
                )
                .observeOn(schedulers.main());
    }

    void changeWallet(int position) {
        walletPosition.onNext(position);
    }
}
