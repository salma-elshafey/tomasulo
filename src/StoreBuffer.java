public class StoreBuffer {
    private String tag; // S1, S2, S3
    private boolean busy;
    private int address;
    private int V;
    private String Q;

    public StoreBuffer (String tag, boolean busy, int address, int V, String Q){
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

    public int getV() {
        return V;
    }

    public void setV(int v) {
        V = v;
    }

    public String getQ() {
        return Q;
    }

    public void setQ(String q) {
        Q = q;
    }
}
