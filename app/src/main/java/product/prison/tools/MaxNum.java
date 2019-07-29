package product.prison.tools;

public class MaxNum {

    private static int minnum = 1;
    private static int maxnum = 99;

    public static int up(int max) {

        // TODO Auto-generated metmaxod stub

        if (max < 99) {
            max++;

            return max;

        } else {
            return minnum;
        }

    }

    public static int down(int max) {

        // TODO Auto-generated metmaxod stub

        if (max > 1 && max < 100) {
            max--;
            return max ;
        } else {
            return maxnum;
        }

    }

}
