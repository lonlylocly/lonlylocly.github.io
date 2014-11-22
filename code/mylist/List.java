package mylist;

import java.lang.IllegalArgumentException;

public class List<E> {

    Item<E> head = null;
    int length = 0;
   
    public void append(E e) {
        if (head == null) {
            head = new Item<E>(null, e);
        } else {
            Item<E> prev = search(length -1 );
            Item<E> ptr = new Item<E>(prev, e);
            prev.setNext(ptr);
        }
        length += 1;
    }

    public void insert(int i, E e) {
        if (i == length) {
            append(e);
            return;
        }
        Item<E> n = new Item<E>(e);
        Item<E> ptr = search(i); 
        Item<E> prev = ptr.getPrev();

        assert ptr != null; 
        
        n.setNext(ptr);
        n.setPrev(prev);
        if (prev != null) {
            prev.setNext(n);
        }
        ptr.setPrev(n);

        if (i == 0) {
            head = n;
        }
        
        length += 1;
    }

    public boolean contains(E e) {
        return search(e) != null;
    }

    public E get(int i) {
        if (i < 0 || i >= length) {
            throw new IllegalArgumentException();
        }
        Item<E> e = search(i);
        return e.getValue();
    }

    private Item<E> search(E e) {
        Item<E> ptr = head;
        while(true) {
            if (ptr.getNext() == null) {
                break;
            }
            if (ptr.getValue().equals(e)) {
                break;
            }
            ptr = ptr.getNext();
        }
        return ptr;
    }

    private Item<E> search(int i) {
        if ( i<0 || i > length) {
            throw new IllegalArgumentException();
        }
        Item<E> ptr = head;
        for(int k = 0; k < i; k++ ) {
            ptr = ptr.getNext();
        }
        
        return ptr; 
    }

    public E remove(int i) {
        Item<E> ptr = search(i);
        remove(ptr);

        return ptr.getValue();
    }

    public E remove(E e) {
        Item<E> ptr = search(e);
        if (ptr != null) {
            remove(ptr);
            return ptr.getValue();
        } else {
            return null;
        }
    }

    private void remove(Item<E> e) {
        Item<E> prev = e.getPrev();
        Item<E> next = e.getNext();
        if (prev != null) {
            prev.setNext(next);
        }
        if (next != null) {
            next.setPrev(prev);
        }
        if (prev == null) {
            head = next;
        }
        length -= 1;
    }

    public int length() {
        return length;
    } 

    public static void main(String[] args) {
        List<String> l = new List<String>();

        assert l.length() == 0;

        l.append("Chandler");
        printList(l);

        assert l.length() == 1;

        assert l.get(0).equals("Chandler");

        l.append("Ross");

        assert l.length() == 2;
        printList(l);


        assert l.get(1).equals("Ross");

        l.insert(1, "Rachel");

        assert l.length() == 3;

        assert l.get(1).equals("Rachel");
        assert l.get(2).equals("Ross");

        printList(l);

        String removed = l.remove(0);

        printList(l);

        assert removed.equals("Chandler");
        assert l.length() == 2;

        try {
            l.get(-1);
            assert false : "-1 index";
        } catch (IllegalArgumentException ex) {
            // ok
        }

        try {
            l.get(l.length());
            assert false: "too big index";
        } catch (IllegalArgumentException ex) {
            // ok
        }

        l.insert(0, "Monica");
        
        assert l.length() == 3;

        assert l.get(0).equals("Monica");
        
        assert l.get(1).equals("Rachel");
        assert l.get(2).equals("Ross");
       
        printList(l);

        l.remove(2);
        l.remove(1);
        l.remove(0);


        printList(l);
    }

    public static <E> void printList(List<E> l) {
        System.out.println("List contents:");
        for(int i = 0; i<l.length(); i++) {
            System.out.println("  " + l.get(i));
        } 
    }
}

