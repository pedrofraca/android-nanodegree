package it.jaschke.alexandria.services.model;

import java.util.ArrayList;
import java.util.Arrays;
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

    public void title(String theTitle){
        title = theTitle;
    }

    public void subtitle(String theSubtitle) {
        subtitle = theSubtitle;
    }

    public void authors(List<String> theAutors){
        authors=theAutors;
    }

    public void description(String theDescription){
        description=theDescription;
    }

    public void categories(List<String> theCategories){
        categories = theCategories;
    }

    public void imageLinks(ImageLinks theImageLinks){
        imageLinks = theImageLinks;
    }

    public void authors(String theAuthors) {
        if(theAuthors!=null){
            String[] authorsArr = theAuthors.split(",");
            authors = Arrays.asList(authorsArr);
        } else {
            authors = new ArrayList<>(0);
        }

    }

    public void categories(String theCategories) {
        if(theCategories!=null){
            String[] authorsArr = theCategories.split(",");
            categories = Arrays.asList(authorsArr);
        } else {
            categories = new ArrayList<>(0);
        }

    }
}
