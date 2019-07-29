package product.prison.ui;

import android.app.Fragment;


/**
 * Created by zhu on 2017/8/24.
 */

public class BaseFram extends Fragment {


    public <T, View> T $(int id) {
        return (T) super.getActivity().findViewById(id);
    }
}
