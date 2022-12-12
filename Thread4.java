import java.util.Arrays;

public class Thread4 extends Thread{


    public void run(){
//        int H = Data.N/4; ---------- непотрібно, бо є в Data. може саме через це було неправильно ----------
        System.out.println("T4 started");

        // wait for input
        try {
            Data.inputOutputMonitor.waitForInputSignal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // minimal in vector B scalar q
        int q4 = Data.resourcesMonitor.minB(Data.H*3,Data.H*4);
        Data.resourcesMonitor.compareScalarQ(q4);

        // signal about calculation of finding min of B
        Data.synchronizationMonitor.signalCalculatedScalarQ();

        // wait for calculation of finding min of B
        Data.synchronizationMonitor.waitForCalculatedScalarQ();

        // copy of q
        q4 = Data.resourcesMonitor.copyScalarQ();

        System.out.println(q4 + " q4");

        // copy of p
        int p4 = Data.resourcesMonitor.copyScalarP();

        System.out.println(p4 + " p4");

        // set M
        int []partVectorM = Data.multiplyVectorBySubMatrix(Data.resourcesMonitor.A,Data.resourcesMonitor.MB,Data.H*3,Data.H*4);
        Data.writeVectorResult(partVectorM,0,Data.resourcesMonitor.M,Data.H*3,Data.H);
        Data.synchronizationMonitor.signalForCalculatedVectorM();
        Data.synchronizationMonitor.waitForCalculatedVectorM();

        // set MT
        int[][] partMatrixMT = Data.multiplyMatrixAndSubMatrix(Data.resourcesMonitor.MZ, Data.resourcesMonitor.MR,Data.H*3,Data.H*4);
        Data.writeRealMatrix(Data.resourcesMonitor.getMT(),partMatrixMT,3);

        System.out.println(Arrays.deepToString(partMatrixMT) + " - 4 part MT");
        System.out.println(Arrays.deepToString(Data.resourcesMonitor.getMT()) + " My MT by method 4");

        // set L
        int [] partVectorL = Data.multiplyConstantBySubVector(p4,Data.resourcesMonitor.getM(),Data.H*3,Data.H*4);
        Data.writeVectorResult(partVectorL,0,Data.resourcesMonitor.L,Data.H*3,Data.H);
        System.out.println(Arrays.toString(partVectorL) + " L in T4");
        Data.synchronizationMonitor.signalForCalculatedVectorL();
        Data.synchronizationMonitor.waitForCalculatedVectorL();

        // set N
        int [] partVectorN = Data.multiplyVectorBySubMatrix(Data.resourcesMonitor.B, Data.resourcesMonitor.getMT(), Data.H*3, Data.H*4);
        Data.writeVectorResult(partVectorN,0,Data.resourcesMonitor.N,Data.H*3,Data.H);
        Data.synchronizationMonitor.signalForCalculatedVectorN();
        Data.synchronizationMonitor.waitForCalculatedVectorN();

        // set scalar c
        int c = Data.multiplyVectorAndSubVector(Data.resourcesMonitor.getL(),Data.resourcesMonitor.getVectorN(),Data.H*3,Data.H*4);
        int c4 = Data.resourcesMonitor.copyScalarC() + c;
        Data.resourcesMonitor.setScalarC(c4);
        System.out.println(c4 + " c4");
        Data.synchronizationMonitor.signalCalculatedScalarC();
        Data.synchronizationMonitor.waitForCalculatedScalarC();
    }
}
