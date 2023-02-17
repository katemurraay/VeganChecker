package com.kmm.vegancheckerapp.features.Scanning;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.kmm.vegancheckerapp.R;
import com.kmm.vegancheckerapp.ToastMatcher;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class CameraActivityTest {


    @Rule
    public ActivityScenarioRule<CameraActivity> mActivityRule = new ActivityScenarioRule<>(
            CameraActivity.class);

    @Before
    public void setUp()  {
        ActivityScenario.launch(CameraActivity.class);

    }

    @Test
   public void checkSearchDialogAppears(){
        onView(withId(R.id.btnSearch)).perform(click());
        onView(withId(R.id.etSearchName)).check(matches(isDisplayed()));
    }


    @Test
    public void checkSearchDialogCancelClick(){
        onView(withId(R.id.btnSearch)).perform(click());
        onView(withId(R.id.btnBack)).perform(click());
        onView(withId(R.id.etSearchName)).check(doesNotExist());

    }




@Test
    public void checkSearchDialogContinueWithNullClick(){
    onView(withId(R.id.btnSearch)).perform(click());
    onView(withId(R.id.btnContinue)).perform(click());
    onView(withText(R.string.no_results)).inRoot(new ToastMatcher())
            .check(matches(withText("No Product Found")));


    }

@Test
    public void checkManualSearchIncorrectInput(){
    onView(withId(R.id.btnSearch)).perform(click());
    String strInput = "123";
    onView(withId(R.id.etSearchName)).perform(typeText(strInput));
    onView(withId(R.id.btnContinue)).perform(click());
    onView(withText(R.string.no_results)).inRoot(new ToastMatcher())
            .check(matches(withText("No Product Found")));

}


    @Test
    public void checkManualSearchCorrectInput(){
        onView(withId(R.id.btnSearch)).perform(click());
        String strInput = "Plant Menu";
        onView(withId(R.id.etSearchName)).perform(typeText(strInput), closeSoftKeyboard());
        onView(withId(R.id.btnContinue)).perform(click());
       onView(withId(R.id.imgResult)).check(matches(isDisplayed()));


    }
}