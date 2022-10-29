import java.awt.geom.Area;
import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class A {
    private static class UnionFindSet {
        int[] pre;

        UnionFindSet(int num) {
            pre = new int[num];
            for (int i = 0; i < num; i++) {
                pre[i] = i;
            }
        }

        int find(int i) {
            if (pre[i] == i) return i;
            return pre[i] = find(pre[i]);
        }

        void union(int i, int j) {
            int x = find(i);
            int y = find(j);
            if (x != y) {
                pre[x] = y;
            }
        }
    }

    private static class node {
        int num;
        int layer;
        node father;
        LinkedList<node> children;
        int total_num;


        node(int n) {
            this.num = n;
        }

        void update() {
            this.total_num = 1;
            if (!children.isEmpty()) {
                this.layer = children.get(0).layer + 1;
            } else {
                this.layer = 0;
            }
            for (node n : children) {
                this.total_num += n.total_num;
            }
        }
    }

    static int[] co_ancestor(node p, node q) {
        boolean tmp = false;
        int counter = 0;
        if (p.layer == q.layer) {
            tmp = true;
        }
        while (p.layer != q.layer) {
            if (p.layer > q.layer) {
                q = q.father;
            } else {
                p = p.father;
            }
            counter++;
        }
        while (p != q) {
            p = p.father;
            q = q.father;
            counter++;
        }
        if (tmp) {
            return new int[]{p.num, 1};
        } else {
            return new int[]{counter, -1};
        }
    }

    public static void main(String[] args) {
        QReader reader = new QReader();
        QWriter writer = new QWriter();
        int n = reader.nextInt();
        node[] con = new node[n];
        node root;// FIXME
        for (int i = 0; i < n; i++) {
            con[i] = new node(i);
        }
        for (int i = 0; i < n; i++) {
            if (i == 0) {

            }
        }
        int m = reader.nextInt();
        for (int i = 0; i < m; i++) {
            node p = con[reader.nextInt() - 1];
            node q = con[reader.nextInt() - 1];
            if (p == q) {
                writer.println(root.total_num);
            } else {
                int[] arr = co_ancestor(p, q);
                if (arr[1] == -1){
                    if (arr[0] % 2 == 0){
                        writer.println(1);
                    }
                }else {
                    writer.println(root.total_num - con[arr[0]].total_num + 1);
                }
            }
        }
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