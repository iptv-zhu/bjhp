package product.prison.ui.msg;

import java.util.Comparator;
import java.util.Date;

import product.prison.bean.Email;

public class EmailTimeSort implements Comparator {

    public int compare(Object arg0, Object arg1) {
        Email user0 = (Email) arg0;
        Email user1 = (Email) arg1;
        int flag = new Date(user1.getCreatetime()).compareTo(new Date(user0.getCreatetime()));
        return flag;
    }
}
