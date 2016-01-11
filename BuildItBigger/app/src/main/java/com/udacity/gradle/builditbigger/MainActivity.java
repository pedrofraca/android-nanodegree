package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import io.github.pedrofraca.jokeslibrary.JokeActivity;


public class MainActivity extends AppCompatActivity implements GetJokeAsyncTaskListener {

    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressView = findViewById(R.id.progress_view);
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

    public void tellJoke(View view){
        new GetJokeAsyncTask(this).execute();
        mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTokenReceived(String joke) {
        startActivity(JokeActivity.createIntent(this, joke));
        mProgressView.setVisibility(View.GONE);
    }

    @Override
    public void onTokenError(Exception e) {
        mProgressView.setVisibility(View.GONE);
        Log.d("Error",e.getMessage());
    }
}
