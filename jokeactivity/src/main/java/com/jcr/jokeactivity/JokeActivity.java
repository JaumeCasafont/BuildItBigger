package com.jcr.jokeactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "joke";

    private TextView mJokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        mJokeTextView = findViewById(R.id.joke_tv);

        tellJoke(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        tellJoke(intent);
    }

    private void tellJoke(Intent intent){
        if (intent.getExtras() != null && intent.getExtras().containsKey(EXTRA_JOKE)) {
            mJokeTextView.setText(intent.getStringExtra(EXTRA_JOKE));
        }
    }
}
