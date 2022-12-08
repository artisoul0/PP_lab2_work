import java.util.Arrays;

public class Thread3 extends Thread{


    public void run(){
        System.out.println("T3 started");
        int H = Data.N/4;
        int [] B = new int[Data.N];
        int [][] MB = new int[Data.N][Data.N];
        int p = 1;
        Write.fillVectorByOne(B);
        Data.setB(B);
        Write.fillMatrixByOne(MB);
        Data.setMB(MB);
        System.out.println("T3 data has been successfully entered");
        Data.synchroMonitor.signalInput();
        Data.synchroMonitor.waitForInput();
        int q3 = Data.synchroMonitor.minQ(H*2, H*3);
        Data.synchroMonitor.findMinQ(q3);
        Data.synchroMonitor.signalMinQ();
        Data.synchroMonitor.waitForMinQ();
        System.out.println(Arrays.toString(Data.B));
        int q3_copied = Data.synchroMonitor.copyScalarQ();
        int p3_copied = Data.synchroMonitor.copyScalarP();
        System.out.println(q3 + " q3_copied");
    }
}
