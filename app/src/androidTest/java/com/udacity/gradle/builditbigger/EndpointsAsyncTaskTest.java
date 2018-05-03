package com.udacity.gradle.builditbigger;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.gradle.builditbigger.IdlingResource.SimpleIdlingResource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Jaume on 01/05/2018.
 */
@RunWith(AndroidJUnit4.class)
public class EndpointsAsyncTaskTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private SimpleIdlingResource mIdlingResource;

    @Before
    public void setupAsyncTask() {
        mIdlingResource = (SimpleIdlingResource)mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void testEndpointResponse() {
        mActivityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityTestRule.getActivity().onButtonClick();
            }
        });

        onView(withId(R.id.joke_tv)).check(matches(withText("This is totally a funny joke")));
    }
}
