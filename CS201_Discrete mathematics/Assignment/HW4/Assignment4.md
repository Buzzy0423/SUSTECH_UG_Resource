# Assignment4

## 12011517 李子南

### Q1.

Set $P(n):\ \overline{A_1 \cup A_2 \cup \ ... \cup A_n} = \overline{A_1} \cap \overline{A_2}\ ...\ \overline{A_n}$

**Base case**

For $P(2)$ :

Set $x \in \overline{A_1 \cup A_2}$

Then $x \not \in (A_1 \cup A_2)$

Therefore $x \not \in A_1 \and x \not \in A_2$

Thus $x \in \overline{A_1} \and x \in \overline{A_2}$

Therefore $x \in \overline{A_1} \cap \overline{A_2}$

(1) $ \overline{A_1 \cup A_2} \sube(\overline{A_1} \cap \overline{A_2})$

Set $y \in \overline{A_1} \cap \overline{A_2}$

Then $y \in \overline{A_1} \and x \in \overline{A_2}$

Therefore $y \not \in A_1 \and y \not \in A_2$

Thus $y \not \in (A_1 \cup A_2)$

Therefore  $y \in \overline{A_1 \cup A_2}$

(2) $(\overline{A_1} \cap \overline{A_2}) \sube \overline{A_1 \cup A_2}$

From (1) & (2) we infer that $(\overline{A_1} \cap \overline{A_2}) = \overline{A_1 \cup A_2}$, $P(2)$ is proved.

**Inductive step**

Assume that $P(n-1)$ holds

Set $\overline{B} = \overline{A_1 \cup A_2 \cup \ ... \cup A_{n-1}} = \overline{A_1} \cap \overline{A_2}\ ...\ \overline{A_{n-1}}$

$P(n): \overline{B \cup A_n} = \overline{B}\cap \overline{A_n}$

Using the same way of proving $P(2)$, we can prove $P(n)$ is true.

### Q2.

Because $a^n-b^n=(a-b)(a^{n-1}+ba^{n-2}+\ ...\ +b^{n-1})$

Thus $a^n-b^n \leq na^{n-1}(a-b) \equiv (a^{n-1}+ba^{n-2}+\ ...\ +b^{n-1}) \leq na^{n-1}$

$0 < b < a,\ n > 0$

Then $b^da^{n-1-d} \leq a^{n-1}(d\in Z^+ \and d < n)$

Therefore $(a^{n-1}+ba^{n-2}+\ ...\ +b^{n-1}) \leq na^{n-1}$ , $a^n-b^n\leq na^{n-1}(a-b)$

Q.E.D

### Q3.

$10, 20, 25, 30, 35, 40, 45, 50, 55, 60\ ...$

Set \$10 to be p, \$25 to be q,  $P(0) = 10, P(n) = 5n + 15(n \in Z^+)$

Set $f(n)$ to be $P(n)$ can be represented as the linear combination of p and q.

**Base step**

$P(0) = p$

$P(1) = 2p$

$P(2) = q$

**Inductive step**

Assume that for all $1 \leq n \leq k$, $f(n)$ is true.

Case 1: $k = 2w(w \in N)$

$P(k) = 5k+15$

$P(k-1) = 5k + 10$

$P(k-1)  + 2p= 5k + 20 = P(k + 1)$

$f(k+1)$ is true 

Case 2: $k = 2w+1(w \in N)$

$P(k) =5k+15$

$P(k-4) = 5k -5$

$P(k-4) + q = P(k+1) = 5k+20$

$f(k=1)$ is true

Therefore $P(n)$  can be represented as the linear combination of p and q.

### Q4.

**From mathematical induction to strong induction**

If mathematical induction holds, then we have:

1.   $P(0)$

2.   $\forall k \in N(P(k) \rightarrow P(k+1))$

3.   $\forall k \in N(\forall m \leq k(P(m)) \rightarrow P(k))$

From 2 & 3 we conclude that $\forall k \in N(\forall m \leq k(P(m)) \rightarrow P(k + 1))$

Thus $\forall k \in N(P(N))$

Therefore property that can be proved by mathematical induction can also be proved by strong induction.

**From strong induction to mathematical induction**

If strong induction holds, then we have:

1.   $P(0)$

     Set $Q(n) = \forall m \leq n(P(n))$

2.   $\forall k \in N(Q(k) \rightarrow P(k + 1))$

3.   $\forall k \in N(Q(k) \rightarrow Q(k)))$

Thus we can get $\forall k \in N(Q(k) \rightarrow Q(k) \and P(k + 1))\equiv \forall k \in N(Q(k) \rightarrow Q(k + 1))$

Then from mathematical induction we can get $\forall k \in N(Q(k)) \equiv \forall k \in N(P(k))$

Therefore property that can be proved by strong induction can also be proved by mathematical induction.

### Q5.

CPP_code

```CPP
long long Q5(long long num, long long exp) {
    if (exp == 0) {
        return num;
    }
    return pow(Q5(num, exp - 1), 2);
}
```

### Q6.

$f(4^k) = 5f(4^{k-1}) + 6 \times 4^k \times 5^0$

$\begin{cases}f(1) = 1 \\f(4^k) = 5^k + \sum_{n =1}^{k}5^{k-n} \times 6\times 4^n\end{cases}$

Prove:

**Base case**

$f(1) = 1$

$f(4^1) = 5 \times f(1) + 6 \times 4 =5 ^ 1 + 5 ^ 0 \times 6 \times 4$

 **Inductive step**

Assume that $f(4^{k-1}) = 5^{k-1} + \sum_{n =1}^{k-1}5^{k-1-n} \times 6\times4^n$

$f(4^k) = 5 \times f(4^{k-1})+6\times 4^k=5^k+\sum_{n =1}^{k-1}5^{k-n} \times 6\times 4^n + 6\times 4^k = 5^k + \sum_{n =1}^{k}5^{k-n} \times 6n$                                                                            

Therefore the equation is true.

### Q7.

(a) If n > 2, then $|A| > |B|$

Therefore there doesn't exist one-to-one func from A to B.

If n <= 2, there exist 2 one-to-one funcs from A to B. 

(b) If the function assigns 0 to both 1 and n. Then the rest n-2 elements in A all have two choices.

If n > 1, then we have $2^{n-2}$ different functions that assign 0 to both 1 and n. If n = 1, then we have only 1 function assign 0 to both 1 and n.

(c)There are n-1 possible numbers to be assigned as 1, the other numbers are assigned as 0.

Therefore there are n-1 functions  

### Q8.

First we pick 4 ranks from 13 ranks, then we pick 2 ranks form the 4 ranks we pick to be pairs.

Then we pick the suit of each rank.

Therefore $N=C_{13}^4\times C_4^2\times C_4^1\times C_4^1 \times C_4^2 \times C_4^2 = 2471040$

### Q9.

Prove:

$C_{240}^{120}=\frac{240!}{120!\times120!}=\frac{\prod_{i=121}^{240}i}{\prod_{j=1}^{120}j}$

Because $2n \in [122,240] \rightarrow x \in [61,120]$

Therefore $C_{240}^{120}=\frac{240!}{120!\times120!}=\frac{\prod_{i=121}^{240}i}{\prod_{j=1}^{120}j} = \frac{\prod^{119}_{i = 61}2i+1}{\prod_{j=1}^{60}j} \times 121 \times 2^{60}$

Therefore we have $242|C_{240}^{120}$

### Q10.

Set $a \in \{1,2,3,4,5,6\}, \ b\in\{1,2,3,4,5,6\}$

When $(a_1,b_1) = (1,6),\ (a_2,b_2) = (6,1)$

$a_1\ mod \ 5 = a_2\ mod \ 5,\ b_1\ mod\ 5=b_2\ mod \ 5$

$N = C_6^1 \times C_6^1 = 36$

36 pairs is needed. 

### Q11.

Set two distinct points to be $(x_i,y_i),\ (x_j,y_j)$

Their midpoint is $(\frac{x_i+x_j}{2},\frac{y_i+y_j}{2})$

If the midpoint has integer coordinate, then $x_i$ and $x_j$, $y_i$ and $y_j$ must be both even or odd.

There exist four cases in total

1.   (even, even)

2.   (even, odd)

3.   (odd, even)

4.   (odd, odd)

Because we have five points in total, according to pigeonhole principle, there must exist two points with same parity pair.

Therefore the midpoint of the line joining at least one pair of these points has integers coordinates.

Q.E.D

### Q12.

Assume that there exist no people who know the same number of people.

Case 1: There exist people who know 0 people in the party

Thus the maximum number that people know is n-2

Case 2: There doesn't exist people who know 0 people in the party

Thus the maximum number that people know is n-1

Therefore there are n-1 possible numbers.

But there are n people in the party.

According to pigeonhole principle, there must be two people who know the same number of other people there.

### Q13.

$a_n = 2a_{n-1} + a_{n-2} - 2a_{n-3}$

The characteristic function is $x^3 = 2x^2 + x -2$

Solve the equation we get:

$\begin{cases}x_1 = 1 \\ x_2 = -1 \\ x_3 =2\end{cases}$

Thus $a_n = \alpha_1 + (-1)^n\alpha_2+2^n\alpha_3$

Because $a_0 = 3,\ a_1 = 6\ a_2 = 0$

Therefore we have $\begin{cases}\alpha_1 = 6 \\ \alpha_2 = -2 \\ \alpha_3 = -1\end{cases}$

Therefore $a_n = 6-(-1)^n \times 2 -2^n$

### Q14.

$a_n=5a_{n-1}-6a_{n-2}$

Solve $x^2-5x + 6=0$ we can get $\lambda_1=2,\ \lambda_2 = 3$

According to formula $a_n=\frac{\lambda_1^n(a_1-\lambda_2a_0)-\lambda_2^n(a_1-\lambda_1a_0)}{\lambda_1-\lambda_2}$

We can get $a_n=3 \times 2^n - 2 \times 3^n$

### Q15.

$a_0=0,\ a_1 = 1,\ a_2 = 2,\ a_3=a_2 + a_1 + 1,\ a_4=a_3 + a_2 + 1,\ a_5=a_4+a_3 + 1,\ a_6 = a_5 + a_4+1$

Assume that $S_n=\{1,2,3,\ ...\ ,n\}$,  $a_n$ denote the number of non-empty subsets of $S_n$ that contain no two consecutive integers

Then $S_{n+1} = S_n + \{n+1\}$

$a_n$ consists of three parts:

1.   The subsets doesn't include $\{n+1\}$, it's $a_n$

2.   The subsets include $\{n+1\}$. Thus these subsets don't include $\{n\}$ , it's $a_{n-1}$

3.   $\{n+1\}$ itself alone as a subset.

Therefore $a_{n+1} = a_n + a_{n-1} + 1,\ a_0 = 0,\ a_1 = 1$

### Q16.

To prove $C(n,r) = C(n-1,r) + C(n-1,r-1)$

By binomial theorem: $(1 + x)^n = \sum_{k=0}^{\infty}C(n,k)x^k$

Since $(1+x)^n = (1+x)^{n-1} + x(1 + x)^{n-1} $

$= \sum_{k=0}^{\infty}C(n-1,k)x^k + \sum_{k=0}^{\infty}C(n-1,k)x^{k + 1}$

$=1 +\sum_{k=1}^{\infty}C(n-1,k)x^k + \sum_{k=1}^{\infty}C(n-1,k-1)x^{k}$

$=1 + \sum_{k=1}^{\infty}(C(n-1,k) + C(n-1,k-1))x^k$

Therefore $\sum_{k=0}^{\infty}C(n,k)x^k = 1 + \sum_{k=1}^{\infty}C(n,k)x^k=1 + \sum_{k=1}^{\infty}(C(n-1,k) + C(n-1,k-1))x^k$

Thus $\sum_{k=1}^{\infty}C(n,k)x^k= \sum_{k=1}^{\infty}(C(n-1,k) + C(n-1,k-1))x^k$

Set k to r : $C(n,r) = C(n-1,r) + C(n-1,r-1)$

Q.E.D
