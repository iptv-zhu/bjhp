package product.prison.bean;

import java.io.Serializable;

/**
 * Created by zhu on 2017/9/18.
 */

public class LiveApps implements Serializable {
    private int id;

    private String name;

    private String path;

    private String package_name;

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

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPackage_name() {
        return this.package_name;
    }

}
