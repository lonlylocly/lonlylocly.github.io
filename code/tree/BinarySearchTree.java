package tree;

public class BinarySearchTree<K extends Comparable,V> {
    Item<K,V> root = null;
    int size = 0;   

    /**
     *  O(lg n) average, because traverses all tree height
     *  O(n) worst case, if tree is list
     */  
    public void insert(K k, V v) {
        if (k == null) {
            throw new IllegalArgumentException();
        }
        Item<K,V> i = new Item<K,V>(k, v);
        size += 1;
        if (root == null) {
            root = i;
            return;
        }

        Item<K,V> x = root;
        Item<K,V> p = root;
        while (true) {
            if (x == null) {
                break;
            }
            p = x;
            // less than
            if (x.getK().compareTo(k) <= 0){ 
                x = x.getR();
            } else {
                x = x.getL();
            }
        }
        i.setP(p);
        if (p.getK().compareTo(k) <= 0){
            p.setR(i);
        } else {
            p.setL(i);
        }
    }

    // same as insert
    public Item<K,V> search(K k) {
        Item<K,V> x = root;
        while(x != null && !k.equals(x.getK())) {
            if (x.getK().compareTo(k) <= 0) {
                x = x.getR();
            } else {
                x = x.getL();
            }
        }
        return x;
    }

    // O(1) when one child or none
    // O(lg N) if two
    public void delete(Item<K,V> i) {
        if (i.getL() == null && i.getR() == null) {
            if (i.getP() != null) {
                declineChild(i, null);
            } else {
                root = null;
            }
        } else if (i.getL() != null && i.getR() != null) {
            Item<K,V> s = successor(i);
            declineChild(s, s.getR());
            i.setK(s.getK());
            i.setV(s.getV());
        } else {
            Item<K,V> x = i.getL();
            if (x == null) {
                x = i.getR();
            }
            if (i.getP() != null) {
                declineChild(i, x);
            } else {
                root = x;
                x.setP(null);
            }
        }
        size -= 1;
    }

    private void declineChild(Item<K,V> c, Item<K,V> newC) {
        Item<K,V> p = c.getP();
        if (c.getP().getR() == c) {
            c.getP().setR(newC);
        } else if(c.getP().getL() == c) {
            c.getP().setL(newC);
        } else {
            throw new IllegalStateException();
        }

        if (newC != null) {
            newC.setP(p);
        }
    }
    
    public Item<K,V> treeMin(Item<K,V> i) {
        return treeLimit(i, true);
    }

    public Item<K,V> treeMax(Item<K,V> i) {
        return treeLimit(i, false);
    }

    // O(ln N) since traverses all tree high
    public Item<K,V> treeLimit(Item<K,V> i, boolean min) {
        Item<K,V> x = i;
        Item<K,V> p = i;
        while (x != null) {
            p = x;
            if (min) {
                x = x.getL();
            } else {
                x = x.getR();
            }
        }
        return p;
    }

    // O(lg N) again
    public Item<K,V> successor(Item<K,V> i) {
        if (i.getR() != null) {
            return treeMin(i.getR());
        }
        Item<K,V> p = i.getP();
        while (p!= null && p.getR() == i) {
            p = p.getP();
        } 
        return p;
    }

    public Item<K,V> predecessor(Item<K,V> i) {
        if (i.getL() != null) {
            return treeMax(i.getL());
        }
        Item<K,V> p = i.getP();
        while (p!= null && p.getL() == i) {
            p = p.getP();
        } 
        return p;    
    }

    public int size() {
        return size;
    }

    public void printAsc(){
        printAsc(root);
        System.out.println(";;");
    }

    // depth-first, O(N) - prints all elements, in-order
    public void printAsc(Item<K,V> i){
        if (i==null) {
            return;
        }        
        printAsc(i.getL());
        System.out.println(i);
        printAsc(i.getR());
    }

    public void printTree() {
        printTree(root, 1);
    }

    // depth-first, O(N), pre-order
    public void printTree(Item<K,V> i, int t) {
        if (i == null) {
            System.out.println(";;");
            return;
        }
        String tab = "";
        for(int j = 0; j < t; j++) {
            tab += "  ";
        }
        System.out.println(i + ";; p: " + i.getP());
        System.out.print(tab + "R: ");
        printTree(i.getR(), t + 1);
        System.out.print(tab + "L: ");
        printTree(i.getL(), t + 1);
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer,String> t = new BinarySearchTree<Integer,String>();
        
        t.printTree();
        
        int size = 0;
        for(int i : new int[]{1, 5, 34, 12, 9, 28}) {
            t.insert(i, "Item " + i);
            size += 1;
            assert t.size() == size; 
        }
        t.printTree();
        
        assert t.successor(t.search(12)).getK().equals(28);

        for(int i : new int[]{5, 12, 9, 1, 34, 28}) {
            System.out.println("Remove " + i);
            Item s = t.search(i);
            
            assert s.getK().equals(i);

            t.delete(s);
            size -= 1;

            assert t.size() == size;
            assert t.size() >= 0;

            t.printTree();
        }

        assert false;
    }
}
