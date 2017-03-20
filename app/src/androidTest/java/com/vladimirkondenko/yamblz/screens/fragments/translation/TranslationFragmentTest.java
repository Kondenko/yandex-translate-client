package com.vladimirkondenko.yamblz.screens.fragments.translation;

import android.app.FragmentTransaction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.vladimirkondenko.yamblz.R;
import com.vladimirkondenko.yamblz.screens.main.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressMenuKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class TranslationFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        onView(withChild(withId(R.id.item_bottomnav_translation))).perform(pressMenuKey());
        /*
        MainActivity activity = mActivityRule.getActivity();
        activity.setFragment(new TranslationFragment());
        */
    }

    @Test
    public void testClearTextButton() {
        int inputFieldId = R.id.edittext_translation_input;
        onView(withId(inputFieldId)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.button_translation_clear_input)).perform(click());
        onView(withId(inputFieldId)).check(matches(withText("")));
    }

}