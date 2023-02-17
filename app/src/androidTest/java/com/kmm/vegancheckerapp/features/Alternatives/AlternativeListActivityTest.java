package com.kmm.vegancheckerapp.features.Alternatives;

import android.content.Intent;
import android.os.Bundle;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.kmm.vegancheckerapp.R;
import com.kmm.vegancheckerapp.dao.ProductDAO;
import com.kmm.vegancheckerapp.model.Product;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.kmm.vegancheckerapp.features.Alternatives.RecyclerViewMatcher.withRecyclerView;

public class AlternativeListActivityTest {
     ProductDAO productDAO;
    String type ="Burger";
    ArrayList<Product> productArrayList;



    /*
    Code below is based on:
    Stakeoverflow answer to Question: "How to putExtra data using the newest ActivityScenarioRule/ActivityScenario?
    Jose Leles, Edited by: anotherdave,
    https://stackoverflow.com/a/57777912
            */
    static Intent intent;
    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(),AlternativeListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("PRODUCT_TYPE", "Burger");
        bundle.putString("Lottie", "Food");
        intent.putExtras(bundle);
    }
    @Rule
    public ActivityScenarioRule<AlternativeListActivity> activityScenarioRule = new ActivityScenarioRule<>(intent); //END
@Before
public void setUp() throws SQLException, IOException, ClassNotFoundException {
    productDAO = new ProductDAO();
    productArrayList = new ArrayList<>(productDAO.getVeganProductsByType(type));
}

    @Test
    public void onAppLaunchTest(){
        onView(withId(R.id.rcAlternatives)).check(matches(isDisplayed()));
    }

    @Test
    public void sizeArrayTest(){
    int size = productArrayList.size();
     onView(withId(R.id.rcAlternatives)).check(new RecyclerViewMatcher.RecyclerViewItemCountAssertion(size));
    }

    @Test
    public void contentsNameTest(){
        String productName = productArrayList.get(0).getProductBrand() + " " + productArrayList.get(0).getProductName();
        onView(withRecyclerView(R.id.rcAlternatives)
                .atPositionOnView(0, R.id.tvPName))
                .check(matches(withText(productName)));
}
@Test
    public void contentAvailableFrom(){
    String availableFrom = productArrayList.get(1).getAvailableFrom();
    String strAvailable= "Available From: " + availableFrom.toUpperCase();
    onView(withRecyclerView(R.id.rcAlternatives)
            .atPositionOnView(1, R.id.tvAvailableFrom))
            .check(matches(withText(strAvailable)));
}






    }



