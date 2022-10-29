import java.io.*;
import java.util.*;

public class B {
    static int log = 17;
    static int max = 100001;

    public static void main(String[] args) {
        QReader reader = new QReader();
        QWriter writer = new QWriter();
        int n = reader.nextInt();
        int m = reader.nextInt();
        int[][] st = new int[max][log + 1];
        Map<Integer, Integer> index = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            int tmp = reader.nextInt();
            st[i][0] = tmp;
            index.put(tmp, i - 1);
        }
        for (int j = 1; j <= log; j++) {
            for (int i = 1; i + (1 << j) - 1 <= n; i++) {
                st[i][j] = Math.min(st[i][j - 1], st[i + (1 << (j - 1))][j - 1]);
            }
        }
        int[] bit = new int[n];
        for (int i = 0; i < n; i++) {
            bit[i] = reader.nextInt();
        }
        int[] sum = new int[n];
        sum[0] = bit[0];
        for (int i = 1; i < n; i++) {
            sum[i] = bit[i] + sum[i - 1];
        }
        int[] logn = new int[max + 1];
        logn[1] = 0;
        logn[2] = 1;
        for (int i = 3; i < max; i++) {
            logn[i] = logn[i / 2] + 1;
        }
        int sec = 0;
        long ans = 0;
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> o2[0] - o1[0]);
        priorityQueue.add(new int[]{sum[n - 1], 0, n - 1, 0});
        while (sec < m && !priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();
            int min = query(st, logn, arr[1], arr[2]);
            int id = index.get(min);
            if (sec + (min - arr[3]) > m) {
                ans += (long) (m - sec) * arr[0];
                break;
            } else {
                ans += (long) (min - arr[3]) * arr[0];
                sec += (min - arr[3]);
            }
            if (id - 1 >= arr[1]) {
                if (arr[1] == 0) {
                    priorityQueue.add(new int[]{sum[id - 1], arr[1], id - 1, min});
                } else {
                    priorityQueue.add(new int[]{(sum[id - 1] - sum[arr[1] - 1]), arr[1], id - 1, min});
                }
            }
            if (arr[2] >= id + 1) {
                priorityQueue.add(new int[]{(sum[arr[2]] - sum[id]), id + 1, arr[2], min});
            }
        }
        writer.print(ans);
        writer.close();
    }

    static int query(int[][] st, int[] logn, int i, int j) {
        int k = logn[j - i + 1];
        return Math.min(st[i + 1][k], st[(j + 1) - (1 << k) + 1][k]);
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