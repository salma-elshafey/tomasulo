import java.util.HashMap;

public class Architecture {
    InstructionQueue instructionQueue = new InstructionQueue();
    OperationBuffer[] addSubBuffers = new OperationBuffer[3];
    OperationBuffer[] mulDivBuffers = new OperationBuffer[2];
    LoadBuffer[] loadBuffers = new LoadBuffer[3];
    StoreBuffer[] storeBuffers = new StoreBuffer[3];
    Register[] registerFile = new Register[6];
    Bus commonDataBus = new Bus();
    HashMap<Integer, Integer> memory = new HashMap<Integer, Integer>(); // <address, value>;


    public void setup () {
        // load buffers
        LoadBuffer L1 = new LoadBuffer("L1", false, 0);
        LoadBuffer L2 = new LoadBuffer("L2", false, 0);
        LoadBuffer L3 = new LoadBuffer("L3", false, 0);
        loadBuffers[0] = L1;
        loadBuffers[1] = L2;
        loadBuffers[2] = L3;

        // store buffers
        StoreBuffer S1 = new StoreBuffer("S1", false, 0, "", "");
        StoreBuffer S2 = new StoreBuffer("S2", false, 0, "", "");
        StoreBuffer S3 = new StoreBuffer("S3", false, 0, "", "");
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
        registerFile[0] = F0;
        registerFile[1] = F1;
        registerFile[2] = F2;
        registerFile[3] = F3;
        registerFile[4] = F4;
        registerFile[5] = F5;
    }
}
