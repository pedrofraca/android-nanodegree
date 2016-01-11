package com.udacity.gradle.builditbigger;

/**
 * Created by fracaped on 11/01/16.
 */
public interface GetJokeAsyncTaskListener {
    void onTokenReceived(String token);
    void onTokenError(Exception e);
}
