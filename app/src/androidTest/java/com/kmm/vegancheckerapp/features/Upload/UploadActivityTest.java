package com.kmm.vegancheckerapp.features.Upload;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.kmm.vegancheckerapp.R;
import com.kmm.vegancheckerapp.ToastMatcher;
import com.kmm.vegancheckerapp.features.Searching.SplashScreenActivity;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UploadActivityTest {
    String strBarcode = "501109600580";
    String strIngredients = "Wheat";
    String strTest="UI Test";


@Rule
public ActivityScenarioRule<SplashScreenActivity> mActivtyScenario = new ActivityScenarioRule<>(SplashScreenActivity.class);

@Before
public void setUp(){
    onView(withId(R.id.nav_user)).perform(click());
    onView(withId(R.id.add_upload)).perform(click());
    }

@Test
    public void AA_ShowToastBarcodeAlreadyEnter(){
    onView(withId(R.id.etBarcode)).perform(typeText("5011096005881"));
    onView(withId(R.id.spCategory)).perform(click());
    onView(withText("Food")).perform(click());
    onView(withId(R.id.btnStep1)).perform(click());
    onView(withText(R.string.barcode_indb)).inRoot(new ToastMatcher())
            .check(matches(withText("This Product is already stored")));
}

@Test
    public void AB_showToastOnNullBarcode(){
    onView(withId(R.id.etBarcode)).perform(typeText(""));
    onView(withId(R.id.btnStep1)).perform(click());
    onView(withText(R.string.please_enter)).inRoot(new ToastMatcher())
            .check(matches(withText("Please Enter the Required Field")));
}

@Test public void AC_CategoryNullTest(){
    onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
    onView(withId(R.id.btnStep1)).perform(click());
    onView(withText(R.string.please_enter)).inRoot(new ToastMatcher())
            .check(matches(withText("Please Enter the Required Field")));
}

    @Test
    public void AD_Step1CompleteTest(){
        onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
        onView(withId(R.id.spCategory)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.btnStep1)).perform(click());
      onView(withId(R.id.layoutStep2)).check(matches(isDisplayed()));
    }




@Test
public void BA_step2ComponentsAppearTest(){
    onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
    onView(withId(R.id.spCategory)).perform(click());
    onView(withText("Food")).perform(click());
    onView(withId(R.id.btnStep1)).perform(click());
    onView(withId(R.id.etResultIngredients)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    onView(withId(R.id.btnUploadIngredients)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    onView(withId(R.id.btnStep2)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
}
  @Test
    public void BB_ShowToastIngredientsContainAnimal(){
        onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
      onView(withId(R.id.spCategory)).perform(click());
      onView(withText("Food")).perform(click());
      onView(withId(R.id.btnStep1)).perform(click());
        onView(withId(R.id.etResultIngredients)).perform(typeText("trigo, queso"), closeSoftKeyboard());
       onView(withId(R.id.btnStep2)).perform(click());
        onView(withText(R.string.contains_animal)).inRoot(new ToastMatcher())
                .check(matches(withText("Product contains Animal Ingredients")));
    }

    @Test
    public void BC_showToastOnNullIngredients(){
        onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
        onView(withId(R.id.spCategory)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.btnStep1)).perform(click());
        onView(withId(R.id.etResultIngredients)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.btnStep2)).perform(click());
        onView(withText(R.string.please_enter)).inRoot(new ToastMatcher())
                .check(matches(withText("Please Enter the Required Field")));
    }
    @Test
    public void BD_step3AppearsTest(){
        onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
        onView(withId(R.id.spCategory)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.btnStep1)).perform(click());

        onView(withId(R.id.etResultIngredients)).perform(typeText(strIngredients), closeSoftKeyboard());
        onView(withId(R.id.btnStep2)).perform(click());
        onView(withId(R.id.layoutStep3)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
    @Test
    public void CA_step3ComponentsAppearTest(){
        onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
        onView(withId(R.id.spCategory)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.btnStep1)).perform(click());

        onView(withId(R.id.etResultIngredients)).perform(typeText(strIngredients), closeSoftKeyboard());
        onView(withId(R.id.btnStep2)).perform(click());
        onView(withId(R.id.etBrand)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.etName)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.spType)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.btnStep3)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void CB_ShowToastEmptyNameTest(){
        onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
        onView(withId(R.id.spCategory)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.btnStep1)).perform(click());

        onView(withId(R.id.etResultIngredients)).perform(typeText(strIngredients), closeSoftKeyboard());
        onView(withId(R.id.btnStep2)).perform(click());
        onView(withId(R.id.etBrand)).perform(typeText(strTest), closeSoftKeyboard());
        onView(withId(R.id.spType)).perform(click());
        onView(withText("Cereal")).perform(click());
        onView(withId(R.id.btnStep3)).perform(click());
        onView(withText(R.string.please_enter)).inRoot(new ToastMatcher())
                .check(matches(withText("Please Enter the Required Field")));
    }

    @Test
    public void CC_showToastOnEmptyBrandTest(){
        onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
        onView(withId(R.id.spCategory)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.btnStep1)).perform(click());
        onView(withId(R.id.etResultIngredients)).perform(typeText(strIngredients), closeSoftKeyboard());
        onView(withId(R.id.btnStep2)).perform(click());
        onView(withId(R.id.etName)).perform(typeText(strTest),closeSoftKeyboard());
        onView(withId(R.id.etBrand)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.spType)).perform(click());
        onView(withText("Cereal")).perform(click());
        onView(withId(R.id.btnStep3)).perform(click());
        onView(withText(R.string.please_enter)).inRoot(new ToastMatcher())
                .check(matches(withText("Please Enter the Required Field")));
    }
    @Test
    public void CD_showToastOnEmptyTypeTest(){
        onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
        onView(withId(R.id.spCategory)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.btnStep1)).perform(click());
        onView(withId(R.id.etResultIngredients)).perform(typeText(strIngredients), closeSoftKeyboard());
        onView(withId(R.id.btnStep2)).perform(click());
        onView(withId(R.id.etName)).perform(typeText(strTest),closeSoftKeyboard());
        onView(withId(R.id.etBrand)).perform(typeText(strTest), closeSoftKeyboard());
        onView(withId(R.id.btnStep3)).perform(click());
        onView(withText(R.string.please_enter)).inRoot(new ToastMatcher())
                .check(matches(withText("Please Enter the Required Field")));
    }

    @Test
    public void CE_showToastOnEmptyStep3Test(){
        onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
        onView(withId(R.id.spCategory)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.btnStep1)).perform(click());
        onView(withId(R.id.etResultIngredients)).perform(typeText(strIngredients), closeSoftKeyboard());
        onView(withId(R.id.btnStep2)).perform(click());
        onView(withId(R.id.etName)).perform(typeText(""),closeSoftKeyboard());
        onView(withId(R.id.etBrand)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.spType)).perform(click());
        onView(withText("Cereal")).perform(click());
        onView(withId(R.id.btnStep3)).perform(click());
        onView(withText(R.string.please_enter)).inRoot(new ToastMatcher())
                .check(matches(withText("Please Enter the Required Field")));
    }
    @Test
    public void CF_step4AppearsTest(){
        onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
        onView(withId(R.id.spCategory)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.btnStep1)).perform(click());
        onView(withId(R.id.etResultIngredients)).perform(typeText(strIngredients), closeSoftKeyboard());
        onView(withId(R.id.btnStep2)).perform(click());
        onView(withId(R.id.etName)).perform(typeText(strTest),closeSoftKeyboard());
        onView(withId(R.id.etBrand)).perform(typeText(strTest), closeSoftKeyboard());
        onView(withId(R.id.spType)).perform(click());
        onView(withText("Cereal")).perform(click());
        onView(withId(R.id.btnStep3)).perform(click());
        onView(withId(R.id.layoutStep4)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));


    }

    @Test
    public void DA_step4ComponentsAppearTest(){
        onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
        onView(withId(R.id.spCategory)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.btnStep1)).perform(click());
        onView(withId(R.id.etResultIngredients)).perform(typeText(strIngredients), closeSoftKeyboard());
        onView(withId(R.id.btnStep2)).perform(click());
        onView(withId(R.id.etName)).perform(typeText(strTest),closeSoftKeyboard());
        onView(withId(R.id.etBrand)).perform(typeText(strTest), closeSoftKeyboard());
        onView(withId(R.id.spType)).perform(click());
        onView(withText("Cereal")).perform(click());
        onView(withId(R.id.btnStep3)).perform(click());

        onView(withId(R.id.rdoNoVeganSign)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.rdoYesVeganSign)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.cbHB)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.cbAldi)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.cbLidl)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.cbTesco)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.cbDunnes)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.cbOther)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.cbIceland)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.cbSupervalu)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.btnStep4)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    }

    @Test
    public void DB_showToastOnEmptyStep4Test(){
        onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
        onView(withId(R.id.spCategory)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.btnStep1)).perform(click());
        onView(withId(R.id.etResultIngredients)).perform(typeText(strIngredients), closeSoftKeyboard());
        onView(withId(R.id.btnStep2)).perform(click());
        onView(withId(R.id.etName)).perform(typeText(strTest),closeSoftKeyboard());
        onView(withId(R.id.etBrand)).perform(typeText(strTest), closeSoftKeyboard());
        onView(withId(R.id.spType)).perform(click());
        onView(withText("Cereal")).perform(click());
        onView(withId(R.id.btnStep3)).perform(click());
        onView(withId(R.id.btnStep4)).perform(click());
        onView(withText(R.string.please_choose)).inRoot(new ToastMatcher())
                .check(matches(withText("Please Choose an Option")));
    }
    @Test
    public void DC_showToastOnEmptyCheckboxStep4Test(){
        onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
        onView(withId(R.id.spCategory)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.btnStep1)).perform(click());

        onView(withId(R.id.etResultIngredients)).perform(typeText(strIngredients), closeSoftKeyboard());
        onView(withId(R.id.btnStep2)).perform(click());
        onView(withId(R.id.etName)).perform(typeText(strTest),closeSoftKeyboard());
        onView(withId(R.id.etBrand)).perform(typeText(strTest), closeSoftKeyboard());
        onView(withId(R.id.spType)).perform(click());
        onView(withText("Cereal")).perform(click());
        onView(withId(R.id.btnStep3)).perform(click());
        onView(withId(R.id.rdoYesVeganSign)).perform(click());
        onView(withId(R.id.btnStep4)).perform(click());
        onView(withText(R.string.please_choose)).inRoot(new ToastMatcher())
                .check(matches(withText("Please Choose an Option")));
    }

    @Test
    public void DD_checkedOtherTest(){
        onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
        onView(withId(R.id.spCategory)).perform(click());
        onView(withText("Alcohol")).perform(click());
        onView(withId(R.id.btnStep1)).perform(click());
        onView(withId(R.id.etResultIngredients)).perform(typeText(strIngredients), closeSoftKeyboard());
        onView(withId(R.id.btnStep2)).perform(click());
        onView(withId(R.id.etName)).perform(typeText(strTest),closeSoftKeyboard());
        onView(withId(R.id.etBrand)).perform(typeText(strTest), closeSoftKeyboard());
        onView(withId(R.id.spType)).perform(click());
        onView(withText("Cognac")).perform(click());
        onView(withId(R.id.btnStep3)).perform(click());
        onView(withId(R.id.rdoYesVeganSign)).perform(click());
        onView(withId(R.id.cbOther)).perform(click());
        onView(withId(R.id.cbOther)).check(matches(isChecked()));
        onView(withId(R.id.etSpecify)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void DE_showToastOnEmptyOtherTest(){
        onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
        onView(withId(R.id.spCategory)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.btnStep1)).perform(click());
        onView(withId(R.id.etResultIngredients)).perform(typeText(strIngredients), closeSoftKeyboard());
        onView(withId(R.id.btnStep2)).perform(click());
        onView(withId(R.id.etName)).perform(typeText(strTest),closeSoftKeyboard());
        onView(withId(R.id.etBrand)).perform(typeText(strTest), closeSoftKeyboard());
        onView(withId(R.id.spType)).perform(click());
        onView(withText("Cereal")).perform(click());
        onView(withId(R.id.btnStep3)).perform(click());
        onView(withId(R.id.rdoYesVeganSign)).perform(click());
        onView(withId(R.id.cbOther)).perform(click());
        onView(withId(R.id.btnStep4)).perform(click());
        onView(withText(R.string.please_choose)).inRoot(new ToastMatcher())
                .check(matches(withText("Please Choose an Option")));
    }

    @Test
    public void E_successfulUploadTest(){
        onView(withId(R.id.etBarcode)).perform(typeText(strBarcode));
        onView(withId(R.id.spCategory)).perform(click());
        onView(withText("Food")).perform(click());
        onView(withId(R.id.btnStep1)).perform(click());
        onView(withId(R.id.etResultIngredients)).perform(typeText(strIngredients), closeSoftKeyboard());
        onView(withId(R.id.btnStep2)).perform(click());
        onView(withId(R.id.etName)).perform(typeText(strTest),closeSoftKeyboard());
        onView(withId(R.id.etBrand)).perform(typeText(strTest), closeSoftKeyboard());
        onView(withId(R.id.spType)).perform(click());
        onView(withText("Cereal")).perform(click());
        onView(withId(R.id.btnStep3)).perform(click());
        onView(withId(R.id.rdoYesVeganSign)).perform(click());
        onView(withId(R.id.cbDunnes)).perform(click());
        onView(withId(R.id.btnStep4)).perform(click());
        onView(withText("UI Test UI Test")).check(matches(isDisplayed()));
    }

}