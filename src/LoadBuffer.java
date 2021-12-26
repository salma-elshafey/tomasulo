public class LoadBuffer {
    private String tag; // L1, L2, L3
    private boolean busy;
    private int address;

    public LoadBuffer (String tag, boolean busy, int address){
        this.tag = tag;
        this.busy = busy;
        this.address = address;
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
}
