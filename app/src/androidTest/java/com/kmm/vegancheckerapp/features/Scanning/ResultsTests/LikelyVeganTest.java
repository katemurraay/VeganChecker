package com.kmm.vegancheckerapp.features.Scanning.ResultsTests;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.kmm.vegancheckerapp.R;
import com.kmm.vegancheckerapp.features.Scanning.ResultActivity;
import com.kmm.vegancheckerapp.model.Product;
import com.kmm.vegancheckerapp.utils.IConstants;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.kmm.vegancheckerapp.features.Scanning.ResultsTests.ImageViewTestsMatchers.withDrawable;

public class LikelyVeganTest {
    String[] ingredients = {"Vegan"};
    Product product = new Product(1, "Porridge Oats","", "4088600056128", "Cereal", "Kavanagh's", ingredients, "Likely Vegan", "", null, "Food" );

    String name = product.getProductBrand() + " " + product.getProductName();
    String production ="Production: " + product.getProductProduction();
    String tvIngredients ="Ingredients: Vegan";


    /*
    Code below is based on:
    Stakeoverflow answer to Question: "How to putExtra data using the newest ActivityScenarioRule/ActivityScenario?
    Jose Leles, Edited by: anotherdave,
    https://stackoverflow.com/a/57777912
            */

    static Intent intent;



    static {

        intent = new Intent(ApplicationProvider.getApplicationContext(), ResultActivity.class);
        intent.putExtra("LOADERID", IConstants.BARCODELOADERID);
        intent.putExtra("RESULT" , "4088600056128");


    }

    @Rule
    public ActivityScenarioRule<ResultActivity> activityScenarioRule = new ActivityScenarioRule<>(intent); //END



@Test
public void imageTest() {
onView(withId(R.id.imgResult)).check(matches(withDrawable(R.drawable.ic_question)));
}

@Test
    public void textViewTest(){
    onView(withId(R.id.tvName)).check(matches(withText(name)));
    onView(withId(R.id.tvProduction)).check(matches(withText(production)));
    onView(withId(R.id.tvResult)).check(matches(withText(product.getProductProduction())));
    onView(withId(R.id.tvIngredients)).check(matches(withText(tvIngredients)));
}

@Test
public void tvNameTest(){
    onView(withId(R.id.tvName)).check(matches(withText(name)));
}

@Test
public void tvProductionTest(){
    onView(withId(R.id.tvProduction)).check(matches(withText(production)));
}

@Test
public void tvIngredientsTest(){
    onView(withId(R.id.tvIngredients)).check(matches(withText(tvIngredients)));
}

@Test
public void tvResultTest(){
    onView(withId(R.id.tvResult)).check(matches(withText(product.getProductProduction())));
}

@Test
    public void checkHiddenElementsTest(){
    onView(withId(R.id.tvValidatedBy)).check(matches(withText("")));
    onView(withId(R.id.tvContains)).check(matches(withText("")));


}

    @Test
    public void viewAlternativeVisibilityTest(){
        onView(withId(R.id.btnViewAlternatives)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void viewAlternativesTest(){
        onView(withId(R.id.btnViewAlternatives)).perform(click());
        onView(withId(R.id.rcAlternatives)).check(matches(isDisplayed()));
    }




}
