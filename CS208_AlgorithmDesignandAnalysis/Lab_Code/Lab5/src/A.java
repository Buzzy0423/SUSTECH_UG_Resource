//import java.io.*;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.Map;
//import java.util.StringTokenizer;
//
//class node {
//    int k;
//    int v;
//    node prev;
//    node rear;
//
//    node(int kk, int vv) {
//        this.k = kk;
//        this.v = vv;
//    }
//}
//
//class List {
//    private node first;
//    private node last;
//
//    List() {
//        first = null;
//        last = null;
//    }
//
//    public void add(node p) {
//        if (first == null && last == null) {
//            first = p;
//            p.prev = null;
//            p.rear = null;
//        } else {
//            last.rear = p;
//            p.prev = last;
//            p.rear = null;
//        }
//        last = p;
//    }
//
//    public node pollFirst() {
//        node p = first;
//        first = first.rear;
//        remove(p);
//        return p;
//    }
//
//    public void remove(node p) {
//        if (p == first) {
//            first = p.rear;
//        }
//        if (p == last) {
//            last = p.prev;
//        }
//        if (p.prev != null) {
//            p.prev.rear = p.rear;
//        }
//        if (p.rear != null) {
//            p.rear.prev = p.prev;
//        }
//    }
//
//}
//
//
//public class A {
//    public static void main(String[] args) {
//        QReader reader = new QReader();
//        QWriter writer = new QWriter();
//        int n = reader.nextInt();
//        int m = reader.nextInt();
//        int counter = 0;
//        Map<Integer, node> map = new HashMap<>();
//        List list = new List();
//        LinkedList ll = new LinkedList();
//        ll.remove(new node(1,2));
//        ll.add(new node(1,2));
//        for (int i = 0; i < m; i++) {
//            if (reader.next().equals("get")) {
//                int k = reader.nextInt();
//                if (map.get(k) == null) {
//                    writer.println(-1);
//                } else {
//                    node p = map.get(k);
//                    list.remove(p);
//                    list.add(p);
//                    writer.println(p.v);
//                }
//            } else {
//                int k = reader.nextInt();
//                int v = reader.nextInt();
//                if (map.get(k) == null) {
//                    if (counter < n) {
//                        map.put(k, new node(k, v));
//                        list.add(map.get(k));
//                        counter++;
//                    } else {
//                        node tmp = list.pollFirst();
//                        map.remove(tmp.k);
//                        map.put(k, new node(k, v));
//                        list.add(map.get(k));
//                    }
//                } else {
//                    node tmp = map.get(k);
//                    list.remove(tmp);
//                    map.remove(tmp.k);
//                    map.put(k, new node(k, v));
//                    list.add(map.get(k));
//                }
//            }
//        }
//        writer.close();
//    }
//}
//
////class QReader {
////    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
////    private StringTokenizer tokenizer = new StringTokenizer("");
////
////    private String innerNextLine() {
////        try {
////            return reader.readLine();
////        } catch (IOException e) {
////            return null;
////        }
////    }
////
////    public boolean hasNext() {
////        while (!tokenizer.hasMoreTokens()) {
////            String nextLine = innerNextLine();
////            if (nextLine == null) {
////                return false;
////            }
////            tokenizer = new StringTokenizer(nextLine);
////        }
////        return true;
////    }
////
////    public String nextLine() {
////        tokenizer = new StringTokenizer("");
////        return innerNextLine();
////    }
////
////    public String next() {
////        hasNext();
////        return tokenizer.nextToken();
////    }
////
////    public int nextInt() {
////        return Integer.parseInt(next());
////    }
////
////    public long nextLong() {
////        return Long.parseLong(next());
////    }
////}
////
////class QWriter implements Closeable {
////    private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
////
////    public void print(Object object) {
////        try {
////            writer.write(object.toString());
////        } catch (IOException e) {
////            return;
////        }
////    }
////
////    public void println(Object object) {
////        try {
////            writer.write(object.toString());
////            writer.write("\n");
////        } catch (IOException e) {
////            return;
////        }
////    }
////
////    @Override
////    public void close() {
////        try {
////            writer.close();
////        } catch (IOException e) {
////            return;
////        }
////    }
////}