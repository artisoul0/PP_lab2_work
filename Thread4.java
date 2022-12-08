import java.util.Arrays;

public class Thread4 extends Thread{


    public void run(){
        int H = Data.N/4;
        System.out.println("T4 started");
        Data.synchroMonitor.waitForInput();
        int q4 = Data.synchroMonitor.minQ(H*3, H*4);
        Data.synchroMonitor.compareScalarQ(q4);
        Data.synchroMonitor.signalMinQ();
        Data.synchroMonitor.waitForMinQ();
        System.out.println(Arrays.toString(Data.B));
        int q4_copied = Data.synchroMonitor.copyScalarQ();
        int p4_copied = Data.synchroMonitor.copyScalarP();
        System.out.println(q4 + " q4");
        System.out.println(q4_copied + "  q4_copied");
    }
}
