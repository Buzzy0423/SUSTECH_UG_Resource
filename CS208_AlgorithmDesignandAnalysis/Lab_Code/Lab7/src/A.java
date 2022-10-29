import java.io.*;
import java.util.StringTokenizer;

public class A {
    public static void main(String[] args) {
        QReader reader = new QReader();
        QWriter writer = new QWriter();
        long n = reader.nextLong();
        long d = 1;
        StringBuilder stringBuilder = new StringBuilder();
        while (n >= d) {
            d = d << 1;
        }
        while (d > 1) {
            if (n > d / 2 - 1) {
                stringBuilder.append("1");
                n = d - 1 - n;
            }else {
                stringBuilder.append("0");
            }
            d = d >> 1;
        }
        String ans = stringBuilder.toString();
        boolean flag = false;
        for (int i = 0;i < ans.length();i++){
            if (ans.charAt(i) == '0' && !flag){
                continue;
            }else {
                flag = true;
                writer.print(ans.charAt(i));
            }
        }
        writer.close();
    }
}

