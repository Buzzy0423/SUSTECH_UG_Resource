import java.io.*;
import java.util.*;

public class Lab6B {
    static class con{
        ArrayList<Integer> c = new ArrayList<>();
        ArrayList<Integer> r = new ArrayList<>();
    }

    static int n;
    static ArrayList<con> ans = new ArrayList<>();


    public static void main(String[] args) {
        QReader reader = new QReader();
        QWriter writer = new QWriter();

        n = reader.nextInt();
        int len = (int) Math.ceil((Math.log(n) / Math.log(2)));

        for (int i = 1;i <= len;i++ ){
            con c1 = new con();
            con c2 = new con();
            int k = 1 << i;
            k = k >> 1;
            int r = k + 1;
            int c = 1;
            while (r <= n && c <= n) {
                for (int j = 0; j < k; j++) {
                    if (r <= n) {
                        c1.r.add(r);
                        c2.c.add(r++);
                    }
                    if (c <= n) {
                        c1.c.add(c);
                        c2.r.add(c++);
                    }
                }
                r += k;
                c += k;
            }
            ans.add(c1);
            ans.add(c2);
        }

        writer.println(ans.size());

        for (con c: ans){
            writer.print(c.r.size());
            writer.print(" ");
            for (int i:c.r){
                writer.print(i);
                writer.print(" ");
            }
            writer.println("");
            writer.print(c.c.size());
            writer.print(" ");
            for (int i: c.c){
                writer.print(i);
                writer.print(" ");
            }
            writer.println("");
        }
        writer.close();
    }
}
class QReader {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private StringTokenizer tokenizer = new StringTokenizer("");

    private String innerNextLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean hasNext() {
        while (!tokenizer.hasMoreTokens()) {
            String nextLine = innerNextLine();
            if (nextLine == null) {
                return false;
            }
            tokenizer = new StringTokenizer(nextLine);
        }
        return true;
    }

    public String nextLine() {
        tokenizer = new StringTokenizer("");
        return innerNextLine();
    }

    public String next() {
        hasNext();
        return tokenizer.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public long nextLong() {
        return Long.parseLong(next());
    }
}

class QWriter implements Closeable {
    private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public void print(Object object) {
        try {
            writer.write(object.toString());
        } catch (IOException e) {
            return;
        }
    }

    public void println(Object object) {
        try {
            writer.write(object.toString());
            writer.write("\n");
        } catch (IOException e) {
            return;
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            return;
        }
    }
}