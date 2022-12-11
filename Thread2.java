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


        int []partOfVectorM = Data.multiplyVectorBySubMatrix(Data.resourcesMonitor.A,Data.resourcesMonitor.MB,Data.H,Data.H*2);

        Data.writePartVector(partOfVectorM,0,Data.resourcesMonitor.M, H,H);

        System.out.println(Arrays.toString(partOfVectorM) + " part in M");

//        System.out.println("T2 data has been successfully entered");
//        Data.synchroMonitor.signalInput();
//        Data.synchroMonitor.waitForInput();
//        int q2 = Data.synchroMonitor.minQ(H, H*2);
//        Data.synchroMonitor.compareScalarQ(q2);
//        Data.synchroMonitor.signalMinQ();
//        Data.synchroMonitor.waitForMinQ();
//        System.out.println(Arrays.toString(Data.B));
//        int q2_copied = Data.synchroMonitor.copyScalarQ();
//        int p2_copied = Data.synchroMonitor.copyScalarP();
//        int c2_copied = Data.synchroMonitor.copyScalarC();
//        e = p2_copied + c2_copied;
    }
}
