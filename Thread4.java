import java.util.Arrays;

public class Thread4 extends Thread{


    public void run(){
        int H = Data.N/4;
        System.out.println("T4 started");

        try {
            Data.inputOutputMonitor.waitForInputSignal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int q4 = Data.resourcesMonitor.minB(H*3,H*4);
        Data.resourcesMonitor.compareScalarQ(q4);
        Data.synchronizationMonitor.signalCalculatedScalarQ();
        Data.synchronizationMonitor.waitForCalculatedScalarQ();

        q4 = Data.resourcesMonitor.copyScalarQ();

        int p4 = Data.resourcesMonitor.copyScalarP();


        System.out.println(q4 + " q4");


        try {
            Data.inputOutputMonitor.WaitForOutputSignal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //set M
        int []partVectorM = Data.multiplyVectorBySubMatrix(Data.resourcesMonitor.A,Data.resourcesMonitor.MB,Data.H*3,Data.H*4);
        Data.writeVectorResult(partVectorM,0,Data.resourcesMonitor.M,H*3,H);


        //set MT

        int[][] partMatrixMT = Data.multiplyMatrixAndSubMatrix(Data.resourcesMonitor.MZ, Data.resourcesMonitor.MR,Data.H*3,Data.H*4);
        Data.writeRealMatrix(Data.resourcesMonitor.getMT(),partMatrixMT,3);
        Data.synchronizationMonitor.signalForCalculatedMatrixMT();
        Data.synchronizationMonitor.waitForCalculatedMatrixMT();
        System.out.println(Arrays.deepToString(Data.resourcesMonitor.getMT()) + " My MT by method#4");


    }
}
