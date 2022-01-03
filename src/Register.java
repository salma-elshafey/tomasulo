import java.util.Random;

public class Register {
    private String name; // F0, F1, etc.
    private String Qi; // either "" or tag
    private Double value;

    public Register(String name) {
        this.name = name;
        Qi = "";
        Random r = new Random();
        double randomValue = 0 + (100 - 0) * r.nextDouble();
      //  System.out.println(randomValue);
        randomValue = randomValue*100;
        randomValue = Math.round(randomValue);
        randomValue = randomValue /100;
      //  System.out.println(randomValue);
        value = randomValue;
    }
    public String toString(){
        return "  QI:"+getQi()+"       Value:"+value+"\n";
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
