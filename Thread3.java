import java.util.Arrays;

public class Thread3 extends Thread{


    public void run(){
        System.out.println("T3 started");

//        int H = Data.N/4; ---------- непотрібно, бо є в Data. може саме через це було неправильно ----------

        // input
        int [] B = new int[Data.N];
        int [][] MB = new int[Data.N][Data.N];
        Write.fillVectorByOne(B);
        Write.fillMatrixByOne(MB);
        Data.resourcesMonitor.setB(B);
        Data.resourcesMonitor.setMB(MB);
        Data.resourcesMonitor.setScalarP(1);

        // signal about input
        Data.inputOutputMonitor.inputSignal();

        // wait for input
        try {
            Data.inputOutputMonitor.waitForInputSignal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // minimal in vector B scalar q
        int q3 = Data.resourcesMonitor.minB(Data.H*2, Data.H*3);
        Data.resourcesMonitor.compareScalarQ(q3);

        // signal about calculation of finding min of B
        Data.synchronizationMonitor.signalCalculatedScalarQ();

        // wait for calculation of finding min of B
        Data.synchronizationMonitor.waitForCalculatedScalarQ();

        // copy of q
        q3 = Data.resourcesMonitor.copyScalarQ();

        System.out.println(q3 + " q3");

        // copy of p
        int p3 = Data.resourcesMonitor.copyScalarP();

        System.out.println(p3 + " p3");

        // set M
        int []partVectorM = Data.multiplyVectorBySubMatrix(Data.resourcesMonitor.A,Data.resourcesMonitor.MB,Data.H*2,Data.H*3);
        Data.writeVectorResult(partVectorM,0,Data.resourcesMonitor.M,Data.H*2,Data.H);

        // set MT
        int[][] partMatrixMT = Data.multiplyMatrixAndSubMatrix(Data.resourcesMonitor.MZ, Data.resourcesMonitor.MR,Data.H*2,Data.H*3);
        Data.writeRealMatrix(Data.resourcesMonitor.getMT(),partMatrixMT,2);

        System.out.println(Arrays.deepToString(partMatrixMT) + " - 3 part MT");
        System.out.println(Arrays.deepToString(Data.resourcesMonitor.getMT()) + " My MT by method 3");

        // set L
        int [] partVectorL = Data.multiplyConstantBySubVector(p3,Data.resourcesMonitor.getM(),Data.H*2,Data.H*3);
        Data.writeVectorResult(partVectorL,0,Data.resourcesMonitor.L,Data.H*2,Data.H);
        System.out.println(Arrays.toString(partVectorL) + " L in T3");
    }
}
