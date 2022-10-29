import java.io.*;
import java.util.*;

public class A {
    static long mod = (long) (1e9 + 7);

    public static void main(String[] args) {
        QReader reader = new QReader();
        QWriter writer = new QWriter();
        int n = reader.nextInt();
        int l = reader.nextInt();
        int r = reader.nextInt();
        int[] w = new int[r];
        int max = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int tmp = reader.nextInt();
            w[tmp]++;
            max = Math.max(tmp, max);
            sum += tmp;
        }
        int L = Math.max(sum - r, l);
        int R = Math.min(sum - l, r);
        int[][] dp = new int[max + 1][R + 1];
        dp[0][0] = 1;
        for (int i = 1; i < max + 1; i++) {
            for (int j = 0; j < R + 1; j++) {
                if (i > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                    for (int k = 1; k <= w[i] && j >= k * i; k++) {
                        dp[i][j] = Mod(dp[i][j] + dp[i - 1][j - k * i]);
                    }
                }
            }
        }
        int ans = 0;
        for (int i = L;i <= R;i++){
            ans = Mod(ans + dp[max][i]);
        }
        writer.print(ans);
        writer.close();
    }

    static int Mod(long num) {
        return (int) (num % mod);
    }
}

//class QReader {
//    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//    private StringTokenizer tokenizer = new StringTokenizer("");
//
//    private String innerNextLine() {
//        try {
//            return reader.readLine();
//        } catch (IOException e) {
//            return null;
//        }
//    }
//
//    public boolean hasNext() {
//        while (!tokenizer.hasMoreTokens()) {
//            String nextLine = innerNextLine();
//            if (nextLine == null) {
//                return false;
//            }
//            tokenizer = new StringTokenizer(nextLine);
//        }
//        return true;
//    }
//
//    public String nextLine() {
//        tokenizer = new StringTokenizer("");
//        return innerNextLine();
//    }
//
//    public String next() {
//        hasNext();
//        return tokenizer.nextToken();
//    }
//
//    public int nextInt() {
//        return Integer.parseInt(next());
//    }
//
//    public long nextLong() {
//        return Long.parseLong(next());
//    }
//}
//
//class QWriter implements Closeable {
//    private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
//
//    public void print(Object object) {
//        try {
//            writer.write(object.toString());
//        } catch (IOException e) {
//            return;
//        }
//    }
//
//    public void println(Object object) {
//        try {
//            writer.write(object.toString());
//            writer.write("\n");
//        } catch (IOException e) {
//            return;
//        }
//    }
//
//    @Override
//    public void close() {
//        try {
//            writer.close();
//        } catch (IOException e) {
//            return;
//        }
//    }
//}