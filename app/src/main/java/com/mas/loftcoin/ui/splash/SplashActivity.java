package com.mas.loftcoin.ui.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.mas.loftcoin.R;
import com.mas.loftcoin.ui.main.MainActivity;
import com.mas.loftcoin.ui.welcome.WelcomeActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Animation animationRotateCenterUP = AnimationUtils.loadAnimation(
                this, R.anim.rotate_center_clockwise);
        final Animation animationRotateCenterDown = AnimationUtils.loadAnimation(
                this, R.anim.rotate_center_counterclock);
        ImageView imageView1 = findViewById(R.id.start_top_corner);
        ImageView imageView2 = findViewById(R.id.start_bottom_corner);
        imageView1.startAnimation(animationRotateCenterUP);
        imageView2.startAnimation(animationRotateCenterDown);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        compositeDisposable.add(Completable.timer(3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() ->
                {
                    if (preferences.getBoolean(WelcomeActivity.KEY_SHOW_WELCOME, true)) {
                        startActivity(new Intent(this, WelcomeActivity.class));
                    } else {
                        startActivity(new Intent(this, MainActivity.class));
                    }
                }, throwable -> {
                    Toast.makeText(getApplicationContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }));

    }

    @Override
    protected void onStop() {
        compositeDisposable.dispose();
        super.onStop();
    }
}
