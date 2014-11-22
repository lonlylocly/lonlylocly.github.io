package mylist;

public class Item<E> {
    Item<E> prev;
    Item<E> next;
    E value;

    public Item (E value) {
        this.value = value;
    }
    public Item (Item<E> prev, E value) {
        this.prev = prev;
        this.value = value;
        this.next = null;
    }
    public Item (Item<E> prev, E value, Item<E> next) {
        this.prev = prev;
        this.value = value;
        this.next = next;
    }

    public Item<E> getNext() {
        return next;
    } 

    public void setNext(Item<E> next) {
        this.next = next;
    }
    
    public Item<E> getPrev() {
        return prev;
    } 

    public void setPrev(Item<E> prev) {
        this.prev = prev;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
}
