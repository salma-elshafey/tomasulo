public class Register {
    private String name; // F0, F1, etc.
    private String Qi; // either "0" or tag
    private int value;

    public Register(String name) {
        this.name = name;
        Qi = "0";
        value = 0;
    }
    public Register (String name, String Qi, int value){
        this.name = name;
        this.Qi = Qi;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQi() {
        return Qi;
    }

    public void setQi(String qi) {
        Qi = qi;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
