package zhuj.java.regex;


import java.util.regex.Pattern;

public class RegexTest {

    Pattern pattern = Pattern.compile("");

    public static void main(String[] args) {

        String reg = "<.*>";
        String text = "Eddy <font color=\"#ffff00\">rolled</font> onto the school lot.";

        String result = text.replaceAll(reg, "");

    }

}
