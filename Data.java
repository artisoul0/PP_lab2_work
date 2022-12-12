import java.util.Arrays;

public class Data {
    public static final int N = 4;
    public static final int P = 4;
    public static int H = N / P;
    public static ResourcesMonitor resourcesMonitor = new ResourcesMonitor();
    public static inputOutputMonitor inputOutputMonitor = new inputOutputMonitor();
    public static SynchronizationMonitor synchronizationMonitor = new SynchronizationMonitor();
    public static class SynchronizationMonitor {
        private int F1 = 0;
        private int F2 = 0;

        private int F3 = 0;

        private int F4 = 0;
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

        public synchronized void waitForCalculatedVectorM() {
            try {
                System.out.println(F2 + " in wait");
                if (F2 < 4) {
                    wait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public synchronized void signalForCalculatedVectorM() {
            ++F2;
            System.out.println(F2 + " in signal");
            if (F2 >= 4) {
                notifyAll();
            }
        }

        public synchronized void waitForCalculatedVectorL() {
            try {
                if (F3 < 4) {
                    wait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public synchronized void signalForCalculatedVectorL() {
            ++F3;

            if (F3 >= 4) {
                notifyAll();
            }
        }

        public synchronized void waitForCalculatedVectorN() {
            try {
                if (F4 < 4) {
                    wait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public synchronized void signalForCalculatedVectorN() {
            ++F4;

            if (F4 >= 4) {
                notifyAll();
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
        public int[] A;

        public int [] N = new int[Data.N];
        public int [] L = new int[Data.N];
        public int [] B = new int[Data.N];

        public int [] M = new int[Data.N];
        public int[][] MZ, MB, MR;

        public int [][] MT = new int[Data.N][Data.N];
        private static int e = 0;
        private boolean first = false;
        public synchronized void setMB(int[][] MB) {
            this.MB = MB;
        }

        public synchronized int[] getM(){return M;}

        public synchronized int[] getL(){return L;}

        public synchronized int[] getVectorN(){return N;}

        public synchronized int[][] getMT(){return MT;}

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



        public int minB(int start, int end) {
            int min = Data.resourcesMonitor.B[start];
            for (int i = start; i < end; i++) {
                min = Math.min(min, Data.resourcesMonitor.B[i]);
            }
            return min;
        }
    }


    public static int[] multiplyVectorBySubMatrix(int[] A, int[][] MB, int start, int end) {
        int[] K = new int[N];
        for (int i = 0; i < end - start; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < MB[j].length; k++) {
                    K[k] += A[j] * MB[j][k];
                }
            }
        }
        return K;
    }

    public static synchronized void writeVectorResult(int [] source, int sourcePos, int [] dest, int destPos, int amount){
        System.arraycopy(source,sourcePos,dest,destPos,amount);
    }

    public static synchronized void writeMatrixResult(int [][] source, int sourcePos, int [][] dest, int destPos, int amount){
        for (int i = 0; i < N; i++) {
                System.arraycopy(source,sourcePos,dest,i,amount);
        }

    }

    public static synchronized void writeRealMatrix( int [][] commonResourceMatrix, int [][] source, int start){
        for (int i = 0; i < Data.N; i++) {
            for (int j = 0; j < Data.N; j++) {
                commonResourceMatrix[i][start] = source[i][start];
            }
        }
    }


    private static void writeToResult(int e) {
        ResourcesMonitor.e = e;
    }
    public static int multiplyVectorAndSubVector(int[] X, int[] Y, int start, int end)
    {
        int result = 0;
        for (int i = start; i < end; i++)
        {
            result += X[i] * Y[i];
        }
        return result;
    }




    public static int[] multiplyConstantBySubVector(int a, int[] Vector, int start, int end) {
        for (int i = start; i < end; i++) {
            Vector[i] *= a;
        }
        return Vector;
    }




    public static int[][] multiplyMatrixAndSubMatrix(int[][] MX, int[][] MY, int start, int end) {
        int [][] Matrix = new int[Data.N][Data.N];
        for (int i = 0; i < N; i++) {
            int g = start;
            for (int j = start; j < end; j++) {
                Matrix[i][g] = 0;
                for (int k = 0; k < N; k++) {
                    Matrix[i][g] += MX[i][k] * MY[k][j];
                }
                g++;
            }
        }
        return Matrix;
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