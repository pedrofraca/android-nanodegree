package it.jaschke.alexandria.services;

import it.jaschke.alexandria.services.model.BookResponse;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by pedrofraca on 19/10/15.
 */
public interface BookAPI {
    @GET("/books/v1/volumes")
    BookResponse getBook(@Query("q") String isbn);
}
