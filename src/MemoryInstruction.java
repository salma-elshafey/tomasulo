public class MemoryInstruction{
    private String operation; // "L.D" or "S.D"
    private String register; // source or destination
    private int address;

    public MemoryInstruction(String operation, String register, int address){
        this.operation = operation;
        this.register = register;
        this.address = address;
    }

    public String toString(){
        return operation + " " +  register + " " + address;
    }
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }
}
