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

        System.out.println(q4 + " q4");
    }
}
