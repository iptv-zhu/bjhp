package product.prison.ui.weather;

import java.io.Serializable;

/**
 * Created by zhu on 2017/9/18.
 */

public class Weather implements Serializable {
    private int id;

    private String info;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setInfo(String info){
        this.info = info;
    }
    public String getInfo(){
        return this.info;
    }

}
