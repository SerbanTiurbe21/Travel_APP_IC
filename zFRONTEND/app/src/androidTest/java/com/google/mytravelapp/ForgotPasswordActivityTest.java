package com.google.mytravelapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.mytravelapp.activities.ForgotPasswordActivity;
import com.google.mytravelapp.activities.LoginActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ForgotPasswordActivityTest {
    @Rule
    public ActivityScenarioRule<ForgotPasswordActivity> activityRule =
            new ActivityScenarioRule<>(ForgotPasswordActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testValidPasswordChange() {
        onView(withId(R.id.emailAddressForgot)).perform(replaceText("serbantiurbe@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.oldPasswordForgot)).perform(replaceText("Serbanel20012"), closeSoftKeyboard());
        onView(withId(R.id.newPasswordForgot)).perform(replaceText("A1#bcdefg"), closeSoftKeyboard());
        onView(withId(R.id.reNewPasswordForgot)).perform(replaceText("A1#bcdefg"), closeSoftKeyboard());

        onView(withId(R.id.resetButtonForgot)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(LoginActivity.class.getName()));
    }
}
