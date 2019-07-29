package product.prison.bean;

import java.io.Serializable;

/**
 * Created by zhu on 2017/9/27.
 */

public class Details implements Serializable {
    private int id;

    private int infoId;

    private String name;

    private String icon;

    private String content;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public int getInfoId() {
        return this.infoId;
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

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }
}
