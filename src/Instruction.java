public class Instruction {
    String operation;
    String source1;
    String source2;
    String destination;


    public Instruction(String operation, String source1, String source2, String destination) {
        this.operation = operation;
        this.source1 = source1;
        this.source2 = source2;
        this.destination = destination;
    }

    public String toString(){
        return operation + " " +  destination + " " + source1 + " " + source2;
    }
}
