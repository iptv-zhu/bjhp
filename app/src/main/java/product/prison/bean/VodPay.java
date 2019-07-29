package product.prison.bean;

import java.io.Serializable;

/**
 * Created by zhu on 2017/10/11.
 */

public class VodPay implements Serializable {
    private String dayPrice;

    private String oncePrice;

    private int status;

    public void setDayPrice(String dayPrice) {
        this.dayPrice = dayPrice;
    }

    public String getDayPrice() {
        return this.dayPrice;
    }

    public void setOncePrice(String oncePrice) {
        this.oncePrice = oncePrice;
    }

    public String getOncePrice() {
        return this.oncePrice;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }
}
