public class OperationBuffer {
    private int remainingTime;
    private String tag;
    private boolean busy;
    private String op; // "ADD", "SUB", "MUL" or "DIV" --> should we make an enum?
    private int Vj, Vk;
    private String Qi, Qk;
    private int address; // to hold info for the memory address calculation for a load or a store


    public OperationBuffer(String tag) { //default values
        remainingTime = 0;
        this.tag = tag;
        busy = false;
        op = "";
        Vj = 0;
        Vk = 0;
        Qi = "";
        Qk = "";
        address = 0;
    }

    public OperationBuffer(int remainingTime, String tag, boolean busy, String op, int Vj,
                           int Vk, String Qi, String Qk, int address) {
        this.remainingTime = remainingTime;
        this.tag = tag;
        this.busy = busy;
        this.op = op;
        this.Vj = Vj;
        this.Vk = Vk;
        this.Qi = Qi;
        this.Qk = Qk;
        this.address = address;
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

    public int getVj() {
        return Vj;
    }

    public void setVj(int vj) {
        Vj = vj;
    }

    public int getVk() {
        return Vk;
    }

    public void setVk(int vk) {
        Vk = vk;
    }

    public String getQi() {
        return Qi;
    }

    public void setQi(String qi) {
        Qi = qi;
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
}
