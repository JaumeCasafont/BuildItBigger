package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.jcr.javajokes.Joker;
import com.jcr.jokeactivity.JokeActivity;
import com.udacity.gradle.builditbigger.IdlingResource.SimpleIdlingResource;

import static com.jcr.jokeactivity.JokeActivity.EXTRA_JOKE;


public class MainActivity extends AppCompatActivity implements
        EndpointsAsyncTask.OnFinishedCallback,
        OnButtonClickListener {

    private MainActivityFragment mainActivityFragment;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityFragment = (MainActivityFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onButtonClick() {
        mainActivityFragment.setLoadingVisibility(View.VISIBLE);
        mainActivityFragment.enableButton(false);

        new EndpointsAsyncTask(this).execute(mIdlingResource);
    }

    @Override
    public void onFinished(String joke) {
        mainActivityFragment.setLoadingVisibility(View.GONE);
        mainActivityFragment.enableButton(true);

        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra(EXTRA_JOKE, joke);
        startActivity(intent);
    }
}
