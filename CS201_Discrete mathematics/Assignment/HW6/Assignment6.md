# Assignment6

# 12011517 李子南

## Q1

(a) The maximum number of edges is $\sum^{n-1}_{i = 1}i = \frac{(n-1)n}{2}$

(b) The minimum number of edges is $n - 1$

(c) Assume that the minimum degree of any vertex in $G$ is $\geq (n-1)/2$ and $G$ is not connected

Because $G$ is not connected, then $G$ has at least two unconnected components.

Every vertex in $G$ have degree of $(n-1)/2$  or more.

Thus each component in $G$ have at least $(n-1)/2 + 1$ vertices

Therefore there exist at least $n+1$ vertices in $G$, which leads to a contradiction

Therefore $G$ must be connected.

Q.E.D

## Q2

(a) The adjacent matrix is above

 <img src="IMG_F7B349551308-1.jpeg" alt="IMG_F7B349551308-1" style="zoom:50%;" />

(b) The degree of each vertex in $G_n$ is $C_2^{n-2}$

## Q3

Because $G$ is a simple and self-complementary graph, therefore $G$ must have half of all possible edges.

Set the number of vertices  of $G$ is $v$, then $G$ have $C^{v}_2/2 = \frac{v(v-1)}{4}$ edges.

Because the number of edges is an integer

Therefore $v\equiv 0 \ or\ 1\ (mod\ 4)$

## Q4

(a) We start from choose a random vertex $v$ in set, each time we add a unused vertex. Then 

except for $v$ and the newest added vertex, every used vertices have degree of 2. Because 

the set is finite, we have limited vertex, if we repeat the process until the last vertex in set, 

the last vertex and $v$ have degree of 1, which means we have to connect the last vertex and 

$v$. Therefore $G$ must contain at least one cycle.

(b) Assume that $G$ is connected.

If $(u,v)$ is not an edge of $G$, then it is an edge of $\overline{G}$  

If $(u,v)$ is an edge of $G$, because $G$ is not connected. Then there exist vertex $w$ that neither 

$(w,u)$ nor $(w,v)$ is in $G$. Thus $(u,w)$ and $(w,v)$ is exist in $\overline{G}$ . Therefore there exist a path 

from  $u$ to $v$ in $\overline{G}$ .

Therefore for any two vertices in $\overline{G}$ have a path, $\overline{G}$ is connected.

## Q5

(1) If $a \in A \cup B$, than $a \in A \or a \in B$, therefore $N(a) \sube N(A) \cup N(B)$, thus 

$N(A\cup B) \sube N(A)\cup N(B)$

If $a \in A$, $a \in A \cup B$, therefore $N(a) \sube N(A\cup B)$, thus $N(A) \sube N(A \cup B)$

If $b \in B$, $b \in A \cup B$, therefore $N(b) \sube N(A\cup B)$, thus $N(B) \sube N(A \cup B)$

Therefore $N(A)\cup N(B) \sube N(A\cup B)$

Thus $N(A \cup B) = N(A) \cup N(B)$

(2) If $a \in A \cap B$, then $a \in A \and a \in B$, thus $N(a) \in N(A) \and N(a) \in N(B)$, therefore 

$N(A\cap B) \sube N(A) \cup N(B)$

e.g.

<img src="/Users/lee/课件/CS201_Discrete mathematics/Assignment/HW6/IMG_2E0193FA5F06-1.jpeg" alt="IMG_2E0193FA5F06-1" style="zoom:15%;" />

## Q6

Set one group of $G$ has $n$ vertices, other group has $v-n$ vertices. Then the max number of edges is 

$f(n) = n(v-n)$. Take derivative to $f$, we have $\frac{df(n)}{dn} = v-2n$. When $\frac{df(n)}{dn} = 0$, we will find the max 

value $f(v/2) = v^2/4$ Therefore the max value of $e$ is $v^2 / 4$ 

## Q7

 (a) Because $rad(G) = min_{v\in V}[max_{u \in V} d_G(u,v)]$, clearly $rad(G) \leq diam(G)$

Set $diam(G) = d_G(u,v)$, $rad(G)$ start from $r$, then $diam(G) \leq d_G(u,r) + d_G(r, v)$, for $diam(G)$ is the

 shortest path from $u$ to $v$

Because $rad(G)$ start from $r$, $d_G(u,r) + d_G(r, v) \leq 2rad(G)$

Therefore $rad(G) \leq diam(G) \leq 2rad(G)$

(b)A circle with $2n$ or $2n + 1$ vertices will always have $diam(G) = rad(G) = n$

A line with $2n + 1$ vertices will always have $diam(G) = 2rad(G) = 2n$

## Q8.

In $G$, $u_5$ have 1 path of length 4 to $u_6$. But in $H$, $v_5$ have 2 path of length 4 to $v_7$. In both graph, $u_5,u_6, v_5, v_7$ are all have degree of 3. Therefore $G$ and $H$ are not isomorphic.

##  Q9.

Assume that a a directed multigraph $G$ having no isolated vertices has an Euler circuit.

Because Euler circuit is a simple circuit containing every edges of $G$, then the in-degree and 

out-degree of every vertices must be equal. By going through Euler circuit, we can find a 

path between every two vertices. Therefore $G$ must be weakly connected.

The first part is proved.

 Assume that for a weakly connected graph $G$, every vertices $v \in G$ has 

$deg^+(v) = deg^-(v)$

We arbitarily choose a vertex $u$ and any out edge. Because every vertices in $G$ have 

$deg^+(v) = deg^-(v)$, then we can always find another unvisited out edge in next 

vertex. In the end the only vertex which have unvisited out  is $u$. Since there is 

always an out edge we can visit any vertex other than $u$. Therefore there must exist 

a cycle contain $u$

Then we chose a random vertex $v$ and find a circuit begin and end at $v$. We delete 

all edges contained in the cycle in $G$. After deletion, all vertices' out-degree still 

equal to in-degree. By repeating the deletion, we can find cycles that have common 

vertices. At last we can build a cycle goes through all other edges and back to itself 

Thus we find an Euler circuit .

Therefore we proved a directed multigraph having no isolated vertices has an Euler circuit if and only if the graph is weakly connected and the in- degree and out-degree of each vertex are equal.

## Q10.

$K_5, K_{3,3}, K_{3,4}$

<img src="/Users/lee/课件/CS201_Discrete mathematics/Assignment/HW6/IMG_A907EF4750CB-1.jpeg" alt="IMG_A907EF4750CB-1" style="zoom:50%;" />

## Q11.

When $m=1\ or\ n = 1$

Because when $m \not=1\and n \not=1$

There must exist a simple circuit between any $u \in M,v\in N$, it's not a tree.

Therefore any complete bipartite graphs $K_{m,n}$ with $m=1\ or\ n = 1$ is a tree

## Q12.

Using mathematical induction to prove

Set $f(n)$ to be n-cube has a Hamilton circuit

Basic step: Obviously $Q_1$ and $Q_2$ have a Hamilton path. $f(1)$ and $f(2)$ are true

Inductive step: Assume $f(n)$ is true. Because we can construct $Q_{n+1}$ from $Q_n$ with 

two $Q_n$ and $2^n$ new edges. We can go through the one of the Hamilton path in $Q_n$ , then go 

through any edges that connect two $Q_n$. Then go through the Hamilton path of another 

$Q_n$ start from that point. Thus we go through all vertices once, the Hamilton path of $Q_{n+1}$ is 

constructed.

$f(n + 1)$ is true

Therefore we proved that every n-cube has a Hamilton path.

## Q13.

(1) $G$ is bipartite, $H$ is not

(2) No, vertex $h$ in $H$ has a circuit of length 3, but vertex $g$ in $G$ doesn't have.

(3)Neither of them have an Euler circuit

## Q14.

Vertex $v$ have 16 edges out. According to Pigeon hole principle, there must be $\lceil 16/3 \rceil = 6$ students are 

talking about same problem $A$ with $v$. Consider these 6 students to be $K_6$. 

If there exist a pair students $(p,q)$ in $K_6$ are talking about $A$, then there exist $(v,p,q)$ are discussing the same problem $A$. 

If there are no pairwise students in $K_6$ are talking about $A$. Choosing a vertex$u$ in $K_6$. Then according to PHP, there must be $\lceil 5/2 \rceil = 3$ students talking about the same problem with $u$. If all students in $K_3$ all pairewise talking about the same question other than they talking with $u$, then we find  that 3 students. Others, their exist other 2 students pairwise talking the same problem with $u$.

$Q.E.D$

## Q15

(a) 3

(b) 16

(c) 4

(d) 5

## Q16

(a) 1

(b) 2

(c) 3