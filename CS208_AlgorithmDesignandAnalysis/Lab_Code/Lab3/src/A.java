import java.io.*;
import java.util.*;

class node {
    int n;
    int in_d;
    LinkedList<node> sub = new LinkedList<>();

    node(int num) {
        n = num;
    }
}

public class A {
    public static void main(String[] args) {
        QReader reader = new QReader();
        QWriter writer = new QWriter();
        long mod = (long) (1e9 + 7);
        int n, m;
        n = reader.nextInt();
        m = reader.nextInt();
        node g[] = new node[n];
        node g_r[] = new node[n];
        long ans[] = new long[n];
        long ans_r[] = new long[n];
        for (int i = 0; i < n; i++) {
            g[i] = new node(i);
            g_r[i] = new node(i);
        }
        for (int i = 0; i < m; i++) {
            int x = reader.nextInt() - 1;
            int y = reader.nextInt() - 1;
            g[x].sub.add(g[y]);
            g[y].in_d++;
            g_r[y].sub.add(g_r[x]);
            g_r[x].in_d++;
        }
        Queue<node> queue1 = new LinkedList<>();
        Queue<node> queue2 = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (g[i].in_d == 0) {
                queue1.add(g[i]);
                ans[i]++;
            }
            if (g_r[i].in_d == 0) {
                queue2.add(g_r[i]);
                ans_r[i]++;
            }
        }
        while (!queue1.isEmpty()) {
            node p = queue1.poll();
            for (node q : p.sub) {
                ans[q.n] = (ans[q.n] + ans[p.n]) % mod;
                if (--q.in_d == 0) {
                    queue1.add(q);
                }
            }
        }
        while (!queue2.isEmpty()) {
            node p = queue2.poll();
            for (node q : p.sub) {
                ans_r[q.n] = (ans_r[q.n] + ans_r[p.n]) % mod;
                if (--q.in_d == 0) {
                    queue2.add(q);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            writer.print(((ans[i] * ans_r[i]) % mod) + " ");
        }
        writer.close();
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