package com.metody.mkgif.data.tools;

public class DataItem extends Data {

    private Float rating = (float) 0;

    private DataStatus status;

    Float getAvgRating() {
        if(avgRating == -1)
            return rating;
        else
            return avgRating;
    }

    public void setAvgRating(Float avgRating) {
        this.avgRating = avgRating;
    }

    private Float avgRating = (float) -1;

    private String date = "";

    private String creator = "example";

    private String createDate = "example";

    private String brand = "example";

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

    String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    String getCreateDate() {
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

    DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }
}
