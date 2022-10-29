import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class A {
    public static void main(String[] args) {
        QReader reader = new QReader();
        QWriter writer = new QWriter();
        int n = reader.nextInt();
        boolean[] inTree = new boolean[n];
        node[] tree = new node[n];
        int[][] edge = new int[n - 1][4];
        long opt[][] = new long[n][2];
        for (int i = 0; i < n; i++) {
            tree[i] = new node(i);
        }
        for (int i = 0; i < n - 1; i++) {
            int x = reader.nextInt() - 1;
            int y = reader.nextInt() - 1;
            int w = reader.nextInt();
            tree[x].children.add(tree[y]);
            tree[x].weight.add(w);
            tree[y].children.add(tree[x]);
            tree[y].weight.add(w);
        }
        dfs(opt, tree[0], -1);
        writer.println(opt[0][1]);
        writer.close();
    }

    private static void dfs(long[][] opt, node n, int father) {
        Boolean leaf = true;
        for (node tmp : n.children) {
            if (tmp.index != father) {
                dfs(opt, tmp, n.index);
                leaf = false;
            }
        }
        if (leaf){
            return;
        }
        for (node tmp : n.children) {
            if (tmp.index != father){
                opt[n.index][0] += opt[tmp.index][1];
            }
        }
        opt[n.index][1] = opt[n.index][0];
        for (int i = 0; i < n.children.size(); i++) {
            if (n.children.get(i).index != father){
                opt[n.index][1] = Math.max(opt[n.index][1], (n.weight.get(i) + opt[n.index][0] - opt[n.children.get(i).index][1] + opt[n.children.get(i).index][0]));
            }
        }
    }


    private static class node {
        int index;
        ArrayList<node> children = new ArrayList<>();
        ArrayList<Integer> weight = new ArrayList<>();

        node(int i) {
            index = i;
        }
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