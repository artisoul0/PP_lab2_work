public class MonitorForSynchronization {

    private static int F1 = 0;
    private static int F2 = 0;
    private static int F3 = 0;

    public synchronized void waitForInput() {
        try {
            if (F1 != 2)
                wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void signalInput() {
        ++F1;
        if (F1 == 2)
            notifyAll();
    }

    public synchronized void findMinQ(int qi) {
        if (Data.q > qi)
            Data.q = qi;
    }

    public synchronized void waitForMinA() {
        try {
            if (F2 != 4)
                wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void signalMinQ() {
        ++F2;
        if (F2 == 4)
            notifyAll();
    }

    public synchronized int copyScalarQ() {
        return Data.q;
    }

    public synchronized int copyScalarC() {
        return Data.c;
    }

    public synchronized int copyScalarP() {
        return Data.p;
    }

    public synchronized int[] copyVectorA() {
        return Data.A;
    }

    public synchronized int[] copyVectorB() {
        return Data.B;
    }

    public synchronized int[][] copyMatrixMZ() {
        return Data.MZ;
    }

    public synchronized void waitForCalcScalarE() {
        try {
            if (F3 != 3)
                wait();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void signalFinishCalcScalarE() {
        ++F3;
        if (F3 == 3)
            notify();
    }

//    public synchronized void assignMO(int[][] MOhi, int start, int end) {
//        int j = 0;
//        for (int i = start; i < end; i++) {
//            MO[i] = MOhi[j];
//            j++;
//        }
//    }
}

