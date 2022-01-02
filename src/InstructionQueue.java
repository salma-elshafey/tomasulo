import java.util.LinkedList;
import java.util.Queue;

public class InstructionQueue {
    MemoryInstruction mem;
    Instruction instruction;
    int issue = -1;
    int startExecution = -1;
    int finishExecution = -1;
    int writeBack = -1;

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }



    public InstructionQueue(MemoryInstruction mem) {
        this.mem = mem;
        instruction = null;
    }

    public InstructionQueue(Instruction instruction) {
        this.instruction = instruction;
        mem = null;
    }

    public String toString(){
        if (instruction == null) //mem instruction
            return mem.getOperation() + " " +  mem.getRegister() + " " + mem.getAddress()+"  "
                    +"issue: "+issue +"  startExecution: "+startExecution+"  finishExecution: "+finishExecution+"  writeBack: "+writeBack+"\n";
        else
            return instruction.operation + " " + instruction.destination + " " + instruction.source1 + " " + instruction.source2 +"  "
                    +"issue: "+issue +"  startExecution: "+startExecution+"  finishExecution: "+finishExecution+"  writeBack: "+writeBack+"\n";

    }
}
