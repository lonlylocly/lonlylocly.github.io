public class Element {
    int key;
    String value;

    public Element(int key, String value) {
        this.key = key;
        this.value = value;
    } 
    
    @Override
    public String toString(){
        return key + ";" + value;
    }
}


