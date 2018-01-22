package com.spa.model.servicemodel;

public class ChartDataSet {
    private String category_name;
    private float current_price;
    private String dealprice;
    private String yousave;

    public float getCurrent_price() {
        return this.current_price;
    }

    public void setCurrent_price(float current_price) {
        this.current_price = current_price;
    }

    public String getCategory_name() {
        return this.category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getDealprice() {
        return this.dealprice;
    }

    public void setDealprice(String dealprice) {
        this.dealprice = dealprice;
    }

    public String getYousave() {
        return this.yousave;
    }

    public void setYousave(String yousave) {
        this.yousave = yousave;
    }
}
