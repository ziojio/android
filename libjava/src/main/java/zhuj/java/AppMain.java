package zhuj.java;

import zhuj.java.log.ZLog;
import zhuj.java.zzz_test.UserData;
import zhuj.java.zzz_test.UserData2;

public class AppMain {
    public static ZLog logger = new ZLog();

    public static void main(String[] args) {

        String s = " XXXXXXXXXXXXX ";
        logger.info(s);


        new UserData().print();
        new UserData2().print();
    }

}