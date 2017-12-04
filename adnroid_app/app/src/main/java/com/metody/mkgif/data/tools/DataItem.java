package com.metody.mkgif.data.tools;

public class DataItem extends Data {

    private Float rating = (float) 0;

    private String date = "";

    private String creator = "";

    private String createDate = "";

    private String brand = "";

    public DataItem(String content) {
        super(content, DataType.item);
    }

    Float getRating() {
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
