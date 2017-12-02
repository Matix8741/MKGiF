package com.metody.mkgif.data.tools;

public class DataItem {
    public String getContent() {
        return content;
    }

    public DataType getDataTyp() {
        return dataTyp;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

    private Float rating = (float)0;

    private String date = "";

    public void setDataTyp(DataType dataTyp) {
        this.dataTyp = dataTyp;
    }

    private DataType dataTyp;

    public DataItem(String content, DataType dataTyp){
        this.content = content;
        this.dataTyp = dataTyp;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public boolean equals(Object o){
        if(!( o instanceof DataItem)){
            return false;
        }
        DataItem other = (DataItem) o;
        return dataTyp.equals(other.getDataTyp()) && content.equals(other.content);
    }
    @Override
    public int hashCode(){
        return dataTyp.hashCode()+content.hashCode();
    }
}
