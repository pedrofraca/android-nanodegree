package io.github.pedrofraca.joketelling.backend;

/** The object model for the data we are sending through endpoints */
public class Joke {

    private String mJokeData;

    public String getData() {
        return mJokeData;
    }

    public void setData(String data) {
        mJokeData = data;
    }
}