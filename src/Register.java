public class Register {
    private String name; // F0, F1, etc.
    private String Qi; // either "" or tag
    private Double value;

    public Register(String name) {
        this.name = name;
        Qi = "";
        value = 0.0;
    }
    public Register (String name, String Qi, Double value){
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
