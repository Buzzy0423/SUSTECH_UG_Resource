import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Timer;

public class B {
    private static class node {
        long l;
        long r;
        int cnt;
        node l_child;
        node r_child;

        node(long ll, long rr, int c) {
            l = ll;
            r = rr;
            cnt = c;
            l_child = null;
            r_child = null;
        }

        node(node copy) {
            if (copy != null) {
                l = copy.l;
                r = copy.r;
                cnt = copy.cnt + 1;
                l_child = copy.l_child;
                r_child = copy.r_child;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        QReader reader = new QReader();
        QWriter writer = new QWriter();
        int n = reader.nextInt();
        int q = reader.nextInt();
        long[] arr = new long[n + 1];
        long max = Long.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            arr[i + 1] = reader.nextLong();
            max = Math.max(arr[i + 1], max);
        }
        node[] con = new node[n + 1];
        con[0] = new node(1, max, 0);
        for (int i = 1; i < n + 1; i++) {
            con[i] = build(con[i], con[i - 1], 1, max, arr[i]);
        }
        for (int i = 0; i < q; i++) {
            int a = reader.nextInt();
            int b = reader.nextInt();
            if (a > b) {
                int tt = a;
                a = b;
                b = tt;
            }
            long rank = (b - a + 2) / 2;
            writer.println(query(con[a-1], con[b], 1, max, rank));
        }
        writer.close();
    }

    private static node build(node t, node pre, long min, long max, long val) {
        if (pre != null) {
            t = new node(pre);
        } else {
            t = new node(min, max, 1);
        }
        if (min == max) {
            if (pre == null) {
                return new node(min, max, 1);
            } else {
                return t;
            }
        }
        long mid = (min + max) / 2;
        if (val > mid) {
            if (pre == null) {
                t.r_child = build(t.r_child, null, mid + 1, max, val);
            } else {
                t.r_child = build(t.r_child, pre.r_child, mid + 1, max, val);
            }
        } else {
            if (pre == null) {
                t.l_child = build(t.l_child, null, min, mid, val);
            } else {
                t.l_child = build(t.l_child, pre.l_child, min, mid, val);
            }
        }
        return t;
    }

    private static long query(node l, node r, long min, long max, long rank) {
        if (min == max) {
            return min;
        }
        long mid = (min + max) / 2;
        if (l == null || l.l_child == null) {
            long l_diff;
            if (r.l_child == null) {
                l_diff = 0;
            } else {
                l_diff = r.l_child.cnt;
            }
            if (rank > l_diff) {
                if (l != null) {
                    return query(l.r_child, r.r_child, mid + 1, max, rank - l_diff);
                } else {
                    return query(null, r.r_child, mid + 1, max, rank - l_diff);
                }
            } else {
                return query(null, r.l_child, min, mid, rank);
            }
        } else {
            long l_diff = r.l_child.cnt - l.l_child.cnt;
            if (rank > l_diff) {
                return query(l.r_child, r.r_child, mid + 1, max, rank - l_diff);
            } else {
                return query(l.l_child, r.l_child, min, mid, rank);
            }
        }
    }
}

class QReader {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private StringTokenizer tokenizer = new StringTokenizer("");

    QReader() throws FileNotFoundException {
    }

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