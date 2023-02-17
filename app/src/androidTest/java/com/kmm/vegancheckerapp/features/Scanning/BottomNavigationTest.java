package com.kmm.vegancheckerapp.features.Scanning;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kmm.vegancheckerapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


public class BottomNavigationTest {
/*
Code below is based on:
Android Developer Docs Guide: "Espresso setup instructions",
Google,
https://developer.android.com/training/testing/espresso/setup#java */
   @Rule
   public ActivityTestRule<NavigationActivity> activityRule =
           new ActivityTestRule<>(NavigationActivity.class); //END


    @Before
    public void setUp()  {
        ActivityScenario.launch(NavigationActivity.class);
    }





/* Code below is based on:
StackOverflow Answer to Question:  "How assert a BottomNavigationItemView is checked with Expresso",
arekolek,
https://stackoverflow.com/a/46707014

 */
    public static Matcher<View> hasCheckedItem(final int id) {
        return new BoundedMatcher<View, BottomNavigationView>(BottomNavigationView.class) {
            Set<Integer> checkedIds = new HashSet<>();
            boolean itemFound = false;
            boolean triedMatching = false;

            @Override public void describeTo(Description description) {
                if (!triedMatching) {
                    description.appendText("BottomNavigationView");
                    return;
                }

                description.appendText("BottomNavigationView to have a checked item with id=");
                description.appendValue(id);
                if (itemFound) {
                    description.appendText(", but selection was=");
                    description.appendValue(checkedIds);
                } else {
                    description.appendText(", but it doesn't have an item with such id");
                }
            }

            @Override protected boolean matchesSafely(BottomNavigationView navigationView) {
                triedMatching = true;

                final Menu menu = navigationView.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    final MenuItem item = menu.getItem(i);
                    if (item.isChecked()) {
                        checkedIds.add(item.getItemId());
                    }
                    if (item.getItemId() == id) {
                        itemFound = true;
                    }
                }
                return checkedIds.contains(id);
            }
        };
    }




    @Test
    public void verifySearchIsSelected() {
         onView(withId(R.id.nav_search)).perform(click());
        onView(withId(R.id.bottom_navigation)).check(matches(hasCheckedItem(R.id.nav_search)));

    }
    @Test
    public void verifyUserIsSelected() {
        onView(withId(R.id.nav_user)).perform(click());
        onView(withId(R.id.bottom_navigation)).check(matches(hasCheckedItem(R.id.nav_user)));

    }




    @Test
    public void verifyReturnToSearch(){
        onView(withId(R.id.nav_search)).perform(click());
        onView(withId(R.id.nav_search)).perform(click());
        onView(withId(R.id.bottom_navigation)).check(matches(hasCheckedItem(R.id.nav_search)));
    }

    @Test
    public void verifyReturnToUser() {
        onView(withId(R.id.nav_search)).perform(click());

        onView(withId(R.id.nav_user)).perform(click());
        onView(withId(R.id.bottom_navigation)).check(matches(hasCheckedItem(R.id.nav_user)));
    }


    @Test
    public void verifyDoubleSearchClick() {
        onView(withId(R.id.nav_search)).perform(doubleClick());
        onView(withId(R.id.bottom_navigation)).check(matches(hasCheckedItem(R.id.nav_search)));

    }


    @Test
    public void verifyDoubleUserClick() {
        onView(withId(R.id.nav_user)).perform(doubleClick());
        onView(withId(R.id.bottom_navigation)).check(matches(hasCheckedItem(R.id.nav_user)));
    }  //END








}