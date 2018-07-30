package com.webstormcomputers.udacitynewsapp;

public class News {
    private final String mTitle;
    private final String mNameOfSection;
    private String mAuthor;
    private final String mDatePublished;
    private final String mURL;

    /**
     * Constructor with all data
     * @param title
     * @param nameOfSection
     * @param author
     * @param datePublished
     * @param Url
     */
    News(String title, String nameOfSection, String author, String datePublished, String Url) {
        mTitle = title;
        mNameOfSection = nameOfSection;
        mAuthor = author;
        mDatePublished = datePublished;
        mURL = Url;
    }

    String getTitle() { return mTitle; }
    String getNameOfSection() { return mNameOfSection; }
    String getAuthor() { return mAuthor; }
    String getDatePublished() { return mDatePublished; }
    String getUrl() {return mURL;}
}
