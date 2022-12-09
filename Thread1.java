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

        int q1 = Data.resourcesMonitor.minB(0,Data.H);
        Data.resourcesMonitor.compareScalarQ(q1);
        Data.synchronizationMonitor.signalCalculatedScalarQ();
        Data.synchronizationMonitor.waitForCalculatedScalarQ();


    }
}
