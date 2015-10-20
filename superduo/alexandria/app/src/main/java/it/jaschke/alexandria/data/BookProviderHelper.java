package it.jaschke.alexandria.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import it.jaschke.alexandria.services.model.VolumeInfo;

/**
 * Created by pedrofraca on 19/10/15.
 */
public class BookProviderHelper {

    public void writeBackBook(ContentResolver contentResolver,String ean, VolumeInfo volumeInfo) {
        ContentValues values= new ContentValues();
        values.put(AlexandriaContract.BookEntry._ID, ean);
        values.put(AlexandriaContract.BookEntry.TITLE, volumeInfo.title());
        if(volumeInfo.imageLinks().thubmnail()!=null){
            values.put(AlexandriaContract.BookEntry.IMAGE_URL,volumeInfo.imageLinks().thubmnail());
        } else {
            values.put(AlexandriaContract.BookEntry.IMAGE_URL,volumeInfo.imageLinks().smallThumbnail());
        }
        values.put(AlexandriaContract.BookEntry.SUBTITLE, volumeInfo.subtitle());
        values.put(AlexandriaContract.BookEntry.DESC, volumeInfo.description());
        writeBackAuthors(contentResolver, ean, volumeInfo.authors());
        writeBackCategories(contentResolver,ean,volumeInfo.categories());
        contentResolver.insert(AlexandriaContract.BookEntry.CONTENT_URI,values);
    }

    private void writeBackAuthors(ContentResolver contentResolver,String ean, List<String> authors) {
        ContentValues values= new ContentValues();
        for (String author : authors) {
            values.put(AlexandriaContract.AuthorEntry._ID, ean);
            values.put(AlexandriaContract.AuthorEntry.AUTHOR, author);
            contentResolver.insert(AlexandriaContract.AuthorEntry.CONTENT_URI, values);
            values= new ContentValues();
        }
    }

    private void writeBackCategories(ContentResolver contentResolver,String ean,  List<String> categories) {
        ContentValues values= new ContentValues();
        for (String category:categories) {
            values.put(AlexandriaContract.CategoryEntry._ID, ean);
            values.put(AlexandriaContract.CategoryEntry.CATEGORY, category);
            contentResolver.insert(AlexandriaContract.CategoryEntry.CONTENT_URI, values);
            values= new ContentValues();
        }
    }

    public boolean bookAlreadyOnDB(ContentResolver contentResolver,String ean){
        if(ean.length()!=13){
            return false;
        }

        Cursor bookEntry = contentResolver.query(
                AlexandriaContract.BookEntry.buildBookUri(Long.parseLong(ean)),
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        if(bookEntry.getCount()>0){
            bookEntry.close();
            return true;
        }
        bookEntry.close();
        return false;
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    public void deleteBook(ContentResolver contentResolver,String ean) {
        if(ean!=null) {
            contentResolver.delete(AlexandriaContract.BookEntry.buildBookUri(Long.parseLong(ean)), null, null);
        }
    }
}
