//import java.io.*;
//import java.util.*;
//
//public class A {
//    static long num = (long) (1e9 + 7);
//
//    public static void main(String[] args) {
//        QReader reader = new QReader();
//        QWriter writer = new QWriter();
//        int n = reader.nextInt();
//        int m = reader.nextInt();
//        int nest = (Math.min(n, m) + 1) / 2;
//        int length = Math.max(n, m);
//        long[][] arr = new long[nest + 1][length + 1];
//        long[] tmp = new long[length + 1];
//        long[] tmp2 = new long[length + 1];
//        Arrays.fill(arr[1], 1);
//        long ans = 1;
//        for (int i = 2; i < nest + 1; i++) {
//            for (int j = 1; j < length + 1; j++) {
//                if (j == 3) {
//                    tmp[j] = arr[i - 1][j - 2];
//                    tmp2[j] = arr[i - 1][j - 2] * (j - 2);
//                } else if (j > 3) {
//                    tmp[j] = mod(tmp[j - 1] + arr[i - 1][j - 2]);
//                    tmp2[j] = mod(tmp2[j - 1] + arr[i - 1][j - 2] * (j - 2));
//                }
//                arr[i][j] = mod((j - 1) * tmp[j] - tmp2[j]);
//            }
//            ans += mod(arr[i][n] * arr[i][m]);
//            ans %= num;
//        }
//        writer.print(ans > 0 ? ans : ans + num);
//        writer.close();
//    }
//
//    static long mod(long n){
//        return n % num;
//    }
//}
//
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