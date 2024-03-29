package product.prison.download;

import android.os.Environment;

/**
 * 
 * 对sd卡的操作
 * 
 * @author 
 * 
 */

public class SDHandler {

	public final static boolean isSDAvaliable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取sd卡的根目录
	 * 
	 * @return
	 */
	public final static String getSDRootDIR() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

}
