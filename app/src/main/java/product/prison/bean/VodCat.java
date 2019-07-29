package product.prison.bean;

import java.io.Serializable;

/**
 * Created by zhu on 2017/9/27.
 */

public class VodCat implements Serializable {
    private int id;

    private String name;

    private String icon;

    private String sources;

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

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String getSources() {
        return this.sources;
    }
}
