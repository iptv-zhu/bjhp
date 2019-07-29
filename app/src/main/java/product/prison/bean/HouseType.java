package product.prison.bean;

import java.io.Serializable;

/**
 * Created by zhu on 2017/9/18.
 */

public class HouseType implements Serializable {
    private int id;

    private String name;

    private double price;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }
}
