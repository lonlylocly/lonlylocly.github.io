package tree;

public class Item<K,V> {
    K k;
    V v;
    Item<K,V> p = null;
    Item<K,V> l = null;
    Item<K,V> r = null;

    public Item(K k, V v) {
        this.k = k;
        this.v = v;
    }

    @Override
    public String toString(){
        return k + ": " + v;
    }

    public K getK() {
        return k;
    }

    public V getV() {
        return v;
    }

    public void setK(K k) {
        this.k = k;
    }

    public void setV(V v) {
        this.v = v;
    }

    public Item<K,V> getP() {
        return p;
    }
    public Item<K,V> getL() {
        return l;
    }
    public Item<K,V> getR() {
        return r;
    }

    public void setP(Item<K,V> p) {
        this.p = p;
    }
    public void setL(Item<K,V> l) {
        this.l = l;
    }
    public void setR(Item<K,V> r) {
        this.r = r;
    }


}
