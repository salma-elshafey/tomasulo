public class LoadBuffer {
    private int remainingTime;
    private String tag; // L1, L2, L3
    private boolean busy;
    private int address;
    private int instructionIndex;
    private double result;
    int issued;
    int finished;
    int lastClockBeforeRemovingRow=-1;

    public  String toString(){
        return "Tag: "+tag+ "  "+"   Address:"+getAddress()+"   Busy:"+busy+"\n" ;
    }
    public void goDefault(){
         issued=0;
         finished=0;
         lastClockBeforeRemovingRow=-1;
    }
    public LoadBuffer (String tag, boolean busy, int address){
        remainingTime = -2;
        this.tag = tag;
        this.busy = busy;
        this.address = -1;
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
