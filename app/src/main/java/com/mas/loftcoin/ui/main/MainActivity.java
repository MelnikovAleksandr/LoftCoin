package com.mas.loftcoin.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;

import com.mas.loftcoin.R;
import com.mas.loftcoin.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        setContentView(binding.getRoot());



        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_fragments);
        assert navHostFragment != null;
        final NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
        NavigationUI.setupWithNavController(binding.toolbar, navController, new AppBarConfiguration
                .Builder(binding.bottomNavigation.getMenu())
                .build());
    }
}
