
public class Data {
    public static final int N = 4;
    public static final int P = 4;
    public static int H = N / P;
    public static ResourcesMonitor resourcesMonitor = new ResourcesMonitor();
    public static inputOutputMonitor inputOutputMonitor = new inputOutputMonitor();
    public static SynchronizationMonitor synchronizationMonitor = new SynchronizationMonitor();
    public static class SynchronizationMonitor {
        private int F1 = 0;
        public synchronized void signalCalculatedScalarQ() {
            F1++;
            if (F1 >= 4) {
                notifyAll();
            }
        }
        public synchronized void waitForCalculatedScalarQ() {
            try {

                if (F1 < 4) {
                    wait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static class inputOutputMonitor {
        private int F1 = 0;
        private int F2 = 0;
        public synchronized void inputSignal() {
            F1++;
            if (F1 >= 2) {
                notifyAll();
            }
        }
        public synchronized void waitForInputSignal() throws
                InterruptedException {
            if (F1 < 2) {
                wait();
            }
        }
        public synchronized void OutputSignal() {
            F2++;
            if (F2 >= 3) {
                notify();
            }
        }
        public synchronized void WaitForOutputSignal() throws
                InterruptedException {
            if (F2 < 3) {
                wait();
            }
        }
    }
    public static class ResourcesMonitor {
        public int q,c,p;
        public int[] A, B;
        public int[][] MZ, MB, MR;
        private static final int e = 0;
        private boolean first = false;
        public synchronized void setMB(int[][] MB) {
            this.MB = MB;
        }
        public synchronized void setMR(int[][] MR) {
            this.MR = MR;
        }
        public synchronized void setMZ(int[][] MZ) {
            this.MZ = MZ;
        }
        public synchronized void setA(int[] A) {
            this.A = A;
        }

        public synchronized void setB(int[] B) {
            this.B = B;
        }
        public synchronized void setScalarP(int p) {
            this.p = p;

        }




        public synchronized int getScalarE() {
            return e;
        }
        public synchronized void compareScalarQ(int qi) {
            if (first) {
                if (qi < this.q) {
                    this.q = qi;
                }
            } else {
                this.q = qi;
                first = true;
            }
        }

        public synchronized int copyScalarC() {
            return c;
        }
        public synchronized int copyScalarQ() {
            return q;
        }
        public synchronized int copyScalarP() {
            return p;
        }



//        public void calculateResultPart(int di, int ai, int start, int end) {
//            writeToResult(
//                    sumMatrix(
//                            multiplySubMatrixByConstant(di,
//                                    multiplyMatrixAndSubMatrix(MB,
//                                            multiplyMatrixAndSubMatrix(MC, MM, start,
//                                                    end, new int[N][N]),
//                                            start, end, new int[N][N]), start, end),
//                            multiplySubMatrixByConstant(ai, MC, start, end),
//                            start, end),
//                    start, end);
//        }
//        private static void writeToResult(int[][] MW, int start, int end) {
//            for (int i = 0; i < N; i++) {
//                for (int j = start; j < end; j++) {
//                    MO[i][j] = MW[i][j];
//                }
//            }
//        }
        public int minB(int start, int end) {
            int min = B[start];
            for (int i = start; i < end; i++) {
                min = Math.min(min, B[i]);
            }
            return min;
        }
    }
    private static int[][] sumMatrix(int[][] X, int[][] Y, int start, int
            end) {
        for (int j = 0; j < N; j++) {
            for (int i = start; i < end; i++) {
                X[j][i] = X[j][i] + Y[j][i];
            }
        }
        return X;
    }

    public static int[][] multiplyMatrixAndSubMatrix(int[][] MX, int[][] MY,
                                                     int start, int end, int[][] MT) {
        for (int i = 0; i < N; i++) {
            int g = start;
            for (int j = start; j < end; j++) {
                MT[i][g] = 0;
                for (int k = 0; k < N; k++) {
                    MT[i][g] += MX[i][k] * MY[k][j];
                }
                g++;
            }
        }
        return MT;
    }
    public static int[][] multiplySubMatrixByConstant(int a, int[][] MX, int
            start, int end) {
        for (int j = 0; j < N; j++) {
            for (int i = start; i < end; i++) {
                MX[j][i] *= a;
            }
        }
        return MX;
    }
    public static void printMatrix(int[][] MX) throws InterruptedException {
        for (int[] X : MX) {
            for (int x : X) {
                System.out.print("\t" + x);
            }
            System.out.println();
        }
    }
}