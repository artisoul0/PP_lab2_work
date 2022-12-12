import java.util.Arrays;

public class Thread2 extends Thread{


    public void run(){
        System.out.println("T2 started");

//        int H = Data.N/4; ---------- непотрібно, бо є в Data. може саме через це було неправильно ----------

        // input
        int [] A = new int[Data.N];
        int [][] MR = new int[Data.N][Data.N];
        Write.fillVectorByOne(A);
        Write.fillMatrixByOne(MR);
        Data.resourcesMonitor.setA(A);
        Data.resourcesMonitor.setMR(MR);

        // signal about input
        Data.inputOutputMonitor.inputSignal();

        // wait for input
        try {
            Data.inputOutputMonitor.waitForInputSignal();
        } catch (InterruptedException ev) {
            throw new RuntimeException(ev);
        }

        // minimal in vector B scalar q
        int q2 = Data.resourcesMonitor.minB(Data.H, Data.H*2);
        Data.resourcesMonitor.compareScalarQ(q2);

        // signal about calculation of finding min of B
        Data.synchronizationMonitor.signalCalculatedScalarQ();

        // wait for calculation of finding min of B
        Data.synchronizationMonitor.waitForCalculatedScalarQ();

        // copy of q
        q2 = Data.resourcesMonitor.copyScalarQ();

        System.out.println(q2 + " q2");

        // copy of p
        int p2 = Data.resourcesMonitor.copyScalarP();

        System.out.println(p2 + " p2");

        // set M
        int []partVectorM = Data.multiplyVectorBySubMatrix(Data.resourcesMonitor.A,Data.resourcesMonitor.MB,Data.H,Data.H*2);
        Data.writeVectorResult(partVectorM,0,Data.resourcesMonitor.M,Data.H,Data.H);
        Data.synchronizationMonitor.signalForCalculatedVectorM();
        Data.synchronizationMonitor.waitForCalculatedVectorM();

        // set MT
        int[][] partMatrixMT = Data.multiplyMatrixAndSubMatrix(Data.resourcesMonitor.MZ, Data.resourcesMonitor.MR,Data.H,Data.H*2);
        Data.writeRealMatrix(Data.resourcesMonitor.getMT(),partMatrixMT,1);

        System.out.println(Arrays.deepToString(partMatrixMT) + " - 2 part MT");
        System.out.println(Arrays.deepToString(Data.resourcesMonitor.getMT()) + " My MT by method 2");

        // set L
        int [] partVectorL = Data.multiplyConstantBySubVector(p2,Data.resourcesMonitor.getM(),Data.H,Data.H*2);
        Data.writeVectorResult(partVectorL,0,Data.resourcesMonitor.L,Data.H,Data.H);
        System.out.println(Arrays.toString(partVectorL) + " L in T2");
        Data.synchronizationMonitor.signalForCalculatedVectorL();
        Data.synchronizationMonitor.waitForCalculatedVectorL();

        // set N
        int [] partVectorN = Data.multiplyVectorBySubMatrix(Data.resourcesMonitor.B, Data.resourcesMonitor.MT, Data.H, Data.H*2);
        Data.writeVectorResult(partVectorN,0,Data.resourcesMonitor.N,Data.H*2,Data.H);
    }
}
