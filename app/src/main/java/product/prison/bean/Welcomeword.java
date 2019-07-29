package product.prison.bean;

import java.io.Serializable;

/**
 * Created by zhu on 2017/9/22.
 */

public class Welcomeword implements Serializable {
    private int id;

    private String content;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

}
