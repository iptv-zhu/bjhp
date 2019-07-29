package product.prison.bean;

import java.io.Serializable;

/**
 * Created by zhu on 2017/10/13.
 */

public class Brow implements Serializable {
    private int id;

    private String name;

    private String path;

    private int nums;

    private int loan_num;

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    private String describes;

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

    public void setNums(int nums) {
        this.nums = nums;
    }

    public int getNums() {
        return this.nums;
    }

    public void setLoan_num(int loan_num) {
        this.loan_num = loan_num;
    }

    public int getLoan_num() {
        return this.loan_num;
    }

}
