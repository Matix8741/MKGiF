package com.metody.mkgif.data.tools;

public class DataItem {
    public String getContetn() {
        return contetn;
    }

    public DataType getDataTyp() {
        return dataTyp;
    }

    public void setContetn(String contetn) {
        this.contetn = contetn;
    }

    private String contetn;

    public void setDataTyp(DataType dataTyp) {
        this.dataTyp = dataTyp;
    }

    private DataType dataTyp;

    public DataItem(String content, DataType dataTyp){
        this.contetn = content;
        this.dataTyp = dataTyp;
    }

}
