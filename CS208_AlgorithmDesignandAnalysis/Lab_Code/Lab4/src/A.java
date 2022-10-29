import java.io.*;
import java.util.*;

class node {
    int n;
    int in_d;
    int out_d;
    long ce = 0;
    double ce_log = 0;
    long h;
    long a;
    long b;
    long c;
    LinkedList<node> sub = new LinkedList<>();

    node(int num, long H, long A, long B, long C) {
        n = num;
        h = H;
        a = A;
        b = B;
        c = C;
    }
}

public class A {
    public static void main(String[] args) {
        QReader reader = new QReader();
        QWriter writer = new QWriter();
        int n = reader.nextInt();
        int m = reader.nextInt();
        int c = reader.nextInt();
        node[] dag = new node[n];
        Queue<node> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            dag[i] = new node(i, reader.nextLong(), reader.nextLong(), reader.nextLong(), reader.nextLong());
        }
        for (int i = 0; i < m; i++) {
            int a = reader.nextInt() - 1;
            int b = reader.nextInt() - 1;
            dag[a].sub.add(dag[b]);
            dag[a].out_d++;
            dag[b].in_d++;
        }
        dag[0].ce = c;
        dag[0].ce_log = Math.log(c);
        queue.add(dag[0]);
        long ans = -1;
        double ans_log = -1;
        while (!queue.isEmpty()) {
            node p = queue.poll();
            if (p.ce_log > Math.log(1e9) || p.ce >= p.h){
                if (p.ce_log > Math.log(1e9)) {
                    p.ce = mod(p.ce * p.b);
                    p.ce_log += Math.log(p.b);
                } else {
                    p.ce = Math.max(Math.max(p.ce + p.a, p.ce * p.b), p.c);
                    p.ce_log = Math.log(p.ce);
                    p.ce = mod(p.ce);
                }
                if (p.out_d == 0 && p.ce_log > ans_log) {
                    ans = p.ce;
                    ans_log = p.ce_log;
                }
                for (node q : p.sub) {
                    q.in_d--;
                    if (p.ce_log > q.ce_log) {
                        q.ce = p.ce;
                        q.ce_log = p.ce_log;
                    }
                    if (q.in_d == 0) {
                        queue.add(q);
                    }
                }
            }
        }
        writer.print(mod(ans));
        writer.close();
    }

    static long mod(long n) {
        return (n % (long) (1e9 + 7));
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