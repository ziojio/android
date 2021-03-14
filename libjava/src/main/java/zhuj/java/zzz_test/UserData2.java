package zhuj.java.zzz_test;

public class UserData2 extends UserData {
    public static final String TAG = "AAAAAAAAAAAAAAAAA";
    String name;
    String age;
    int sex;

    public void print() {
        System.out.println(TAG);
    }

    @Override
    public String toString() {
        return "UserData{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex=" + sex +
                '}';
    }
}
