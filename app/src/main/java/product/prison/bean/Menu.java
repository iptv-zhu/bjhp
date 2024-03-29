package product.prison.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Created by zhu on 2017/9/25.
 */

public class Menu implements Serializable {

    private int id;

    private String name;

    private String path;

    private int position;

    private int status;

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

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }



}
