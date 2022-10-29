import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B {
    private static class node {
        boolean l_edge;
        int l;
        int r;
        int index;
        node next;

        node(int ll, int rr, int ii, boolean edge) {
            l = ll;
            r = rr;
            index = ii;
            l_edge = edge;
        }
    }

    public static void main(String[] args) {
        QReader reader = new QReader();
        QWriter writer = new QWriter();
        int n = reader.nextInt();
        long[] arr = new long[n + 1];
        long[] sum = new long[n + 1];
        arr[0] = 0;
        sum[0] = 0;
        for (int i = 1; i < n + 1; i++) {
            arr[i] = reader.nextLong();
            sum[i] = sum[i - 1] + arr[i];
        }
        int m = reader.nextInt();
        node[] list = new node[n + 1];
        long[] ans = new long[m];
        Arrays.fill(ans, Long.MIN_VALUE);
        for (int i = 0; i < m; i++) {
            int p = reader.nextInt();
            int q = reader.nextInt();
            if (list[p] == null) {
                list[p] = new node(p, q, i, true);
            } else {
                node tmp = list[p];
                while (tmp.next != null) {
                    tmp = tmp.next;
                }
                tmp.next = new node(p, q, i, true);
            }
            if (list[q] == null) {
                list[q] = new node(p, q, i, false);
            } else {
                node tmp = list[q];
                while (tmp.next != null) {
                    tmp = tmp.next;
                }
                tmp.next = new node(p, q, i, false);
            }
        }
        query(1, n, arr, sum, list, ans);
        for (long l : ans) {
            writer.println(l);
        }
        writer.close();
    }

    public static long query(int l, int r, long[] arr, long[] sum, node[] list, long[] ans) {
        long __max;
        int mid = (l + r) / 2;
        if (l == r) {
            node tmp = list[l];
            while (tmp != null) {
                ans[tmp.index] = Math.max(ans[tmp.index], arr[l]);
                tmp = tmp.next;
            }
            __max = arr[l];
        } else {
            long l_max = query(l, mid, arr, sum, list, ans);
            long r_max = query(mid + 1, r, arr, sum, list, ans);
            long cross_max = merge(l, r, mid, sum);
            __max = Math.max(l_max, r_max);
            __max = Math.max(__max, cross_max);
            for (int i = l; i <= r; i++) {
                node tmp = list[i];
//                while (tmp != null) {
//                    if (l < tmp.l && tmp.l <= mid && mid < tmp.r && tmp.r < r){
//                        ans[tmp.index] = Math.max(ans[tmp.index], merge(tmp.l, tmp.r))
//                    } else if () {
//
//                    }else if (){
//
//                    }else if (tmp.l <= l && tmp.r >= r){
//                        ans[tmp.index] = Math.max(ans[tmp.index], __max);
//                    }
//                    tmp = tmp.next;
//                }
            }
        }
        return __max;
    }

    public static long merge(int l, int r, int mid, long[] sum) {//FIXME: TLE !!!
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for (int i = mid + 1; i <= r; i++) {
            if (max < sum[i]) {
                max = sum[i];
            }
        }
        for (int i = mid - 1; i >= l - 1; i--) {
            if (min > sum[i]) {
                min = sum[i];
            }
        }
        return max - min;
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