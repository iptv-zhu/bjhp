package product.prison.bean;

import java.io.Serializable;

/**
 * Created by zhu on 2017/10/12.
 */

public class Bills implements Serializable {
    private String content;

    private String date;

    private String price;

    private String total;

    private String count;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return this.price;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotal() {
        return this.total;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCount() {
        return this.count;
    }
}
