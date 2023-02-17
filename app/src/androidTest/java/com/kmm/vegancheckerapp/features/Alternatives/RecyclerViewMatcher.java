package com.kmm.vegancheckerapp.features.Alternatives;

import android.content.res.Resources;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Objects;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RecyclerViewMatcher {
/* Code below is based on:
Github Repository: Espresso-samples,
dannyroa, LICENSE: https://github.com/dannyroa/espresso-samples/blob/master/LICENSE,
https://github.com/dannyroa/espresso-samples/tree/master/RecyclerView/app/src/androidTest/java/com/dannyroa/espresso_samples/recyclerview
 */

    private final int recyclerViewId;

    public RecyclerViewMatcher(int recyclerViewId) {
        this.recyclerViewId = recyclerViewId;
    }



    public Matcher<View> atPositionOnView(final int position, final int targetViewId) {

        return new TypeSafeMatcher<View>() {
            Resources resources = null;
            View childView;

            public void describeTo(Description description) {
                String idDescription = Integer.toString(recyclerViewId);
                if (this.resources != null) {
                    try {
                        idDescription = this.resources.getResourceName(recyclerViewId);
                    } catch (Resources.NotFoundException var4) {
                        idDescription = String.format("%s (resource name not found)",
                                recyclerViewId);
                    }
                }

                description.appendText("with id: " + idDescription);
            }

            public boolean matchesSafely(View view) {

                this.resources = view.getResources();

                if (childView == null) {
                    RecyclerView recyclerView =
                             view.getRootView().findViewById(recyclerViewId);
                    if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                        childView = Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position)).itemView;
                    }
                    else {
                        return false;
                    }
                }

                if (targetViewId == -1) {
                    return view == childView;
                } else {
                    View targetView = childView.findViewById(targetViewId);
                    return view == targetView;
                }

            }
        };
    }


    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {

        return new RecyclerViewMatcher(recyclerViewId);
    } //END

          /*
    Code Below is based on:
    StackOverflow Answer to: 'How to count RecyclerView items with Espresso',
    nenick, edited by: Willi Mentzel,
https://stackoverflow.com/a/37339656
     */

    public static class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final int expectedCount;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), is(expectedCount));
        }//END


    }



}
