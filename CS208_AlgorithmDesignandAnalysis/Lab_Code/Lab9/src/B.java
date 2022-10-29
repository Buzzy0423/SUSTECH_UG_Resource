import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.StringTokenizer;

class matrix {
    int col;
    int row;
    long[][] data;

    matrix(int c, int r) {
        col = c;
        row = r;
        data = new long[r][c];
    }
}

public class B {
    static long mod = (long) (1e9 + 7);

    public static long Mod(long n) {
        return n % mod;
    }

    public static void main(String[] args) {
        QReader reader = new QReader();
        QWriter writer = new QWriter();
        int n = reader.nextInt();
        int m = reader.nextInt();
        matrix matrix = new matrix(m, m);
        if (m != 1) {
            for (int i = 0; i < m - 1; i++) {
                matrix.data[0][i] = 1;
                matrix.data[i + 1][i] = 1;
            }
            matrix.data[0][m - 1] = 1;
            matrix ans = m_exp(matrix, n);
            long num = Mod(ans.data[0][0] + ans.data[0][1]);
            writer.print(num);
        } else {
            writer.print(1);
        }
        writer.close();
    }

    static matrix m_mul(matrix a, matrix b) {
        matrix ans = new matrix(a.col, a.row);
        for (int i = 0; i < a.row; i++) {
            for (int j = 0; j < a.col; j++) {
                long tmp = a.data[i][j];
                for (int k = 0; k < b.col; k++) {
                    long tmp2 = Mod(tmp * b.data[j][k]);
                    ans.data[i][k] += tmp2;
                    ans.data[i][k] = Mod(ans.data[i][k]);
                }
            }
        }
        return ans;
    }

    static matrix m_exp(matrix a, int exp) {
        matrix ans = new matrix(a.col, a.row);
        for (int i = 0; i < a.col; i++) {
            ans.data[i][i] = 1;
        }
        while (exp != 0) {
            if (exp % 2 == 1) {
                ans = m_mul(ans, a);
            }
            a = m_mul(a, a);
            exp /= 2;
        }
        return ans;
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