
public class Heapsort {
    
    int[] a;
    int h;

    public Heapsort(int[] a) {
        this.a = a;
    }

    public int parent(int i) {
        return i / 2;
    }

    public int left(int i) {
        return 2 * i;
    }

    public int right(int i) {
        return 2 * i + 1;
    }

    /**
     *  Make sure that max-heap holds
     *  i.e. a[i] > a[l] && a[i] > a[r]
     *  all the way down the tree
     *
     *  O(ln n)
     */
    public void maxHeapify(int hSize, int i) {
        int l = left(i);
        int r = right(i);
        int largest = i;
        if (l <= hSize && a[l - 1] > a[i - 1]) {
            largest = l;
        }
        if (r <= hSize && a[r - 1] > a[largest - 1]) {
            largest = r;
        }
        if (i != largest) {
            int swap = a[i - 1];
            a[i - 1] = a[largest - 1];
            a[largest - 1] = swap;

            maxHeapify(hSize, largest);
        }
    }

    /**
     *  Build max-heap over array
     * 
     *  O(n ln n)
     */
    public void buildMaxHeap() {
        for(int i = a.length / 2; i>0; i--) {
           maxHeapify(a.length, i); 
        }
    }

    /**
     *  O(n ln n)
     */
    public void heapSort() {
        //buildMaxHeap();
        for(int i = 1; i < a.length; i++) {
            int n = a.length - i;
            int swap = a[0];
            a[0] = a[n];
            a[n] = swap;
            maxHeapify(n, 1);
        }
    }

    public int[] getA(){ 
        return a;
    }

    public static void print(Heapsort h){
        System.out.println("Content:");
        h.printTree(1, 0);
    }

    public void printTree(int i, int depth) {
        if (i >= a.length){ 
            return;
        }
        int l = left(i);
        int r = right(i);
        String res = "";
        for(int k = 0; k < depth; k++) {
            res += "  ";
        }
        System.out.println(res + a[i - 1]);
        printTree(l, depth + 1);
        printTree(r, depth + 1);
    }

    public static void main(String[] args) {
        Heapsort h = new Heapsort(new int[] {1, 6, 3 , 11, 7, 2, 5});

        print(h);
        h.buildMaxHeap();
        print(h);

        int[] expMaxHeap = new int[] {11, 7, 5, 6, 1, 2, 3};
        for (int i =0 ; i < expMaxHeap.length; i++) {
            assert expMaxHeap[i] == h.getA()[i] : "Comparing " + i + " max heap position";
        } 

        h.heapSort();

        print(h);
        int[] expSort = new int[] {1, 2, 3, 5, 6, 7, 11};
        for (int i =0 ; i < expMaxHeap.length; i++) {
            assert expSort[i] == h.getA()[i] : "Comparing " + i ;
        } 

    }
}
