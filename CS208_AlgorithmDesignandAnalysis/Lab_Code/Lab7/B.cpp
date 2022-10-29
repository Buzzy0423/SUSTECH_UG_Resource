#include <iostream>
using namespace std;

const int n = 2e5 + 1;
int arr[n];

struct node {
    long long sum, l_max, r_max, max;
} d[4 * n];

inline void bulid(int l, int r, int index);
inline void update(int index);
inline node merge(node p, node q);
inline node query(int l, int r, const int l_edge, const int r_edge, int index);

int main() {
    ios::sync_with_stdio(false);
    int num;
    cin >> num;
    for (size_t i = 1; i < num + 1; i++) {
        cin >> arr[i];
    }
    bulid(1, num + 1, 1);
    int m, l, r;
    cin >> m;
    for (size_t i = 0; i < m; i++) {
        cin >> l >> r;
        node ans = query(1, num + 1, l, r, 1);
        cout << ans.max << endl;
    }
}

inline void bulid(int l, int r, int index) {
    if (l == r) {
        d[index].l_max = d[index].r_max = d[index].max = d[index].sum = arr[l];
        return;
    }
    int mid = (r + l) / 2;
    bulid(l, mid, index * 2);
    bulid(mid + 1, r, index * 2 + 1);
    update(index);
}

inline void update(int index) {
    d[index].sum = d[index * 2].sum + d[index * 2 + 1].sum;
    d[index].l_max = max(d[index * 2].l_max, d[index * 2].sum + d[index * 2 + 1].l_max);
    d[index].r_max = max(d[index * 2 + 1].r_max, d[index * 2 + 1].sum + d[index * 2].r_max);
    d[index].max = max(max(d[index * 2].r_max + d[index * 2 + 1].l_max, d[index * 2].max), d[index * 2 + 1].max);
}

inline node merge(node p, node q) {  // p is left node, q is right node
    node tmp;
    tmp.sum = p.sum + q.sum;
    tmp.l_max = max(p.l_max, p.sum + q.l_max);
    tmp.r_max = max(q.r_max, q.sum + p.r_max);
    tmp.max = max(max(p.r_max + q.l_max, p.max), q.max);
    return tmp;
}

inline node query(int l, int r, const int l_edge, const int r_edge, int index) {
    if (l_edge <= l && r <= r_edge) {
        return d[index];
    }
    int mid = (l + r) / 2;
    if (l_edge <= mid && r_edge > mid) {
        node rr, ll;
        ll = query(l, mid, l_edge, r_edge, index * 2);
        rr = query(mid + 1, r, l_edge, r_edge, index * 2 + 1);
        return merge(ll, rr);
    } else if (l_edge > mid) {
        return query(mid + 1, r, l_edge, r_edge, index * 2 + 1);
    } else {
        return query(l, mid, l_edge, r_edge, index * 2);
    }
}