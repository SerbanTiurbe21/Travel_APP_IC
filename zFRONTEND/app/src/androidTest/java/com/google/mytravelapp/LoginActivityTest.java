package com.google.mytravelapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.mytravelapp.activities.ForgotPasswordActivity;
import com.google.mytravelapp.activities.LoginActivity;
import com.google.mytravelapp.activities.RegisterActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testViewsAreDisplayed() {
        onView(withId(R.id.registerButtonLogin))
                .check(matches(isDisplayed()));
        onView(withId(R.id.loginButtonLogin))
                .check(matches(isDisplayed()));
        onView(withId(R.id.emailAddressLogin))
                .check(matches(isDisplayed()));
        onView(withId(R.id.passwordMain))
                .check(matches(isDisplayed()));
        onView(withId(R.id.forgotPassLogin))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testRegisterButtonAction() {
        onView(withId(R.id.registerButtonLogin)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        intended(hasComponent(RegisterActivity.class.getName()));
    }

    @Test
    public void testForgotPassAction() {
        onView(withId(R.id.forgotPassLogin)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        intended(hasComponent(ForgotPasswordActivity.class.getName()));
    }
}
