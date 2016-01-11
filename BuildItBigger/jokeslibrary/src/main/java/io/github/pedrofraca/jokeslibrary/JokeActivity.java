package io.github.pedrofraca.jokeslibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by fracaped on 11/01/16.
 */
public class JokeActivity extends AppCompatActivity {

    private static final String JOKE_TEXT_EXTRA = "JOKE_TEXT_EXTRA";

    /**
     * Method to create a intent to be passed to the activity.
     * @param jokeText The text containing the joke
     * @return Intent ready to be launched
     */
    public static Intent createIntent(Activity callerActivity, String jokeText){
        Intent jokeActivityIntent = new Intent(callerActivity,JokeActivity.class);
        jokeActivityIntent.putExtra(JOKE_TEXT_EXTRA,jokeText);
        return jokeActivityIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        TextView jokeText = (TextView) findViewById(R.id.joke_text);


    }
}
