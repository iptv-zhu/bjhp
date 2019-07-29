package product.prison.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhu on 2017/9/27.
 */

public class Live implements Serializable {
    private List<Singles> singles ;

    private List<Lives> lives ;

    private List<Apps> apps ;

    public void setSingles(List<Singles> singles){
        this.singles = singles;
    }
    public List<Singles> getSingles(){
        return this.singles;
    }
    public void setLives(List<Lives> lives){
        this.lives = lives;
    }
    public List<Lives> getLives(){
        return this.lives;
    }
    public void setApps(List<Apps> apps){
        this.apps = apps;
    }
    public List<Apps> getApps(){
        return this.apps;
    }

}
