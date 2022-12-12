import java.util.Arrays;

public class Thread2 extends Thread{


    public void run(){
        System.out.println("T2 started");
        int H = Data.N/4;
        int [] A = new int[Data.N];
        int [][] MR = new int[Data.N][Data.N];
        int e = 0;
        int c = 0;
        Write.fillVectorByOne(A);
        Write.fillMatrixByOne(MR);
        Data.resourcesMonitor.setA(A);
        Data.resourcesMonitor.setMR(MR);

        Data.inputOutputMonitor.inputSignal();
        try {
            Data.inputOutputMonitor.waitForInputSignal();
        } catch (InterruptedException ev) {
            throw new RuntimeException(ev);
        }

        int q2 = Data.resourcesMonitor.minB(H,H*2);
        Data.resourcesMonitor.compareScalarQ(q2);
        Data.synchronizationMonitor.signalCalculatedScalarQ();
        Data.synchronizationMonitor.waitForCalculatedScalarQ();

        q2 = Data.resourcesMonitor.copyScalarQ();

        System.out.println(q2 + " q2");
        int p2 = Data.resourcesMonitor.copyScalarP();

//        Data.calculateResultPart(p2,q2,H,H*2);


        Data.inputOutputMonitor.OutputSignal();

        //set M
        int []partVectorM = Data.multiplyVectorBySubMatrix(Data.resourcesMonitor.A,Data.resourcesMonitor.MB,Data.H,Data.H*2);
        Data.writeVectorResult(partVectorM,0,Data.resourcesMonitor.M,H,H);

        //set MT

        int[][] partMatrixMT = Data.multiplyMatrixAndSubMatrix(Data.resourcesMonitor.MZ, Data.resourcesMonitor.MR,Data.H,Data.H*2);

        Data.writeRealMatrix(Data.resourcesMonitor.MT,partMatrixMT,1);
        System.out.println(Arrays.deepToString(Data.resourcesMonitor.MT) + " My MT by method#2");


    }
}
