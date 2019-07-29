package product.prison.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhu on 2017/10/12.
 */

public class Order implements Serializable {
    private List<Bills> bills ;

    private String total;

    public void setBills(List<Bills> bills){
        this.bills = bills;
    }
    public List<Bills> getBills(){
        return this.bills;
    }
    public void setTotal(String total){
        this.total = total;
    }
    public String getTotal(){
        return this.total;
    }
}
