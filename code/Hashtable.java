import java.util.List;
import java.util.LinkedList;


public class Hashtable {

    int m;
    List<Element>[] slots;

    public Hashtable(int m) {
        this.m = m;
        slots = new List[m];
        for (int i =0; i < m; i++ ) {
            slots[i] = new LinkedList<Element>();
        }
    }   


    public int h(Element e) {
        return e.key % m;
    }

    public void putUnsafe(Element e) {
        int h = h(e);
        slots[h].add(e);
    }

    public void putSafe(Element e) {
        int h = h(e);
        Element eOld = search(e);
        if (eOld != null) {
            slots[h].remove(eOld);
        }
        slots[h].add(e);
    }

    public Element search(Element e) {
        int h = h(e);
        for(Element e2 : slots[h]) {
            if (e2.key == e.key) {
                return e2;
            }
        } 
        return null;
    }

    public void deleteUnsafe(Element e) {
        int h = h(e);
        slots[h].remove(e);    
    }

    public void deleteSafe(Element e) {
        int h = h(e);
        Element eOld = search(e);
        if (eOld != null ) {
            slots[h].remove(eOld); 
        }   
    }

    public void printContent() {
        for(int i=0; i<m; i++) {
            System.out.println("Slot " + i);
            for(Element e : slots[i]) {
                System.out.println(e);
            }
        }
    }
    
    public static void main(String[] args) {
        Hashtable t = new Hashtable(7);
        t.putSafe(new Element(1, "Monica"));
        t.putSafe(new Element(8, "Rachel"));
        t.putSafe(new Element(123123, "Chandler"));
        t.putSafe(new Element(54354, "Ross"));

        t.printContent();

        assert t.search( new Element(8, "Rachel")) != null : "Miss Rachel";
        assert t.search( new Element(14, "Joey")) == null : "Joey is here, bleh";

        Element rachel = t.search(new Element(8, "Rachel"));
        t.deleteUnsafe(rachel);
        
        System.out.println("Removed Rachel");
        assert t.search(new Element(8, "Rachel and Ross")) == null : "Rachel still with Ross?!";

        t.printContent();

        t.deleteSafe(new Element(123123, "And chanler has to go"));

        assert t.search(new Element(123123, "And chanler has to go")) == null : "Chandler is not gone!";
    }
}
