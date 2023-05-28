package com.google.mytravelapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.mytravelapp.activities.LoginActivity;
import com.google.mytravelapp.activities.RegisterActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {
    @Rule
    public ActivityScenarioRule<RegisterActivity> activityRule =
            new ActivityScenarioRule<>(RegisterActivity.class);

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
        onView(withId(R.id.emailAddressReg))
                .check(matches(isDisplayed()));
        onView(withId(R.id.usernameReg))
                .check(matches(isDisplayed()));
        onView(withId(R.id.passwordReg))
                .check(matches(isDisplayed()));
        onView(withId(R.id.registerButtonReg))
                .check(matches(isDisplayed()));
        onView(withId(R.id.repassReg))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testNavigationToLoginActivity() {
        onView(withId(R.id.emailAddressReg)).perform(typeText("test22@test.com"), closeSoftKeyboard());
        onView(withId(R.id.usernameReg)).perform(typeText("testUser"), closeSoftKeyboard());
        onView(withId(R.id.passwordReg)).perform(typeText("A1#bcdefg"), closeSoftKeyboard());
        onView(withId(R.id.repassReg)).perform(typeText("A1#bcdefg"), closeSoftKeyboard());

        onView(withId(R.id.registerButtonReg)).perform(click());

        intended(hasComponent(LoginActivity.class.getName()));
    }

}
