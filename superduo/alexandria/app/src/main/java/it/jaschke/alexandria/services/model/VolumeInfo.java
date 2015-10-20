package it.jaschke.alexandria.services.model;

import java.util.List;

/**
 * Created by pedrofraca on 19/10/15.
 */
public class VolumeInfo {
    private String title;
    private String subtitle;
    private List<String> authors;
    private String description;
    private List<String> categories;
    private ImageLinks imageLinks;

    public String title(){
        return title;
    }

    public String subtitle(){
        return subtitle;
    }

    public List<String> authors(){
        return authors;
    }

    public String description(){
        return description;
    }

    public List<String> categories(){
        return categories;
    }

    public ImageLinks imageLinks(){
        return imageLinks;
    }
}
