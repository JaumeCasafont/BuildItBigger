package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * Created by Jaume on 29/04/2018.
 */

public abstract class ButtonFragment extends Fragment {

    private static final String SPINNER_STATE_KEY = "mSpinner";
    private static final String BUTTON_STATE_KEY = "button";

    protected OnButtonClickListener mCallback;
    protected Button mButton;
    private ProgressBar mSpinner;

    private int spinnerVisibility = View.GONE;
    private boolean buttonEnabled = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnButtonClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            spinnerVisibility = savedInstanceState.getInt(SPINNER_STATE_KEY);
            buttonEnabled = savedInstanceState.getBoolean(BUTTON_STATE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mButton = root.findViewById(R.id.tell_joke_button);
        mButton.setEnabled(buttonEnabled);

        mSpinner = root.findViewById(R.id.progressBar1);
        mSpinner.setVisibility(spinnerVisibility);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });

        return root;
    }

    protected abstract void onButtonClick();

    public void setLoadingVisibility(int visibility) {
        mSpinner.setVisibility(visibility);
    }

    public void enableButton(boolean enable) {
        mButton.setEnabled(enable);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SPINNER_STATE_KEY, mSpinner.getVisibility());
        outState.putBoolean(BUTTON_STATE_KEY, mButton.isEnabled());
    }
}
