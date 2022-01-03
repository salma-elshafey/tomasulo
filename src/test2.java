import java.lang.reflect.Array;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class test2 {
    ArrayList<InstructionQueue> instructionQueue = new ArrayList<InstructionQueue>();
    OperationBuffer[] addSubBuffers = new OperationBuffer[3];
    OperationBuffer[] mulDivBuffers = new OperationBuffer[2];
    LoadBuffer[] loadBuffers = new LoadBuffer[3];
    StoreBuffer[] storeBuffers = new StoreBuffer[3];
    HashMap<String, Register> registerFile = new HashMap<String, Register>(); // {("F0" -> 12), ("F1", 2)}
    // Register[] registerFile = new Register[12];
    // Bus commonDataBus = new Bus();
    HashMap<Integer, Double> memory = new HashMap<Integer, Double>(); // <address, value>
    // Queue<String> waitingToWriteBack = new LinkedList<String>();
    // Map<Integer, String> bus = new LinkedHashMap<>(16, .75f, true);

    // LinkedList<HashMap<String,Double>> bus=new
    // LinkedList<HashMap<String,Double>>();
    LinkedList<HashMap<String, Double>> bus = new LinkedList<HashMap<String, Double>>();
    LinkedList<HashMap<String, Integer>> busIndex = new LinkedList<HashMap<String, Integer>>();

    int numberOfInstructure = 0;
    int clockCycle = 1;
    int instructionToBeIssued = 0;
    int ldLatency = 0;
    int sdLatency = 0;
    int mulLatency = 0;
    int divLatency = 0;
    int addLatency = 0;
    int subLatency = 0;
    int sumLatencies = 0;

    public void setup() {
        // load buffers
        LoadBuffer L1 = new LoadBuffer("L1", false, 0);
        LoadBuffer L2 = new LoadBuffer("L2", false, 0);
        LoadBuffer L3 = new LoadBuffer("L3", false, 0);
        loadBuffers[0] = L1;
        loadBuffers[1] = L2;
        loadBuffers[2] = L3;

        // store buffers
        StoreBuffer S1 = new StoreBuffer("S1", false, 0, 0, "");
        StoreBuffer S2 = new StoreBuffer("S2", false, 0, 0, "");
        StoreBuffer S3 = new StoreBuffer("S3", false, 0, 0, "");
        storeBuffers[0] = S1;
        storeBuffers[1] = S2;
        storeBuffers[2] = S3;

        // add and sub buffers
        OperationBuffer A1 = new OperationBuffer("A1");
        OperationBuffer A2 = new OperationBuffer("A2");
        OperationBuffer A3 = new OperationBuffer("A3");
        addSubBuffers[0] = A1;
        addSubBuffers[1] = A2;
        addSubBuffers[2] = A3;

        // mul and div buffers
        OperationBuffer M1 = new OperationBuffer("M1");
        OperationBuffer M2 = new OperationBuffer("M2");
        mulDivBuffers[0] = M1;
        mulDivBuffers[1] = M2;

        // register file
        Register F0 = new Register("F0");
        Register F1 = new Register("F1");
        Register F2 = new Register("F2");
        Register F3 = new Register("F3");
        Register F4 = new Register("F4");
        Register F5 = new Register("F5");
        Register F6 = new Register("F6");
        Register F7 = new Register("F7");
        Register F8 = new Register("F8");
        Register F9 = new Register("F9");
        Register F10 = new Register("F10");
        Register F11 = new Register("F11");
        registerFile.put("F0", F0);
        registerFile.put("F1", F1);
        registerFile.put("F2", F2);
        registerFile.put("F3", F3);
        registerFile.put("F4", F4);
        registerFile.put("F5", F5);
        registerFile.put("F6", F6);
        registerFile.put("F7", F7);
        registerFile.put("F8", F8);
        registerFile.put("F9", F9);
        registerFile.put("F10", F10);
        registerFile.put("F11", F11);

        // memory
        for (int i = 0; i < 100; i++) {
            double value = Math.floor(Math.random() * (250 - 0 + 1) + 0);
            memory.put(i, value);
        }
    }

    public void parseLatency() {
        try {
            File myObj = new File("src/latency.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] instruction = data.split(" ");
                if (instruction[0].equals("L.D")) {
                    ldLatency = Integer.parseInt(instruction[1]);
                }
                if (instruction[0].equals("S.D")) {
                    sdLatency = Integer.parseInt(instruction[1]);
                }
                if (instruction[0].equals("MUL.D")) {
                    mulLatency = Integer.parseInt(instruction[1]);
                }
                if (instruction[0].equals("DIV.D")) {
                    divLatency = Integer.parseInt(instruction[1]);
                }
                if (instruction[0].equals("ADD.D")) {
                    addLatency = Integer.parseInt(instruction[1]);
                }
                if (instruction[0].equals("SUB.D")) {
                    subLatency = Integer.parseInt(instruction[1]);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        // sumLatencies=sdLatency+mulLatency+addLatency+divLatency+ldLatency+subLatency;
    }

    public void parse() {
        try {
            File myObj = new File("src/program.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                numberOfInstructure++;
                String data = myReader.nextLine();
                String[] instruction = data.split(" ");
                if (instruction[0].equals("L.D") || instruction[0].equals("S.D")) {
                    String operation = instruction[0];
                    String register = instruction[1];
                    int address = Integer.parseInt(instruction[2]);
                    MemoryInstruction m = new MemoryInstruction(operation, register, address);
                    InstructionQueue n = new InstructionQueue(m);
                    instructionQueue.add(n);
                    if (instruction[0].equals("L.D")) {
                        sumLatencies += ldLatency;
                    } else {
                        sumLatencies += sdLatency;
                    }
                } else {
                    String operation = instruction[0];
                    String destination = instruction[1];
                    String source1 = instruction[2];
                    String source2 = instruction[3];
                    Instruction i = new Instruction(operation, source1, source2, destination);
                    InstructionQueue n = new InstructionQueue(i);
                    instructionQueue.add(n);
                    switch (operation) {
                        case "MUL.D": {
                            sumLatencies += mulLatency;
                            break;
                        }
                        case "ADD.D": {
                            sumLatencies += addLatency;
                            break;
                        }
                        case "SUB.D": {
                            sumLatencies += subLatency;
                            break;
                        }
                        case "DIV.D": {
                            sumLatencies += divLatency;
                            break;
                        }
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void start() {
        for (int i = 0; i <= sumLatencies + 2; i++) {
            if (clockCycle != 1) {
                if (instructionToBeIssued < instructionQueue.size()) {
                    issue();
                }
                execute();
                writeResult();
            } else {
                if (instructionToBeIssued < instructionQueue.size()) {
                    issue();
                }
            }

            // writeResult();
            System.out.println("Clock Cycle:" + clockCycle);
            System.out.println("Instructure queue \n" + instructionQueue.toString());
            System.out.println("Add/Sub buffer \n" + Arrays.deepToString(addSubBuffers));
            System.out.println("Mul/Div buffer\n" + Arrays.deepToString(mulDivBuffers));
            System.out.println("Load Buffer\n" + Arrays.deepToString(loadBuffers));
            System.out.println("Store Buffer\n" + Arrays.deepToString(storeBuffers));
            System.out.println("Register File\n" + registerFile.toString());
            System.out.println();
            clockCycle++;
            // ArrayList<InstructionQueue> instructionQueue = new
            // ArrayList<InstructionQueue>();
            boolean enough = true;
            for (InstructionQueue row : instructionQueue) {
                if (row.writeBack == -1) {
                    enough = false;
                }
            }
            if (enough) {
                break;
            }
        }
    }

    public void issue()

    {

        if (instructionQueue.get(instructionToBeIssued).mem == null) { // it's an ALU instruction
            String operation = instructionQueue.get(instructionToBeIssued).instruction.operation;
            if (operation.equals("MUL.D") || operation.equals("DIV.D")) {
                String source1 = instructionQueue.get(instructionToBeIssued).instruction.source1;
                String source2 = instructionQueue.get(instructionToBeIssued).instruction.source2;
                String destination = instructionQueue.get(instructionToBeIssued).instruction.destination;
                for (int i = 0; i < 2; i++) {
                    if (!mulDivBuffers[i].isBusy() && mulDivBuffers[i].lastClockBeforeRemovingRow < clockCycle - 1) {
                        mulDivBuffers[i].setBusy(true);
                        mulDivBuffers[i].setOp(operation);
                        if (registerFile.get(source1).getQi().equals("")) {
                            mulDivBuffers[i].setVj(registerFile.get(source1).getValue());
                        } else {
                            mulDivBuffers[i].setQj(registerFile.get(source1).getQi());
                        }
                        if (registerFile.get(source2).getQi().equals("")) {
                            mulDivBuffers[i].setVk(registerFile.get(source2).getValue());
                        } else {
                            mulDivBuffers[i].setQk(registerFile.get(source2).getQi());
                        }
                        registerFile.get(destination).setQi(mulDivBuffers[i].getTag());
                        instructionQueue.get(instructionToBeIssued).setIssue(clockCycle);
                        mulDivBuffers[i].setInstructionIndex(instructionToBeIssued); // important
                        mulDivBuffers[i].issued = clockCycle;
                        instructionToBeIssued++;
                        if (operation.equals("MUL.D")) {
                            mulDivBuffers[i].setRemainingTime(mulLatency);
                        } else {
                            mulDivBuffers[i].setRemainingTime(divLatency);
                        }
                        break;
                    }
                }
            }
            if (operation.equals("ADD.D") || operation.equals("SUB.D")) {
                String source1 = instructionQueue.get(instructionToBeIssued).instruction.source1;
                String source2 = instructionQueue.get(instructionToBeIssued).instruction.source2;
                String destination = instructionQueue.get(instructionToBeIssued).instruction.destination;
                for (int i = 0; i < 3; i++) {
                    if (!addSubBuffers[i].isBusy() && addSubBuffers[i].lastClockBeforeRemovingRow < clockCycle - 1) {
                        System.out.println(addSubBuffers[i].lastClockBeforeRemovingRow
                                + "instructionQueue.get(addSubBuffers[i].getInstructionIndex()).writeBack");
                        System.out.println(clockCycle + "clockCycle");
                        System.out.println(addSubBuffers[i].getTag() + "tag");

                        addSubBuffers[i].setBusy(true);
                        addSubBuffers[i].setOp(operation);
                        if (registerFile.get(source1).getQi().equals("")) {
                            addSubBuffers[i].setVj(registerFile.get(source1).getValue());
                        } else {
                            addSubBuffers[i].setQj(registerFile.get(source1).getQi());
                        }
                        if (registerFile.get(source2).getQi().equals("")) {
                            addSubBuffers[i].setVk(registerFile.get(source2).getValue());
                        } else {
                            addSubBuffers[i].setQk(registerFile.get(source2).getQi());
                        }
                        registerFile.get(destination).setQi(addSubBuffers[i].getTag());
                        instructionQueue.get(instructionToBeIssued).setIssue(clockCycle);
                        addSubBuffers[i].setInstructionIndex(instructionToBeIssued);
                        addSubBuffers[i].issued = clockCycle;
                        instructionToBeIssued++;
                        if (operation.equals("ADD.D")) {
                            addSubBuffers[i].setRemainingTime(addLatency);
                        } else {
                            addSubBuffers[i].setRemainingTime(subLatency);
                        }
                        break;
                    }
                }
            }
        } else {
            String operation = instructionQueue.get(instructionToBeIssued).mem.getOperation();
            if (operation.equals("L.D")) {
                String register = instructionQueue.get(instructionToBeIssued).mem.getRegister();
                int address = instructionQueue.get(instructionToBeIssued).mem.getAddress();
                for (int i = 0; i < 3; i++) {
                    if (!loadBuffers[i].isBusy() && loadBuffers[i].lastClockBeforeRemovingRow < clockCycle) {
                        // if load buffers aren't busy, we'll set busy to true, then set the address to
                        // the address in the instruction
                        // and set the Qi of the destination register in the register file to the tag of
                        // the load buffer
                        loadBuffers[i].setBusy(true);
                        loadBuffers[i].setAddress(address);
                        registerFile.get(register).setQi(loadBuffers[i].getTag());
                        instructionQueue.get(instructionToBeIssued).setIssue(clockCycle);
                        loadBuffers[i].setInstructionIndex(instructionToBeIssued);
                        loadBuffers[i].issued = clockCycle;
                        instructionToBeIssued++;
                        loadBuffers[i].setRemainingTime(ldLatency);
                        break;
                    }
                }
            }
            if (operation.equals("S.D")) {
                String register = instructionQueue.get(instructionToBeIssued).mem.getRegister();
                int address = instructionQueue.get(instructionToBeIssued).mem.getAddress();
                for (int i = 0; i < 3; i++) {
                    if (!storeBuffers[i].isBusy() && storeBuffers[i].lastClockBeforeRemovingRow < clockCycle) {
                        storeBuffers[i].setBusy(true);
                        storeBuffers[i].setAddress(address);
                        // registerFile.get(register).setQi(storeBuffers[i].getTag());
                        if (registerFile.get(register).getQi().equals("")) {
                            storeBuffers[i].setV(registerFile.get(register).getValue());
                        } else {
                            storeBuffers[i].setQ(registerFile.get(register).getQi());
                        }
                        instructionQueue.get(instructionToBeIssued).setIssue(clockCycle);
                        storeBuffers[i].setInstructionIndex(instructionToBeIssued);
                        storeBuffers[i].issued = clockCycle;
                        instructionToBeIssued++;
                        storeBuffers[i].setRemainingTime(sdLatency);
                        break;
                    }
                }
            }
        }
    }

    public void execute() {
        // loop on mul/div
        for (int i = 0; i < 2; i++) {
            if (mulDivBuffers[i].isBusy()) {
                if (mulDivBuffers[i].getQj().equals("") && mulDivBuffers[i].getQk().equals("")
                        && mulDivBuffers[i].issued < clockCycle) {
                    // to check that all values are ready
                    if (mulDivBuffers[i].getOp().equals("MUL.D") && mulDivBuffers[i].getRemainingTime() == mulLatency) {
                        // System.out.println("mulDivBuffers[i].getRemainingTime() == mulLatency");
                        // System.out.println(mulDivBuffers[i].getRemainingTime() == mulLatency);
                        instructionQueue.get(mulDivBuffers[i].getInstructionIndex()).startExecution = clockCycle;
                        mulDivBuffers[i].setResult(mulDivBuffers[i].getVj() * mulDivBuffers[i].getVk());
                    } else {
                        if (mulDivBuffers[i].getOp().equals("DIV.D")
                                && mulDivBuffers[i].getRemainingTime() == divLatency) {
                            instructionQueue.get(mulDivBuffers[i].getInstructionIndex()).startExecution = clockCycle;
                            mulDivBuffers[i].setResult(mulDivBuffers[i].getVj() / mulDivBuffers[i].getVk());
                        }
                    }
                    if (mulDivBuffers[i].getRemainingTime() == 1) {
                        instructionQueue.get(mulDivBuffers[i].getInstructionIndex()).finishExecution = clockCycle;
                        mulDivBuffers[i].finished = clockCycle;
                    }
                    // else {
                    mulDivBuffers[i].setRemainingTime(mulDivBuffers[i].getRemainingTime() - 1);
                    // }

                }
            }
        }

        // loop on add/sub
        for (int i = 0; i < 3; i++) {
            if (addSubBuffers[i].isBusy()) {
                if (addSubBuffers[i].getQj().equals("") && addSubBuffers[i].getQk().equals("")
                        && addSubBuffers[i].issued < clockCycle) {
                    // to check that all values are ready
                    // System.out.println("addSubBuffers[i].getRemainingTime() == addLatency");
                    // System.out.println(addSubBuffers[i].getRemainingTime() == addLatency);
                    // System.out.println(instructionToBeIssued+"instructionToBeIssued");
                    if (addSubBuffers[i].getOp().equals("ADD.D") && addSubBuffers[i].getRemainingTime() == addLatency) {
                        // System.out.println("entered");
                        instructionQueue.get(addSubBuffers[i].getInstructionIndex()).startExecution = clockCycle;
                        addSubBuffers[i].setResult(addSubBuffers[i].getVj() + addSubBuffers[i].getVk());
                    } else {
                        if (addSubBuffers[i].getOp().equals("SUB.D")
                                && addSubBuffers[i].getRemainingTime() == subLatency) {
                            // System.out.println("entered ثمسث");
                            instructionQueue.get(addSubBuffers[i].getInstructionIndex()).startExecution = clockCycle;
                            addSubBuffers[i].setResult(addSubBuffers[i].getVj() - addSubBuffers[i].getVk());
                        }
                    }
                    if (addSubBuffers[i].getRemainingTime() == 1) {
                        instructionQueue.get(addSubBuffers[i].getInstructionIndex()).finishExecution = clockCycle;
                        addSubBuffers[i].finished = clockCycle;
                    }
                    // else {
                    addSubBuffers[i].setRemainingTime(addSubBuffers[i].getRemainingTime() - 1);
                    // }
                }
            }
        }

        // load
        for (int i = 0; i < 3; i++) {
            if (loadBuffers[i].isBusy() && loadBuffers[i].issued < clockCycle) {
                // to check that all values are ready
                if (loadBuffers[i].getRemainingTime() == ldLatency) {
                    instructionQueue.get(loadBuffers[i].getInstructionIndex()).startExecution = clockCycle;
                    // access the memory and to load value from it
                    loadBuffers[i].setResult(memory.get(loadBuffers[i].getAddress()));
                }
                if (loadBuffers[i].getRemainingTime() == 1) {
                    instructionQueue.get(loadBuffers[i].getInstructionIndex()).finishExecution = clockCycle;
                    loadBuffers[i].finished = clockCycle;
                }
                // else {
                loadBuffers[i].setRemainingTime(loadBuffers[i].getRemainingTime() - 1);
                // }
            }

        }

        // store
        for (int i = 0; i < 3; i++) {
            if (storeBuffers[i].isBusy() && storeBuffers[i].issued < clockCycle) {
                // to check that all values are ready
                if (storeBuffers[i].getQ().equals("") && storeBuffers[i].getRemainingTime() == sdLatency) {
                    instructionQueue.get(storeBuffers[i].getInstructionIndex()).startExecution = clockCycle;
                    // access the memory and to store value in it
                    memory.put(storeBuffers[i].getAddress(), storeBuffers[i].getV());
                }
                if (storeBuffers[i].getRemainingTime() == 1) {
                    instructionQueue.get(storeBuffers[i].getInstructionIndex()).finishExecution = clockCycle;
                    storeBuffers[i].finished = clockCycle;
                }
                // else {
                storeBuffers[i].setRemainingTime(storeBuffers[i].getRemainingTime() - 1);
                // }
            }

        }

    }

    public void writeResultHelper() {
        // String tag= bus
        if (!bus.isEmpty()) {
            // for (Map.Entry<String,Double> entry : bus.entrySet()){
            // System.out.println("Key = " + entry.getKey() + ", Value = " +
            // entry.getValue());
            HashMap<String, Double> result = bus.removeFirst();
            String key = (String) result.keySet().toArray()[0];
            Double value = result.get(key);

            HashMap<String, Integer> resultIndex = busIndex.removeFirst();
            String keyIndex = (String) result.keySet().toArray()[0];
            int valueIndex = resultIndex.get(key);

            // HashMap<String,Integer> myResultIndex=new HashMap<String,Integer>();
            // myResultIndex.put(loadBuffers[i].getTag(),loadBuffers[i].getInstructionIndex());
            // busIndex.add(myResultIndex);

            instructionQueue.get(valueIndex).writeBack = clockCycle;

            // mulDivBuffers[i].setBusy(false);
            // mulDivBuffers[i].lastClockBeforeRemovingRow=clockCycle;

            // loop over reg file, add/sub buffer, mul/div buffer, store buffer
            // mul/div buffer
            for (int i = 0; i < 2; i++) {
                if (mulDivBuffers[i].isBusy()) {
                    if (mulDivBuffers[i].getQj().equals(key)) {
                        mulDivBuffers[i].setVj(value);
                        mulDivBuffers[i].setQj("");
                    }
                    if (mulDivBuffers[i].getQk().equals(key)) {
                        mulDivBuffers[i].setVk(value);
                        mulDivBuffers[i].setQk("");
                    }
                }
            }
            // add/sub buffer
            for (int i = 0; i < 3; i++) {
                if (addSubBuffers[i].isBusy()) {
                    if (addSubBuffers[i].getQj().equals(key)) {
                        addSubBuffers[i].setVj(value);
                        addSubBuffers[i].setQj("");
                    }
                    if (addSubBuffers[i].getQk().equals(key)) {
                        addSubBuffers[i].setVk(value);
                        addSubBuffers[i].setQk("");
                    }
                }
            }
            // store buffer
            for (int i = 0; i < 3; i++) {
                if (storeBuffers[i].isBusy()) {
                    if (storeBuffers[i].getQ().equals(key)) {
                        storeBuffers[i].setV(value);
                        storeBuffers[i].setQ("");
                    }

                }
            }
            // reg file
            // for(Register qValue : registerFile.values()) {
            // if(qValue.getQi().equals(key)){
            // qValue.setQi("");
            // qValue.setValue(value);
            // }
            // }
            for (Register register : registerFile.values()) {
                // System.out.println("entered");
                if (register.getQi().equals(key)) {
                    // System.out.println("entere1");
                    register.setQi("");
                    register.setValue(value);
                }
            }
        }

        // }
    }

    public void writeResult() {
        for (int i = 0; i < 2; i++) {
            if (mulDivBuffers[i].isBusy()) {
                if (mulDivBuffers[i].getQj().equals("") && mulDivBuffers[i].getQk().equals("")) {
                    // to check that all values are ready
                    // System.out.println(mulDivBuffers[i].finished<clockCycle);
                    // System.out.println( mulDivBuffers[i].getRemainingTime() );
                    if (mulDivBuffers[i].getRemainingTime() == -1 && mulDivBuffers[i].finished < clockCycle) {
                        // System.out.println("mulDivBuffers[i].finished<clockCycle");
                        // instructionQueue.get(mulDivBuffers[i].getInstructionIndex()).writeBack =
                        // clockCycle;

                        mulDivBuffers[i].setBusy(false);
                        mulDivBuffers[i].lastClockBeforeRemovingRow = clockCycle;

                        // waitingToWriteBack.add(mulDivBuffers[i].getTag());
                        HashMap<String, Double> myResultTga = new HashMap<String, Double>();
                        myResultTga.put(mulDivBuffers[i].getTag(), mulDivBuffers[i].getResult());
                        bus.add(myResultTga);
                        HashMap<String, Integer> myResultIndex = new HashMap<String, Integer>();
                        myResultIndex.put(mulDivBuffers[i].getTag(), mulDivBuffers[i].getInstructionIndex());
                        busIndex.add(myResultIndex);

                    }
                }
            }
        }

        // loop on add/sub
        for (int i = 0; i < 3; i++) {
            if (addSubBuffers[i].isBusy()) {
                if (addSubBuffers[i].getQj().equals("") && addSubBuffers[i].getQk().equals("")) {
                    // to check that all values are ready
                    if (addSubBuffers[i].getRemainingTime() == -1 && addSubBuffers[i].finished < clockCycle) {
                        // instructionQueue.get(addSubBuffers[i].getInstructionIndex()).writeBack =
                        // clockCycle;

                        addSubBuffers[i].setBusy(false);
                        addSubBuffers[i].lastClockBeforeRemovingRow = clockCycle;

                        HashMap<String, Double> myResultTga = new HashMap<String, Double>();
                        myResultTga.put(addSubBuffers[i].getTag(), addSubBuffers[i].getResult());
                        bus.add(myResultTga);
                        HashMap<String, Integer> myResultIndex = new HashMap<String, Integer>();
                        myResultIndex.put(addSubBuffers[i].getTag(), addSubBuffers[i].getInstructionIndex());
                        busIndex.add(myResultIndex);

                    }

                }
            }
        }

        // load
        for (int i = 0; i < 3; i++) {
            if (loadBuffers[i].isBusy()) {
                // to check that all values are ready
                if (loadBuffers[i].getRemainingTime() == -1 && loadBuffers[i].finished < clockCycle) {
                    // instructionQueue.get(loadBuffers[i].getInstructionIndex()).writeBack =
                    // clockCycle;
                    // access the memory and to load value from it

                    loadBuffers[i].setBusy(false);
                    loadBuffers[i].lastClockBeforeRemovingRow = clockCycle;

                    HashMap<String, Double> myResultTga = new HashMap<String, Double>();
                    myResultTga.put(loadBuffers[i].getTag(), loadBuffers[i].getResult());
                    bus.add(myResultTga);
                    // bus.put(loadBuffers[i].getTag(),loadBuffers[i].getResult());
                    HashMap<String, Integer> myResultIndex = new HashMap<String, Integer>();
                    myResultIndex.put(loadBuffers[i].getTag(), loadBuffers[i].getInstructionIndex());
                    busIndex.add(myResultIndex);

                }

            }
        }
        // store does put anything on bus
        // for(int i=0 ;i<3; i++){
        // if (storeBuffers[i].isBusy()) {
        // //to check that all values are ready
        // if (storeBuffers[i].getRemainingTime() == -1 &&
        // storeBuffers[i].getQ().equals("") ) {
        // instructionQueue.get(storeBuffers[i].getInstructionIndex()).writeBack =
        // clockCycle;
        // //access the memory and to load value from it
        // storeBuffers[i].setBusy(false);
        // bus.put(storeBuffers[i].getTag(),storeBuffers[i].getResult());
        // }
        //
        // }
        // }

        writeResultHelper();
    }

    public static void main(String[] args) {
        Architecture a = new Architecture();
        a.setup();
        a.parseLatency();
        a.parse();
        // System.out.println(a.numberOfInstructure);
        // System.out.println(a.instructionQueue.size());
        //
        //
        // System.out.println(a.sumLatencies);
        a.start();

        // Random rd = new Random(); // creating Random object
        // System.out.println(rd.nextDouble()*10);
        // Random r = new Random();
        // double randomValue = 0 + (100 - 0) * r.nextDouble();
        // System.out.println(randomValue);
        // randomValue = randomValue*100;
        // randomValue = Math.round(randomValue);
        // randomValue = randomValue /100;
        // System.out.println(randomValue);

        // for(Object o : a.instructionQueue) {
        // System.out.println(o.toString());
        // }
        // System.out.println(a.memory);
        // LinkedList<HashMap<String,Double>> bus=new
        // LinkedList<HashMap<String,Double>>();
        // HashMap<String,Double> insert=new HashMap<>();
        // insert.put("nouran",0.7);
        // bus.add(insert);
        // HashMap<String,Double> result=bus.removeFirst();
        // String key = (String) result.keySet().toArray()[0];
        // Double value=result.get(key);
        // System.out.println("key: "+key+"\n"+"value: "+value);
        // System.out.println(bus);
    }
}
