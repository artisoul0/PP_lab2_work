import java.util.Arrays;

public class Thread1 extends Thread{
    public void run(){
        System.out.println("T1 started");

//        int H = Data.N/4; ---------- непотрібно, бо є в Data. може саме через це було неправильно ----------

        // input
        int [][] MZ = new int[Data.N][Data.N];
        Write.fillMatrixByOne(MZ);
        Data.resourcesMonitor.setMZ(MZ);

        // signal about input
        Data.inputOutputMonitor.inputSignal();

        // wait for input
        try {
            Data.inputOutputMonitor.waitForInputSignal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // minimal in vector B scalar q
        int q1 = Data.resourcesMonitor.minB(0, Data.H);
        Data.resourcesMonitor.compareScalarQ(q1);

        // signal about calculation of finding min of B
        Data.synchronizationMonitor.signalCalculatedScalarQ();

        // wait for calculation of finding min of B
        Data.synchronizationMonitor.waitForCalculatedScalarQ();

        // copy of q
        q1 = Data.resourcesMonitor.copyScalarQ();

        System.out.println(q1 + " q1");

        // copy of p
        int p1 = Data.resourcesMonitor.copyScalarP();

        System.out.println(p1 + " p1");

        // set M
        int []partVectorM = Data.multiplyVectorBySubMatrix(Data.resourcesMonitor.A,Data.resourcesMonitor.MB,0,Data.H);
        Data.writeVectorResult(partVectorM,0,Data.resourcesMonitor.M,0,Data.H);

        // set MT
        int[][] partMatrixMT = Data.multiplyMatrixAndSubMatrix(Data.resourcesMonitor.MZ, Data.resourcesMonitor.MR,0,Data.H);
        Data.writeRealMatrix(Data.resourcesMonitor.getMT(),partMatrixMT,0);

        System.out.println(Arrays.deepToString(partMatrixMT) + " - 1 part MT");
        System.out.println(Arrays.deepToString(Data.resourcesMonitor.getMT()) + " My MT by method 1");

        // set L
        int [] partVectorL = Data.multiplyConstantBySubVector(p1,Data.resourcesMonitor.getM(),0,Data.H);
        Data.writeVectorResult(partVectorL,0,Data.resourcesMonitor.L,0,Data.H);
        System.out.println(Arrays.toString(partVectorL) + " L in T1");

        System.out.println(Arrays.toString(Data.resourcesMonitor.getL()) + " : common L");

        // set N
        int [] partVectorN = Data.multiplyVectorBySubMatrix(Data.resourcesMonitor.B, Data.resourcesMonitor.getMT(), 0, Data.H);
    }
}




