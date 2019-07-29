package product.prison.tools;

import android.content.Context;
import android.media.AudioManager;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;

    public Utils() {
        // 转换成字符串的时间
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    }


//    public static void savaDAO(HistoryBean bean) {
//        HistoryBeanDao dao = MyApp.getDaoSession().getHistoryBeanDao();
//        HistoryBean unique = dao.queryBuilder().where(HistoryBeanDao.Properties.Title.eq(bean.getTitle())).unique();
//        if (unique == null) {
//            dao.insert(bean);
//        } else {
//            dao.delete(unique);
//            dao.insert(bean);
//        }
//
//        Log.e("TAG", "插入数据库成功--" + bean);
//    }


    public static String parseToTimeTmp(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return (format.parse(time).getTime() + "").substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }


    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 身份证号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isID(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^\\d{17}([0-9]|X|x)$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static void disPlayImage(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url).into(imageView);
    }


    /**
     * 获取设备当前MAC地址
     */
    public static String getMACAddress() {
        String mac = "error";
        try {
            Process pro = Runtime.getRuntime().exec(
                    "cat /sys/class/net/eth0/address");
            InputStreamReader inReader = new InputStreamReader(
                    pro.getInputStream());
            BufferedReader bReader = new BufferedReader(inReader);
            String line = null;
            while ((line = bReader.readLine()) != null) {
//                mac = line.trim();
                mac = line.trim().replace(":", "");
            }
            System.out.println("---mac---\n" + mac);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mac;
//        return  "ee:65:00:00:00:40";
    }


    /**
     * 获取设备当前版本名
     */
    public static String getVersion(Context context) {
        String version = "1.0";
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取设备当前屏幕宽高
     */

    public static String getWH(Context context) {
        int screenWidth = 0;
        int screenHeight = 0;
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        return screenWidth + "*" + screenHeight;
    }

    /**
     * 获取设备当前音量
     */
    public static int getCurrentVolume(Context context) {
        int mCurrentVolume = 0;
        int mMaxVolume = 0;
        try {
            //得到音量
            AudioManager mAm = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            //最大音量
            mMaxVolume = mAm.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            //当前音量
            mCurrentVolume = mAm.getStreamVolume(AudioManager.STREAM_MUSIC);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mCurrentVolume;
    }


    public String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;

        int minutes = (totalSeconds / 60) % 60;

        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
                    .toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    /**
     * 获取系统时间
     */
    public static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 格式化时间
     */
    public static String format(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * 格式化时间
     */
    public static String creatTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * 格式化时间
     */
    public static String creatDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * 格式化时间
     */
    public static String formatTZTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * 获取系统年月日
     */
    public static String getYear() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(new Date());
    }

    public static String formatTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    public static Date formatTime2(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static Date offtime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            return format.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        return hostIp;
    }

    /**
     * 手机型号
     *
     * @return
     */
    public static String getModel() {
        return Build.MODEL;
    }

    public static String getWeek() {
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        return format.format(new Date());
    }

    public static String getTZTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date());
    }

    public static long serializeTZTime(String time) {
        //2017-05-18 23:59:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (Exception e) {
            Log.e("TAG", "time---" + e.getMessage());
        }
        return date.getTime();
    }

    public static long serializeTime(String time) {
        //2017-05-18 23:59:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (Exception e) {
            Log.e("TAG", "time---" + e.getMessage());
        }
        return date.getTime();
    }

    /**
     * 得到网速
     * 注意：每隔一段时间去调用，每秒种
     *
     * @param context
     * @return
     */
    public String getNetSpeed(Context context) {

        long nowTotalRxBytes = TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB

        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;

        String speedStr = "正在加载 " + String.valueOf(speed) + " kb/s";


        return speedStr;
    }


    /**
     * 获取系统内存大小
     *
     * @return
     */
    public static String getSysteTotalMemorySize(Context context) {
        //获得ActivityManager服务的对象
        android.app.ActivityManager mActivityManager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获得MemoryInfo对象
        android.app.ActivityManager.MemoryInfo memoryInfo = new android.app.ActivityManager.MemoryInfo();
        //获得系统可用内存，保存在MemoryInfo对象上
        mActivityManager.getMemoryInfo(memoryInfo);
        long memSize = memoryInfo.totalMem;
        //字符类型转换
        String availMemStr = android.text.format.Formatter.formatFileSize(context, memSize);
        return availMemStr;
    }

    /**
     * 获取系统可用的内存大小
     *
     * @return
     */
    public static String getSystemAvaialbeMemorySize(Context context) {
        //获得ActivityManager服务的对象
        android.app.ActivityManager mActivityManager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获得MemoryInfo对象
        android.app.ActivityManager.MemoryInfo memoryInfo = new android.app.ActivityManager.MemoryInfo();
        //获得系统可用内存，保存在MemoryInfo对象上
        mActivityManager.getMemoryInfo(memoryInfo);
        long memSize = memoryInfo.availMem;

        //字符类型转换
        String availMemStr = android.text.format.Formatter.formatFileSize(context, memSize);
        return availMemStr;
    }

    /**
     * 获得SD卡总大小
     *
     * @return
     */
    public static String getSDTotalSize(Context context) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return android.text.format.Formatter.formatFileSize(context, blockSize * totalBlocks);
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     *
     * @return
     */
    public static String getSDAvailableSize(Context context) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return android.text.format.Formatter.formatFileSize(context, blockSize * availableBlocks);
    }

}
