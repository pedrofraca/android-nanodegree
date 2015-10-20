package it.jaschke.alexandria.services;

import it.jaschke.alexandria.services.model.VolumeInfo;

/**
 * Created by pedrofraca on 19/10/15.
 */
public interface BookServiceListener {
    void onBookFound(String ean, VolumeInfo info);
    void onBookFoundError(Exception e);
}
