public class Thread2 extends Thread{


    public void run(){
        System.out.println("T2 started");
        int H = Data.N/4;
        int [] A = new int[Data.N];
        int [][] MR = new int[Data.N][Data.N];
        int e = 0;
        int c = 0;
        Write.fillVectorByOne(A);
        Data.setA(A);
        Write.fillMatrixByOne(MR);
        Data.setMR(MR);
        System.out.println("T2 data has been successfully entered");
        Data.synchroMonitor.signalInput();
        Data.synchroMonitor.waitForInput();
        int q2 = Data.synchroMonitor.minQ(H, H*2);
        Data.synchroMonitor.signalMinQ();
        Data.synchroMonitor.waitForMinQ();
        int q2_copied = Data.synchroMonitor.copyScalarQ();
        int p2_copied = Data.synchroMonitor.copyScalarP();
        int c2_copied = Data.synchroMonitor.copyScalarC();
        e = p2_copied + c2_copied;
    }
}
