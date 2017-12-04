package com.metody.mkgif.data.tools;


public abstract class Data {

    private String content;

    public void setDataTyp(DataType dataTyp) {
        this.dataTyp = dataTyp;
    }

    private DataType dataTyp;

    public void setContent(String content) {
        this.content = content;
    }

    Data(String content, DataType dataTyp) {
        this.content = content;
        this.dataTyp = dataTyp;
    }


    String getContent() {
        return content;
    }

    DataType getDataTyp() {
        return dataTyp;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Data)) {
            return false;
        }
        Data other = (Data) o;
        return dataTyp.equals(other.getDataTyp()) && content.equals(other.content);
    }

    @Override
    public int hashCode() {
        return dataTyp.hashCode() + content.hashCode();
    }
}
