package zhuj.zzz_test;

public class UserData {

    String name;
    String age;
    int sex;

    // public UserData() {
    // }

    public UserData(String name, String age, int sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
    //
    // public String getName() {
    //     return name;
    // }
    //
    // public void setName(String name) {
    //     this.name = name;
    // }
    //
    // public String getAge() {
    //     return age;
    // }
    //
    // public void setAge(String age) {
    //     this.age = age;
    // }
    //
    // public int getSex() {
    //     return sex;
    // }
    //
    // public void setSex(int sex) {
    //     this.sex = sex;
    // }

    @Override
    public String toString() {
        return "UserData{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex=" + sex +
                '}';
    }
}
