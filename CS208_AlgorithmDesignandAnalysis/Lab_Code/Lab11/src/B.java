import java.io.*;
import java.util.StringTokenizer;

public class B {
    public static void main(String[] args) {
        QReader reader = new QReader();
        QWriter writer = new QWriter();
        int n = reader.nextInt();
        int m = reader.nextInt();
        boolean[][] arr = new boolean[n][n];
        int pow = (1 << n);
        long[] dp = new long[pow];
        for (int i = 0; i < m; i++) {
            int u = reader.nextInt() - 1;
            int v = reader.nextInt() - 1;
            arr[v][u] = true;
        }
        for (int i = 0; i < n; i++) {
            arr[i][i] = true;
        }
        dp[0] = 1;
        for (int i = 0; i < pow; i++) {
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) == 0) {
                    int counter = 0;
                    for (int k = 0; k < n; k++) {
                        if (((i >> k) & 1) == 1 && arr[k][j]) {
                            counter++;
                        }
                    }
                    dp[i + (1 << j)] += (1L << counter) * dp[i];
                    dp[i + (1 << j)] %= 1e9 + 7;
                }
            }
        }
        writer.println(dp[pow - 1]);
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