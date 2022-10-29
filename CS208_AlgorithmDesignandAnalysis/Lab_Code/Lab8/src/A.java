//import java.io.*;
//import java.util.StringTokenizer;
//
//public class A {
//    static int max = 131072;
//    static double pi = 3.1415;
//    static long arr[] = new long[max];
//
//    public static class Complex {
//        private final double re;
//        private final double im;
//
//        public Complex(double real, double imag) {
//            re = real;
//            im = imag;
//        }
//
//        public String toString() {
//            if (im == 0)
//                return re + "";
//            if (re == 0)
//                return im + "i";
//            if (im < 0)
//                return re + " - " + (-im) + "i";
//            return re + " + " + im + "i";
//        }
//
//        public double abs() {
//            return Math.hypot(re, im);
//        }
//
//        public double phase() {
//            return Math.atan2(im, re);
//        }
//
//        public Complex plus(Complex b) {
//            Complex a = this; // invoking object
//            double real = a.re + b.re;
//            double imag = a.im + b.im;
//            return new Complex(real, imag);
//        }
//
//        public Complex minus(Complex b) {
//            Complex a = this;
//            double real = a.re - b.re;
//            double imag = a.im - b.im;
//            return new Complex(real, imag);
//        }
//
//        public Complex multiple(Complex b) {
//            Complex a = this;
//            double real = a.re * b.re - a.im * b.im;
//            double imag = a.re * b.im + a.im * b.re;
//            return new Complex(real, imag);
//        }
//
//        public Complex multiple(double alpha) {
//            return new Complex(alpha * re, alpha * im);
//        }
//
//        public Complex scale(double alpha) {
//            return new Complex(alpha * re, alpha * im);
//        }
//
//        public Complex conjugate() {
//            return new Complex(re, -im);
//        }
//
//        public Complex reciprocal() {
//            double scale = re * re + im * im;
//            return new Complex(re / scale, -im / scale);
//        }
//
//        public double re() {
//            return re;
//        }
//
//        public double im() {
//            return im;
//        }
//
//        public Complex divides(Complex b) {
//            Complex a = this;
//            return a.multiple(b.reciprocal());
//        }
//
//        public Complex exp() {
//            return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
//        }
//
//        public Complex sin() {
//            return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
//        }
//
//        public Complex cos() {
//            return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
//        }
//
//        public Complex tan() {
//            return sin().divides(cos());
//        }
//
//        public static Complex plus(Complex a, Complex b) {
//            double real = a.re + b.re;
//            double imag = a.im + b.im;
//            Complex sum = new Complex(real, imag);
//            return sum;
//        }
//
//        public boolean equals(Object x) {
//            if (x == null)
//                return false;
//            if (this.getClass() != x.getClass())
//                return false;
//            Complex that = (Complex) x;
//            return (this.re == that.re) && (this.im == that.im);
//        }
//
//    }
//
//    static Complex ans[] = new Complex[max];
//
//    public static void main(String[] args) {
//        QReader reader = new QReader();
//        QWriter writer = new QWriter();
//        double n = reader.nextInt();
//        long m = (long) Math.pow(2, n);
//        for (int i = 0; i < m; i++) {
//            arr[i] = reader.nextLong();
//        }
//
//    }
//
//    private double[] FFT(int x, int N, int s) {
//        if (N == 1) {
//            return new double[]{arr[N]};
//        } else {
//            double omega = Math.exp(-2 * pi / N);
//            double[] arr1 = FFT(x, N / 2, 2 * s);
//            double[] arr2 = FFT(x + s, N / 2, 2 * s);
//            double[] tmp = new double[N];
//            for (int i = 0; i < N / 2; i++) {
//                double p = arr1[i];
//                double q = omega * arr2[i];
//                tmp[i] = p + q;
//                tmp[i + N / 2] = p - q;
//            }
//            return tmp;
//        }
//    }
//
//}
//
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