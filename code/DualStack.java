import java.lang.IllegalStateException;

public class DualStack<E> {
    final E[] bytes;
    final int n;
    int cur1;
    int cur2;
   
    /** 
     * Everything except allocation works in constant time
     * allocation depends on java; large allocations may take O(n) time
     */  
    @SuppressWarnings("unchecked")
    public DualStack(int capacity) {
        bytes = (E[]) new Object[capacity];
        cur1 = -1;
        cur2 = capacity;
        n = capacity;
    }

    /* O(1) */
    public void push1(E e) {
        if (spare() <= 0) {
            throw new IllegalStateException("Overflow");
        } 
        
        cur1 = cur1 + 1;
        bytes[cur1] = e;
    }

    /* O(1) */
    public void push2(E e){
        if (spare() <= 0) {
            throw new IllegalStateException("Overflow");
        } 
        cur2 = cur2 - 1;
        bytes[cur2] = e;
    }

    /* O(1) */
    public E pop1(){
        if (cur1 < 0) {
            throw new IllegalStateException("Underflow 1");
        }
        E e = bytes[cur1];
        bytes[cur1] = null;
        cur1 = cur1 - 1;

        return e;
    }

    /* O(1) */
    public E pop2() {
        if (cur2 >= n) {
            throw new IllegalStateException("Underflow 2");
        }
        E e = bytes[cur2];
        bytes[cur2] = null;
        cur2 = cur2 + 1;

        return e;
    }

    /* O(1) */
    public boolean hasNext1 () {
        return cur1 >=0;
    }

    /* O(1) */
    public boolean hasNext2 () {
        return cur2 < n;
    }

    /* O(1) */
    public int spare() {
        return cur2 - cur1 - 1;
    }


    public static void main(String[] args) {
        System.out.println("Messing with DualStack<String>");
        DualStack<String> d = new DualStack<String>(4);

        assert d.spare() == 4 : "spare is ok";
        assert d.hasNext1() == false;
        assert d.hasNext2() == false;

        d.push1("Chandler");

        assert d.spare() == 3;

        assert d.hasNext1() == true;
        assert d.hasNext2() == false;

        d.push2("Ross");

        assert d.spare() == 2;

        assert d.hasNext1() == true;
        assert d.hasNext2() == true;


        d.push2("Monica");
        d.push2("Rachel");

        assert d.spare() == 0;

        try {
            d.push2("Peter");
            assert false : "should have failed insert into full stack";
        } catch (IllegalStateException ex) {
            // ok
        }

        String popped = d.pop1();

        assert popped.equals("Chandler");

        assert d.spare() == 1;

        try {
            d.pop1();
            assert false : "Should've failed - nothing to pop";
        } catch (IllegalStateException ex) {
            // ok
        }

        d.pop2();
        d.pop2();
        d.pop2();

        try {
            d.pop2();
            assert false : "Should've failed - nothing to pop from 2"; 
        } catch (IllegalStateException ex) {
            // ok
        }

        assert d.spare() == 0;

        System.out.println("Done with DualStack");
    }
}
