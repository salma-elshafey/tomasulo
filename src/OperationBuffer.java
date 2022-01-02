public class OperationBuffer {
    private int remainingTime;
    private String tag;
    private boolean busy;
    private String op; // "ADD.D", "SUB.D", "MUL.D" or "DIV.D" --> should we make an enum?
    private double Vj, Vk;
    private String Qj, Qk;
    private int address; // to hold info for the memory address calculation for a load or a store
    private int instructionIndex;
    private double result;
    int issued;
    boolean resultWritten;

public  String toString(){
    return "Tag: "+tag+ "  "+" OPCODE: "+op+"   QJ:"+getQj()+"   QK:"+getQk()+"  VJ:"+getVj()+"  VK:"+getVk()+"   Busy:"+busy+"\n";
}
    public OperationBuffer(String tag) { //default values
        remainingTime = -2;
        this.tag = tag;
        busy = false;
        op = "";
        Vj = -1;
        Vk = -1;
        Qj = "";
        Qk = "";
        address = 0;
    }


    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public double getVj() {
        return Vj;
    }

    public void setVj(double vj) {
        Vj = vj;
    }

    public double getVk() {
        return Vk;
    }

    public void setVk(double vk) {
        Vk = vk;
    }

    public String getQj() {
        return Qj;
    }

    public void setQj(String qi) {
        Qj = qi;
    }

    public String getQk() {
        return Qk;
    }

    public void setQk(String qk) {
        Qk = qk;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getInstructionIndex() {
        return instructionIndex;
    }

    public void setInstructionIndex(int instructionIndex) {
        this.instructionIndex = instructionIndex;
    }


    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
