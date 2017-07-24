package com.baway.lizhiqiang;

/**
 * date: 2017/7/17
 * author: 李志强
 * function:
 */


public class ShoppBean {
    @Override
    public String toString() {
        return "test [id=" + id + ", color=" + color
                + ", type=" + type + ", integral=" + integral + "]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    private String id;
    private String color;
    private String type;
    private String integral;
    private int num;//商品数量
    private int sumIntegral;
    private boolean isChoosed;   //商品是否在购物车中被选中

    public ShoppBean(String id, String color, String type, String integral) {
        super();
        this.id = id;
        this.color = color;
        this.type = type;
        this.integral = integral;
    }

    public ShoppBean() {
        super();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSumIntegral() {
        return sumIntegral;
    }

    public void setSumIntegral(int sumIntegral) {
        this.sumIntegral = sumIntegral;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean isChoosed) {
        this.isChoosed = isChoosed;
    }
}