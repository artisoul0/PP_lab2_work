import java.util.Arrays;

public class Thread1 extends Thread{
    public void run(){
        System.out.println("T1 started");
        int H = Data.N/4;
        int [][] MZ = new int[Data.N][Data.N];
        Write.fillMatrixByOne(MZ);


        Data.resourcesMonitor.setMZ(MZ);

        Data.inputOutputMonitor.inputSignal();
        try {
            Data.inputOutputMonitor.waitForInputSignal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //minimal in vector B scalar q
        int q1 = Data.resourcesMonitor.minB(0,Data.H);
        Data.resourcesMonitor.compareScalarQ(q1);
        Data.synchronizationMonitor.signalCalculatedScalarQ();
        Data.synchronizationMonitor.waitForCalculatedScalarQ();

        q1 = Data.resourcesMonitor.copyScalarQ();

        System.out.println(q1 + " q1");

        int p1 = Data.resourcesMonitor.copyScalarP();

        System.out.println(p1 + " p1");

        //set M
        int []partVectorM = Data.multiplyVectorBySubMatrix(Data.resourcesMonitor.A,Data.resourcesMonitor.MB,0,Data.H);
        Data.writeVectorResult(partVectorM,0,Data.resourcesMonitor.M,0,H);

        //set MT

        int[][] partMatrixMT = Data.multiplyMatrixAndSubMatrix(Data.resourcesMonitor.MZ, Data.resourcesMonitor.MR,0,Data.H);

        Data.writeRealMatrix(Data.resourcesMonitor.MT,partMatrixMT,0);
        System.out.println(Arrays.deepToString(Data.resourcesMonitor.MT) + " My MT by method");

        System.out.println(Arrays.deepToString(partMatrixMT) + " part MT");



        Data.inputOutputMonitor.OutputSignal();
    }
}




