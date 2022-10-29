import java.io.*;
import java.util.StringTokenizer;

class AVL_tree {
    private class node {
        long weight;

        long height;
        node left;
        node right;
        node father;

        node(long w) {
            weight = w;

        }
    }

    public node root;

    AVL_tree() {
        this.root = null;
    }

    private long height(node p) {
        return p == null ? -1 : p.height;
    }

    private node left_rotate(node a) {
        node b = a.right;
        b.father = a.father;
        if (a.father != null) {
            if (a.father.left == a) {
                a.father.left = b;
            } else {
                a.father.right = b;
            }
        } else root = b;
        a.right = b.left;
        if (b.left != null) b.left.father = a;
        b.left = a;
        a.father = b;
        a.height = Math.max(height(a.left), height(a.right)) + 1;
        b.height = Math.max(height(b.left), height(b.right)) + 1;
        return b;
    }

    private node right_rotate(node a) {
        node b = a.left;
        b.father = a.father;
        if (a.father != null) {
            if (a.father.left == a) {
                a.father.left = b;
            } else {
                a.father.right = b;
            }
        } else root = b;
        a.left = b.right;
        if (b.right != null) b.right.father = a;
        b.right = a;
        a.father = b;
        a.height = Math.max(height(a.left), height(a.right)) + 1;
        b.height = Math.max(height(b.left), height(b.right)) + 1;
        return b;
    }

    public node insert(node p, long num) {
        if (root == null) {
            root = new node(num);
            return null;
        }
        if (p == null) return new node(num);
        else {
            if (num > p.weight) {
                p.right = insert(p.right, num);
                p.right.father = p;
                if (height(p.right) - height(p.left) > 1) {
                    if (height(p.right.right) < height(p.right.left)) {
                        p.right = right_rotate(p.right);
                    }
                    p = left_rotate(p);
                }
            } else if (num < p.weight) {
                p.left = insert(p.left, num);
                p.left.father = p;
                if (height(p.left) - height(p.right) > 1) {
                    if (height(p.left.left) < height(p.left.right)) {
                        p.left = left_rotate(p.left);
                    }
                    p = right_rotate(p);
                }
            }
            p.height = Math.max(height(p.left), height(p.right)) + 1;
            return p;
        }
    }


    public node delete(node p, long n) {
        if (p == null) return null;
        if (n < p.weight) {
            p.left = delete(p.left, n);
            if (height(p.right) - height(p.left) > 1) {
                if (height(p.right.right) < height(p.right.left)) {
                    p.right = right_rotate(p.right);
                }
                p = left_rotate(p);
            }
        } else if (n > p.weight) {
            p.right = delete(p.right, n);
            if (p.right != null) p.right.father = p;
            if (height(p.left) - height(p.right) > 1) {
                if (height(p.left.left) < height(p.left.right)) {
                    p.left = left_rotate(p.left);
                }
                p = right_rotate(p);
            }
        } else {
            if (p.left != null && p.right != null) {
                node t = findmax(p);
                long tt = p.weight;
                p.weight = t.weight;
                t.weight = tt;
                p.left = delete(p.left, n);
            } else {
                if (p == root) {
                    if (p.left == null) {
                        p = p.right;
                        root = p;
                        if (p != null) p.father = null;
                    } else if (p.right == null) {
                        p = p.left;
                        root = p;
                        if (p != null) p.father = null;
                    } else {
                        root = null;
                    }
                } else{
                    if (p.left == null && p.right == null){
                        return null;
                    } else if (p.left == null){
                        if (p.father.left == p){
                            p.father.left = p.right;
                        }else{
                            p.father.right = p.right;
                        }
                        p.right.father = p.father;
                        p = p.right;
                    }else {
                        if (p.father.left == p){
                            p.father.left = p.left;
                        }else{
                            p.father.right = p.left;
                        }
                        p.left.father = p.father;
                        p = p.left;
                    }
                }
            }
        }
        if (p != null) p.height = Math.max(height(p.left), height(p.right)) + 1;
        return p;
    }

    public node findmin(node p) {
        if (p == null) return null;
        p = p.right;
        while (p != null && p.left != null) p = p.left;
        return p;
    }

    public node findmax(node p) {
        if (p.left == null) return null;
        p = p.left;
        while (p.right != null) p = p.right;
        return p;
    }

    public long find_ans(long n) {
        node q = root;
        while (true) {
            if (q.left != null && Math.abs(n - q.weight) >= Math.abs(n - findmax(q).weight)) {
                q = q.left;
            } else if (q.right != null && Math.abs(n - q.weight) > Math.abs(n - findmin(q).weight)) {
                q = q.right;
            } else {
                break;
            }
        }
        return q.weight;
    }

}


public class Main {
    public static void main(String[] args) {
        QReader reader = new QReader();
        QWriter writer = new QWriter();
        long num = reader.nextLong();
        AVL_tree bunnies = new AVL_tree();
        AVL_tree girls = new AVL_tree();
        long ans = 0;
        for (long i = 0; i < num; i++) {
            if (reader.nextInt() == 1) {
                if (bunnies.root == null) {
                    girls.insert(girls.root, reader.nextLong());
                } else {
                    long n = reader.nextLong();
                    long node = bunnies.find_ans(n);
                    ans += Math.abs(n - node);
                    bunnies.delete(bunnies.root, node);
                }
            } else {
                if (girls.root == null) {
                    bunnies.insert(bunnies.root, reader.nextLong());
                } else {
                    long n = reader.nextLong();
                    long node = girls.find_ans(n);
                    ans += Math.abs(n - node);
                    girls.delete(girls.root, node);
                }
            }
        }
        writer.println(ans);
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