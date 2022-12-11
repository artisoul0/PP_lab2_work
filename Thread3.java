import java.util.Arrays;

public class Thread3 extends Thread{


    public void run(){
        System.out.println("T3 started");
        int H = Data.N/4;
        int [] B = new int[Data.N];
        int [][] MB = new int[Data.N][Data.N];
        int p = 1;
        Data.resourcesMonitor.setScalarP(1);
        Write.fillVectorByOne(B);
        Write.fillMatrixByOne(MB);

        Data.resourcesMonitor.setB(B);
        Data.resourcesMonitor.setMB(MB);

        Data.inputOutputMonitor.inputSignal();
        try {
            Data.inputOutputMonitor.waitForInputSignal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int q3 = Data.resourcesMonitor.minB(H*2,H*3);
        Data.resourcesMonitor.compareScalarQ(q3);
        Data.synchronizationMonitor.signalCalculatedScalarQ();
        Data.synchronizationMonitor.waitForCalculatedScalarQ();

        q3 = Data.resourcesMonitor.copyScalarQ();

        System.out.println(q3 + " q3");

        int p3 = Data.resourcesMonitor.copyScalarP();

//        Data.calculateResultPart(p3,q3,H*2,H*3);

        Data.inputOutputMonitor.OutputSignal();


    }
}
