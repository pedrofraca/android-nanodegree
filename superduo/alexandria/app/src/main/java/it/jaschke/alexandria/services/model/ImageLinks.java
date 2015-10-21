package it.jaschke.alexandria.services.model;

/**
 * Created by pedrofraca on 19/10/15.
 */
public class ImageLinks {
    private String smallThumbnail;
    private String thumbnail;

    public String smallThumbnail(){
        return smallThumbnail;
    }

    public String thubmnail(){
        return thumbnail;
    }

    public void thumbnail (String theThumbnail){
        if(theThumbnail==null){
            thumbnail="";
        } else {
            thumbnail=theThumbnail;
        }
    }
}
