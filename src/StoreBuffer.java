public class StoreBuffer {
    private int remainingTime;
    private String tag; // S1, S2, S3
    private boolean busy;
    private int address;
    private double V;
    private String Q;
    private int instructionIndex;
    private double result;

    public StoreBuffer (String tag, boolean busy, int address, int V, String Q){
        remainingTime = -2;
        this.tag = tag;
        this.busy = busy;
        this.address = address;
        this.V = V;
        this.Q = Q;
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

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public double getV() {
        return V;
    }

    public void setV(double v) {
        V = v;
    }

    public String getQ() {
        return Q;
    }

    public void setQ(String q) {
        Q = q;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
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
