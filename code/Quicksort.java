
public class Quicksort {

    int[] a;
    
    public Quicksort(int[] a) {
        this.a = a;
    }
    
    public static int[] sort(int[] a) {
        Quicksort q = new Quicksort(a);
        q.sortPart(0, a.length -1);

        return a;
    }
   
    // runs O(end - start) + 2 O(end-start/2) ... 
    // so O(N logN) on average
    // worst case: already sorted list, O(N^2)
    private void sortPart(int start, int end) {
        if (start < end) {
            int q = rearrangePart(start, end);
            sortPart(start, q - 1);
            sortPart(q, end);
        }
    }

    // runs O(N) where N = end - start
    private int rearrangePart(int start, int end) {
        int pivot = a[end];
        int k = start; // next index for less than pivot
        int n = end -1; // next for more than pivot
        while (k<=n) {
            if (a[n] <= pivot && a[k] > pivot) {
                swap(k,n);
                k ++;
                n --;
            } else if (a[n] > pivot) {
                n --;
            } else if (a[k] <= pivot) {
                k ++;
            }
        }
        swap(n + 1, end);

        return n + 1;
    }

    private void swap(int k, int n) {
        int swap = a[k];
        a[k] = a[n];
        a[n] = swap;
    }

    public static void print(int[] a) {
        String s = "";
        for(int i : a) {
            s += i + ", ";
        }
        System.out.println(s);
    }

    public static void main(String[] args) {
        //int[] a = new int[] {123, 2 , 13 , 12, 2, 4, 5, 2, 3, 6};    
        int[] a = new int[] {2, 5, 1, 4};    
        print(a);

        print(Quicksort.sort(a));
    }
}
