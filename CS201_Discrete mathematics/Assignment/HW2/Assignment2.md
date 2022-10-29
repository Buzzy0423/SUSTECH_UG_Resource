# Assignment2

## 12011517 李子南

### Q1.

(a) If $A-B=A$, then $B \not \sube A$ or $B = \empty$ 

When $A-B=A$ is true, then $B\sub A$ Is false

Therefore the statement is false

(b)$\because(A\cap B\cap C)=\{x| (x\in A) \and (x \in B) \and (x \in C)\}$

$((x \in B) \or (x \in A)) \rightarrow (x \in (A\cup B))$

$\therefore (x \in (A\cap B\cap C)) \rightarrow (x \in (A\cup B))$

The statement is true

(c)$\because \overline{(A - B)} = \overline{(A\cap\overline{B})}=\overline{A}\cup B$

$B-A = B \cap \overline{A}$

$\therefore \overline{(A-B)}\cap (B-A) = (\overline{A}\cup B)\cap (B \cap \overline{A})=B \cap \overline{A}$

The statement is not true

### Q2.

(a)The symmetric difference is associative.

$A \oplus(B \oplus C)=A \oplus((B \and \neg C) \or (\neg B \and C))=(A\and B \and C)\or(\neg A \and \neg B \and C)\or(\neg A \and B\and \neg C)\or (A\and \neg B \and \neg C)$

$(A \oplus B) \oplus C = (A\and B \and C)\or(\neg A \and \neg B \and C)\or(\neg A \and B\and \neg C)\or (A\and \neg B \and \neg C)$

Therefore $A \oplus(B \oplus C)=(A \oplus B) \oplus C$

(b)Yes

$A \oplus C = A\cup C -A\cap C=(A-(A\cap C))+(C-(A\cap C))$

$B \oplus C = B \cup C - B\cap C = (B-(B\cap C))+(C-(B\cap C))$

$\because A \oplus C = B \oplus C$

$ A\cap C = B\cap C$

$\therefore A=B$

### Q3.

(1)$(B-A)\cup(C-A)=(B\cap\overline{A})\cup(C\cap\overline{A})=(B\cup C) \cap \overline{A}$

$(B\cup C)-A=(B\cup C) \cap \overline{A}$

$\therefore(B-A)\cup(C-A) =(B\cup C)-A=(B\cup C) \cap \overline{A}$

(2)$(A\cap B)\cap \overline{(B\cap C)} \cap (A\cap C) = A\cap (B \cap C)\cap\overline{(B\cap C)} = \empty$

### Q4.

If $A \sube B$

$\forall a \in A(a\in B)$

So $(P(a) \in P(A)) \and (P(a) \in P(B))$

$\forall a \in A(P(a)\in P(B))$

$\therefore P(A) \sube P(B)$

If $P(A)\sube P(B)$

$\forall a \in A(P(a)\in P(B))$

Which means $\exist b (P(a)=P(b))$

$\because P \ is \ injective $

$a=b$

$\forall a \in A \exist b \in B(a=b)$

$\therefore A \sube B$

### Q5.

(a)A function is onto but not one to one

$f(x)=f(-x)$, it's not one ot one

$\forall (y\geq0)\exist x\in R(f(x)=y)$, it's onto

(b)A function which is neither one to one nor not onto

$f(3)=6$ is not in $\{2,4\}$,so it's not one to one

$4$ has no x in $\{1,3\}$ that f(x) = 4

(c)A function which is both one to one and onto

When $x \not=y$, $8-2x \not= 8-2y$, therefore f(x) is one to one

There exist f(x) = y if and only if 8-2x = y and x = 4-0.5y, it's onto

(d)A function which is onto but bot one to one

f(1) = f(1.5), it's not one to one

$\forall y \in Z\exist x \in R (f(x) = \lfloor x +1\rfloor)$, it's onto

(e)A function which is onto but not one to one 

$f(0.5) \not \in R^+$, it's not one to one 

$\forall (y \in R^+) \exist(x \in R^+)f(x)=y$, it's onto

(f)A function which is one to one but not onto

When $x \not= y, x+1 \not=y+1$, therefore f(x) is one to one

$\forall x \in Z^+ f(x) \not= 1$, it's not onto

### Q6.

(c)Because f(x) is both onto and one to one, it has $f^{-1}(x)=4-0.5x$ 

### Q7.

If $x\%1 \in [0,\frac{1}{3}]$

$\lfloor x\rfloor = \lfloor x+\frac{1}{3}\rfloor =\lfloor x+ \frac{2}{3}\rfloor= x$

$\lfloor x\rfloor + \lfloor x+\frac{1}{3}\rfloor +\lfloor x+ \frac{2}{3}\rfloor = \lfloor 3x\rfloor =3x$

If $x\%1 \in [\frac{1}{3},\frac{2}{3}]$

$\lfloor x\rfloor = \lfloor x+\frac{1}{3}\rfloor = x$

$\lfloor x+\frac{2}{3}\rfloor = x+1$

$3\lfloor x\rfloor = 3\lfloor x/1\rfloor + \lfloor 3(x\%1) \rfloor=3x+1$

$\lfloor 3x\rfloor =\lfloor x\rfloor + \lfloor x+\frac{1}{3}\rfloor +\lfloor x+ \frac{2}{3}\rfloor =3x+1 $

If $x\%1 \in [\frac{2}{3},1)$

$\lfloor x\rfloor =x$

$\lfloor x+\frac{1}{3}\rfloor = \lfloor x+\frac{2}{3}\rfloor = x+1$

$3\lfloor x\rfloor = 3\lfloor x/1\rfloor + \lfloor 3(x\%1) \rfloor=3x+2$

$\lfloor 3x\rfloor =\lfloor x\rfloor + \lfloor x+\frac{1}{3}\rfloor +\lfloor x+ \frac{2}{3}\rfloor =3x+2 $

### Q8.

(a)yes

Assume $f(g(a))=f(g(b))$

Because $ f \circ g$ is one to one

Therefore $a=b$

Because $g$ is one to one

Then $g(a) =g(b)$

Therefore $f$ is one to one 

(b)yes

(c)yes

proof:(b)&(c)

If $f \circ g$ is one to one

Assume $g(a)=g(b)$

Then $f(g(a))=f(g(b))$

Because $f\circ g$ is one to one 

Therefore $a=b$, $g$ is one to one, $f$ is one to one

(d)Let $c\in C$

Because $f\circ g$ is onto, there is $a \in A$ that $c=f\circ g(a)$ 

Let $b=g(a), b\in B$

Therefore for every $ c\in C$, there is $b \in B$ such that $c=f(b)$

Therefore $f$ is onto

(e)No

For example $A=\{0\}, B=\{0,1\}, C=\{0\}$ with $g(0)=0,f(0)=0,f(1)=1$

In this case $f$ and $f\circ g$ is onto but $g$ is not onto

### Q9.

$(n+1)^3-n^3=3n^2+3n+1$

$n^3-(n-1)^3=3(n-1)^2+3(n-1)+1$

$(n+1)^3-n^3+n^3-(n-1)^3+...+2^3-1^3=(n+1)^3-1^3=3(n^2+(n-1)^2+...+2^2+1^2)+3(n+(n-1)+...+2+1)+n$

$\sum_{k=1}^{n}k^2=\frac{n(n+1)(2n+1)}{6}$

### Q10.

(a)$A\{x|x\in N\},\ B\{x \in Z^+\}$

(b)$A\{x|x=2n, n \in N\},\ B\{x|x=4n,n\in N\}$

(c)$A\{x|x\in R\},\ B\{x|x\in R^+\}$

### Q11.

(a)The set is countable. Assume that CS201 have n students, there exist $C^k_n$ subsets of size k. Therefore the set of all subsets of students in CS201 have $\sum_{k=1}^n C_n^k$ objects in it. It's countable.

(b)The set is countable. We can list all object as follow:
$$
\begin{matrix}
    (0,0) & (0,1) & (0,2) & \cdots & (0,n) \\
    (1,0) & (1,1) & (1,2) & \cdots & (1,n) \\
    \vdots & \vdots & \vdots & \ddots & \vdots \\
    (n,0) & (n,1) & (n,2) & \cdots & (n,n) \\
\end{matrix}
$$
(c)The set is uncountable. Assume that the set is countable, then we list it as: 
$$
\begin{matrix}
    (0,b_1) & (0,b_2) & (0,b_3) & \cdots & (0,b_n) \\
    (1,b_1) & (1,b_2) & (1,b_3) & \cdots & (1,b_n) \\
    \vdots & \vdots & \vdots & \ddots & \vdots \\
    (n,b_1) & (n,b_2) & (n,b_3) & \cdots & (n,b_n) \\
\end{matrix}
$$
Because set R is uncountable, then sequence  $b_1,\ b_2...b_n$ can't have all numbers in R, so this set can't be listed.

Therefore this set is also uncountable.

### Q12.

Suppose that $A-B$ is a countable set.

$B$ is a countable set.

Then $(A-B)\cup B$ is countable.

$A \sube ((A-B)\cup B)$

Therefore $A$ is contained in a countable set.

But $A$ is an uncountable set.

It's contradictory.

Therefore $A-B$ is an uncountable set

### Q13.

$\because m\in Z^+, n \in Z^+$

$\frac{\partial f}{\partial m}=m+n-\frac{1}{2}>0$

$\frac{\partial f}{\partial n}=n+m-\frac{3}{2}>0$

Therefore $f$ is strictly increasing, $f$ is one to one.

Suppose $f(m-1,1)=k,k\in Z^+$, then we can get $f(m-1,1)+1=f(1,m)$

$f(1,1)=1$

Therefore $\forall y \in Z^+ \exist x \in Z^+((f(1,x)=y )\or (f(x,1)=y))$, $f$ is onto

Therefore set $Z^+*Z^+$ is countable

### Q14.

There exist $f(x)=x$ is a injective function from $(0,1)$ to $[0,1]$

There also exist $f(x)=\frac{1}{2}x$ is a injective function from $[0,1]$ to $(0,1)$

By Schro ̈der-Bernstein theorem we have$|(0,1)|=|[0,1]|$

### Q15.

If $|A|=|B|$ and $|B|=|C|$

Then there exist an injective function $f:A\rightarrow B$ and $g:B\rightarrow C$ and $p:B\rightarrow A$ and $q: C\rightarrow B$

So $h=g\circ f$ is an injective function from $A \rightarrow C$ and $r=p\circ q$ is an injective function from $C \rightarrow A$

By Schro ̈der-Bernstein theorem we have $|A|=|C|$

### Q16.

$\because f(x)\ is \ \Theta(g(x)), g(x)\ is\ \Theta(h(x))$

There exist $x>k_1, k_1\in Z^+$, which $C_1|g(x)|\leq |f(x)| \leq C_2|g(x)|, C_1 \in R, C_2 \in R$

There exist $x>k_2,k_2\in Z^+$, which $C_3|h(x)|\leq|g(x)|\leq C_4|h(x)|, C_3\in R, C_4\in R $

Therefore there exist$x>max(k_1,k_2)$, $C_1C_3|h(x)|\leq |f(x)| \leq C_2C_4|h(x)|$

$\therefore f(x)\ is\ \Theta(h(x))$

### Q17.

(a)$2n$ multiplications and $n$ additions are used

(b)$\Theta(n)$

