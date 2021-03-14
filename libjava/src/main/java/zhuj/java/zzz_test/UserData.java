package zhuj.java.zzz_test;

public class UserData {
    public static final String TAG = "hello";
    String name;
    String age;
    int sex;

    public UserData() {
    }

    public UserData(String name, String age, int sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

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
