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

        // set MT
        int[][] partMatrixMT = Data.multiplyMatrixAndSubMatrix(Data.resourcesMonitor.MZ, Data.resourcesMonitor.MR,Data.H*3,Data.H*4);
        Data.writeRealMatrix(Data.resourcesMonitor.getMT(),partMatrixMT,3);

        System.out.println(Arrays.deepToString(partMatrixMT) + " - 4 part MT");
        System.out.println(Arrays.deepToString(Data.resourcesMonitor.getMT()) + " My MT by method 4");
    }
}
