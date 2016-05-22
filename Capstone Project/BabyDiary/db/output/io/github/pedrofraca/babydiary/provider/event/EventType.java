package io.github.pedrofraca.babydiary.provider.event;

/**
 * Possible values for the {@code type} column of the {@code event} table.
 */
public enum EventType {
    /**
     * 
     */
    PHOTO,

    /**
     * 
     */
    VIDEO,

    /**
     * 
     */
    AUDIO,

    /**
     * 
     */
    TEXT,

    /**
     * 
     */
    MEASURE,

    /**
     * 
     */
    VACCINE,

    /**
     * Value to use when neither male nor female
     */
    OTHER,

}