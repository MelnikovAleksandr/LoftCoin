package com.mas.loftcoin.ui.welcome;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.mas.loftcoin.R;
import com.mas.loftcoin.ui.main.MainActivity;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class WelcomeActivityTest {

    @Test
    public void open_main_if_button_begin_pressed() {
        ActivityScenario.launch(WelcomeActivity.class);
        Intents.init();
        onView(withId(R.id.btn_start)).perform(click());
        Intents.intended(IntentMatchers.hasComponent(MainActivity.class.getName()));

    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }
}
