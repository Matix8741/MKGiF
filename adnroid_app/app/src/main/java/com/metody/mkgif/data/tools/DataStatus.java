package com.metody.mkgif.data.tools;


public enum DataStatus {
    Game, Book, Movie;
    public static DataStatus getStatus(String staus){
        switch (staus){
            case "Ksiazka":
            case "Książka":
                return Book;
            case "Film":
                return Movie;
            case "Gra":
                return Game;
            default:
                return Game;
        }
    }
}
