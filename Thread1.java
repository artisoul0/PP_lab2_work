import java.util.Arrays;

public class Thread1 extends Thread{
    public void run(){
        System.out.println("T1 started");
        int H = Data.N/4;
        int [][] MZ = new int[Data.N][Data.N];
        Write.fillMatrixByOne(MZ);
        Data.setMZ(MZ);
        System.out.println("T1 data has been successfully entered");
        Data.synchroMonitor.signalInput();
        Data.synchroMonitor.waitForInput();
        int q1 = Data.synchroMonitor.minQ(0, H);
        Data.synchroMonitor.findMinQ(q1);
        System.out.println(Arrays.toString(Data.B));
        Data.synchroMonitor.signalMinQ();
        Data.synchroMonitor.waitForMinQ();
        int q1_copied = Data.synchroMonitor.copyScalarQ();
        int p1_copied = Data.synchroMonitor.copyScalarP();
        System.out.println(q1 + " q1_copied");
    }
}
