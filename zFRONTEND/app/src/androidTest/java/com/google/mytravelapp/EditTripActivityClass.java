package com.google.mytravelapp;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.mytravelapp.activities.EditTripActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EditTripActivityClass {
    @Rule
    public ActivityScenarioRule<EditTripActivity> activityRule =
            new ActivityScenarioRule<>(EditTripActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

}
