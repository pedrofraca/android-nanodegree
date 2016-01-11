package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import java.util.concurrent.ExecutionException;

/**
 * Created by fracaped on 11/01/16.
 */
public class GetJokeTest extends AndroidTestCase {
    public void testVerifyGetJokeAsyncTask() throws ExecutionException, InterruptedException {
        String value = new GetJokeAsyncTask(null).execute().get();
        if(value==null){
            fail("Check that the server is running.");
        }
        assertFalse(value.isEmpty());
    }
}
