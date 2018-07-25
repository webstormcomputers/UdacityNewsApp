package com.webstormcomputers.udacitynewsapp;

public class News {
    private final String nTitle;
    private final String nNameOfSection;
    private String nAuthor;
    private final String nDatePublished;
    private final String nURL;

    News (String title, String nameOfSection, String datePublished, String Url) {
        nTitle = title;
        nNameOfSection = nameOfSection;
        nDatePublished = datePublished;
        nURL = Url;
    }



    /**
     * Constructor with all data
     * @param title
     * @param nameOfSection
     * @param author
     * @param datePublished
     * @param Url
     */
    News(String title, String nameOfSection, String author, String datePublished, String Url) {
        nTitle = title;
        nNameOfSection = nameOfSection;
        nAuthor = author;
        nDatePublished = datePublished;
        nURL = Url;
    }







    String getTitle() { return nTitle; }
    String getNameOfSection() { return nNameOfSection; }
    String getAuthor() { return nAuthor; }
    String getDatePublished() { return nDatePublished; }
    String getUrl() {return nURL;}
}
