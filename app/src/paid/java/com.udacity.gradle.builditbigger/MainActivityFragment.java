package com.udacity.gradle.builditbigger;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends ButtonFragment {

    public MainActivityFragment() {
    }

    @Override
    protected void onButtonClick() {
        mCallback.onButtonClick();
    }
}
