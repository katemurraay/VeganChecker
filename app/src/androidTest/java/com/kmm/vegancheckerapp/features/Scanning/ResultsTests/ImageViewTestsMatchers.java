package com.kmm.vegancheckerapp.features.Scanning.ResultsTests;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ImageViewTestsMatchers {

    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }


}









/* Code below is based on:
Article: Android UI Test — Espresso Matcher for ImageView,
Daniele Bottillo, October 2015, Medium,
https://medium.com/@dbottillo/android-ui-test-espresso-matcher-for-imageview-1a28c832626f
 */
class DrawableMatcher extends TypeSafeMatcher<View> {


    @Override
    public void describeTo(Description description) {

    }

    private final int expectedId;

    public DrawableMatcher(int resourceId) {
        super(View.class);
        this.expectedId = resourceId;
    }

    @Override
    protected boolean matchesSafely(View target) {
        if (!(target instanceof ImageView)){
            return false;
        }
        ImageView imageView = (ImageView) target;
        if (expectedId < 0){
            return imageView.getDrawable() == null;
        }
        Resources resources = target.getContext().getResources();
        Drawable expectedDrawable = resources.getDrawable(expectedId);
        if (expectedDrawable == null) {
            return false;
        }
        Bitmap bitmap = getBitmap (imageView.getDrawable());
        Bitmap otherBitmap = getBitmap(expectedDrawable);
        return bitmap.sameAs(otherBitmap);
    }
    private Bitmap getBitmap(Drawable drawable){
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    //END
}


