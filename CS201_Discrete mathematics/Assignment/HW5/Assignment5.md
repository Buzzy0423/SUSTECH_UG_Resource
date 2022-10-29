# Assignment5

## 12011517 李子南

### Q1.

Suppose R is an antisymmetric relation, S is a subset of R

Because R is antisymmetric

Thus $(a,b) \in R \and (b,a) \in R \rightarrow (a = b)$

Therefore $(a,b) \in R \and (a \not = b) \rightarrow (b,a) \not \in R$

Because $S\sube R$

Therefore $(a,b) \not\in R \rightarrow (a,b) \not\in S$

Then $(a,b) \in S \and (a \not = b) \rightarrow (b,a) \not \in S$

This equals $(a,b) \in S \and (b,a) \in S \rightarrow (a = b)$

Therefore the subset of antisymmetric relation is also antisymmetric

### Q2.

$R$ is symmetric

$R^* = \cup ^{\infty}_{k = 1}R^k$

If $(a,b)\in R^*$, then there exist $(a, v_1)\in R,(v_1,v_2)\in R,\ ...\ , (v_k, b)\in R$

Because $R$ is symmetric

Therefore there also exist $(b, v_k) \in R, \ ...\ ,(v_2, v_1)\in R,(v1, a)\in R$

Therefore $(b,a)\in R$

Therefore $R^*$ is also symmetric

### Q3.

$R^2 = R \circ R$

For every $(a,b) \in R$

Because $R$ is reflexive, there exist $(b ,b) \in R$ 

Therefore $(a,b)\in R^2$

Therefore $R \sube R^2$

### Q4.

Yes

Because R is a symmetric relation on a set A

Then $(a,b)\in R \rightarrow (b,a) \in R$

Therefore $(a,b) \not \in R \rightarrow (b,a) \not \in R$

Thus $(a,b) \in \overline{R} \rightarrow (b,a) \in \overline{R}$

Therefore the $\overline{R}$ is symmetric

### Q5.

(a) Yes 

Because for $n_i > 0$, $\prod_{k = 0}^{i}n_i \geq \sum_{k = 0}^{i}n_i$

Therefore $n\preccurlyeq n$, $(n,n) \in R$

Therefore this relation is reflective

(b)No

e.g.

$75 \preccurlyeq 14$ becasuse $3+5 \leq 2*7$

$14 \preccurlyeq 75$ because $2 + 7 \leq 3 * 5$

(c)No

e.g.

$26 \preccurlyeq 22$ because $2 + 13 \leq 2 * 11$

$22 \preccurlyeq 14$ because $2 + 11 \leq 2 * 7$

But $26 \not \preccurlyeq 14$ because $2 + 13 > 2 * 7$

### Q6.

$R = \begin{bmatrix}0& 1 & 0\\0 & 0 & 1\\ 1 & 0 & 0\end{bmatrix}$

$R^2 =\begin{bmatrix}0& 0 & 1\\1 & 0 & 0\\ 0 & 1 & 0\end{bmatrix}$

$R^3 = \begin{bmatrix}1 & 0 & 0\\0 & 1 & 0\\ 0 & 0 & 1\end{bmatrix}$

$R^* = R \cup R^2 \cup R^3$

### Q7.

(1) $R$ is reflective because everyone have the same sign of zodiac with himself $(x, x) \in R$

$R$ is symmetric because x and y have the same sign of the zodiac then y and x have the same sign of the zodiac $(x, y) \in R \and (y, x) \in R$

$R$ is transitive because x and y have the same sign of the zodiac and y and z have the same sign of the zodiac then x and z have the same sign of the zodiac  $(x, y) \in R \and (y,z) \in R \rightarrow (x, z) \in R$

Therefore this is a equivalence relation

(2)$R$ is reflective because everyone was born in the same year with himself $(x, x) \in R$

$R$ is symmetric because x and y were born in the same year then y and x was born in the same year $(x, y) \in R \and (y, x) \in R$

$R$ is transitive because x and y were born in the same year and y and z were born in the same year then x and z were born in the same year$(x, y) \in R \and (y,z) \in R \rightarrow (x, z) \in R$

Therefore this is a equivalence relation

(3) $R$ is not transitive

e.g. x, y have been in Shanghai, y, z have been in Beijing, but x have not been in Beijing

In this case $(x,y) \in R, (y, z)\in R$ but $(x,z) \not \in R$

Therefore $R$ is not equivalence

### Q8.

$R$ is reflective $\forall x \in R, x-x = 0 \in Q$

$R $ is symmetric $x - y \in Q \rightarrow(y-x) = -(x - y) \in Q$ because $Q$ is close under negative

$R$ is transitive $x - y \in Q,y-z \in Q$ 

Thus $(x-y) + (y - z) = x - z \in Q$

Therefore $R$ is transitive

$[1] = k, k \in Q$

$[\frac{1}{2}] = k,k \in Q$

$[\pi] = k + \pi, k \in Q$

### Q9.

(a)No

$\propto$ is not symmetric e.g. $n  = O(n^2)$ but $n^2 \not= O(n)$

(b)Yes

$\propto$ is reflective $f \leq \frac{1}{2}f$, $f = O(f)$

$\propto$ is antisymmetric 

If $f = O(g), f \not = g$, there exist $f \leq Cg$

There doesn't exist $g \leq C^`f$

Therefore $\propto$ is antisymmetric

$\propto$ is transitive

If $f \leq C_1g, g \leq C_2h$

Then $f \leq C_1C_2h$

$f = O(h)$

Therefore $\propto$ is transitive

Thus $\propto$ is partial ordering

(c) Yes 

Every function is either $f \propto g$ or $g \propto f$ 

Therefore $\propto$ is a total ordering

### Q10.

$\preccurlyeq$ is reflective 

Because $R \sube R$ for every $R \in R(S)$

$\preccurlyeq$ is antisymmetric 

Because if $R_1 \sube R_2, R_1 \not =R_2$, then $R_2 \not \sube R_1$

$\preccurlyeq$ is transitive 

Because if $R_1 \sube R_2, R_2 \sube R_3$

Then $R_1 \sube R_3$

Therefore $(R(S), \preccurlyeq)$ is a poset

### Q11.

(a)There exists a nonempty R ⊆ P(N) with no maximal element.

Assume set $R = \{\{1\}, \{1,2\},...,\{1,2,...k\}\}, n \in N$

$\{1,2,...,n\}$ is the maximal element in $R$

Because $N$ is an infinite set

We can always find $\{1,2,...,n \} \sube \{1,2,...,n + 1\}, n\in N$ in $R$

Which means we can not find a maximal value in $R$

Therefore there exists a nonempty R ⊆ P(N) with no maximal element

(b)There exists a nonempty R ⊆ P(N) with no minimal element.

Assume set $R =\{\{k,...,n\},\{k+1,...,n\},...\}, k \in N,n \in N, k < n$

Because $n \in N, N$ is an ifinite set

We can always find $\{x + 1,...,n\} \sube \{x,...,n\}$

Which means we can not find a minimal value in $R$

Therefore there exists a nonempty R ⊆ P(N) with no minimal element

(c)There exists a nonempty T ⊆ P(N) that has neither minimal nor maximal elements.

Set $\{p,...,q\} \in T$ is the minimal element of $T$, $\{j,...,k\} \in T$ is the maximal element of $T$ and $j < p < q < k$

Because $p,q,j,k \in N$, $N$ is an infinite set

Therefore we can always find $\{p + 1,...,q - 1\} \sube\{p,...,q\}$, $\{j,...,k \}\sube \{j - 1,...,k + 1\}$ as new minimal and maximal element

Thus,  there exists a nonempty T ⊆ P(N) that has neither minimal nor maximal elements.

### Q12.

(a)$n$

(b) $a,b,c$

(c) Yes $n$

(d) No

(e) $l,n$

(f) $l$

(g) There is no lower bound of $\{f,g,h\}$

(h) None