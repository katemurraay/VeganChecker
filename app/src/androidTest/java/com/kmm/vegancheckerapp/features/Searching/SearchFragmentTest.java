package com.kmm.vegancheckerapp.features.Searching;

import android.app.Activity;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.kmm.vegancheckerapp.R;
import com.kmm.vegancheckerapp.ToastMatcher;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


public class SearchFragmentTest {


@Rule
  public ActivityScenarioRule<SplashScreenActivity> mActivtyScenario = new ActivityScenarioRule<>(SplashScreenActivity.class);


/* Code below is based on:
StackOverflow Answer to Question: "How to Access Activity from ActivityScenarioRule",
tim.k,
https://stackoverflow.com/a/66170451
 */
private <T extends Activity> T getActivity(ActivityScenarioRule<T> activityScenarioRule) {
    AtomicReference<T> activityRef = new AtomicReference<>();
    activityScenarioRule.getScenario().onActivity(activityRef::set);
    return activityRef.get();
} //END

Activity activity;
@Before
public void setUp(){
    activity = getActivity(mActivtyScenario);
}

    @Test
    public void onCreateView() {
        onView(withId(R.id.search_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.btnFood)).check(matches(isDisplayed()));
        onView(withId(R.id.btnAlcohol)).check(matches(isDisplayed()));
         onView(withId(R.id.lvFood)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.ibFood)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        onView(withId(R.id.lvAlcohol)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.ibAlcohol)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void onFoodButtonClick() {
        onView(withId(R.id.btnFood)).perform(click());
        onView(withId(R.id.lvFood)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.ibFood)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.ibFood)).perform(click());
        onView(withId(R.id.lvFood)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.ibFood)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));

    }

    @Test
    public void listOptionsTest() {
        onView(withId(R.id.search_bar)).perform(typeText("Mayo"), closeSoftKeyboard());

        /* Code below is based on:
        Web Article: How to Test Autocomplete Text Box with Espresso,
        QA Automated,
        http://www.qaautomated.com/2016/10/how-to-test-autocomplete-text-box-with.html
         */
        onView(withText("Mayonnaise"))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

    }

    @Test
    public void ClickListOptionsTest() {

        onView(withId(R.id.search_bar)).perform(typeText("Burger"), closeSoftKeyboard());
        onView(withText("Burger"))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                .perform(click());
        onView(withText("Vegan Burger Options")).check(matches(isDisplayed()));


    }

    @Test
    public void list2OptionsTest() {
        Activity activity = getActivity(mActivtyScenario);
        onView(withId(R.id.search_bar)).perform(typeText("Ice"), closeSoftKeyboard());

        onView(withId(R.id.search_bar)).perform(replaceText("Bre"), closeSoftKeyboard());
        onView(withText("Bread"))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));


    }

    @Test
    public void Click2ListOptionsTest()  {
        onView(withId(R.id.search_bar)).perform(typeText("Ice"), closeSoftKeyboard());
        onView(withId(R.id.search_bar)).perform(clearText());
        onView(withId(R.id.search_bar)).perform(typeText("Bread"), closeSoftKeyboard());
        onView(withText("Bread"))
                .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                .perform(click()); //END
        onView(withText("Vegan Bread Options")).check(matches(isDisplayed()));


    }



    @Test
    public void imeActionButtonTest(){
        onView(withId(R.id.search_bar)).perform(typeText("Bread"), closeSoftKeyboard());
        onView(withId(R.id.search_bar)).perform(pressImeActionButton());
        onView(withText("Vegan Bread Options")).check(matches(isDisplayed()));

    }

    @Test
    public void imeActionButtonTest2(){
        onView(withId(R.id.search_bar)).perform(typeText("Beer"), closeSoftKeyboard());
        onView(withId(R.id.search_bar)).perform(pressImeActionButton());
        onView(withText("Vegan Beer Options")).check(matches(isDisplayed()));

    }

    @Test
    public void imeActionButtonToastTest(){
        onView(withId(R.id.search_bar)).perform(typeText("12344"), closeSoftKeyboard());
        onView(withId(R.id.search_bar)).perform(pressImeActionButton());
        onView(withText(R.string.no_results)).inRoot(new ToastMatcher())
                .check(matches(withText("No Product Found")));

    }
    @Test
    public void imeActionButtonNullTest(){
        onView(withId(R.id.search_bar)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.search_bar)).perform(pressImeActionButton());
        onView(withText(R.string.no_results)).inRoot(new ToastMatcher())
                .check(matches(withText("No Product Found")));

    }

    @Test
    public void onAlcoholButtonClickTest1() {
        onView(withId(R.id.btnAlcohol)).perform(click());
        onView(withId(R.id.lvAlcohol)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.ibAlcohol)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));


    }

    @Test
    public void onAlcoholButtonClickTest2(){
        onView(withId(R.id.btnAlcohol)).perform(click());
        onView(withId(R.id.lvAlcohol)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.ibAlcohol)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.ibAlcohol)).perform(click());
        onView(withId(R.id.lvAlcohol)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.ibAlcohol)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void alcoholListClickTest(){
        onView(withId(R.id.btnAlcohol)).perform(click());
        onView(withId(R.id.lvAlcohol)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.ibAlcohol)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withText("Cognac")).perform(click());
        onView(withText("Vegan Cognac Options")).check(matches(isDisplayed()));
    }






}