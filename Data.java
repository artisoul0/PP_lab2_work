public class Data {
    public static final int N = 8;
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

        public int [] L = new int[Data.N];

        public int [] N = new int[Data.N];
        public int [] M = new int[Data.N];
        public int[][] MZ, MB, MR;

        public int [][] MT = new int[Data.N][Data.N];
        private static int e = 0;
        private boolean first = false;
        public synchronized void setMB(int[][] MB) {
            this.MB = MB;
        }

        public synchronized void setM(int [] M){this.M = M;}

        public synchronized void setL(int [] L){this.L = L;}

        public synchronized void setN(int [] N){this.N = N;}

        public synchronized void setMT(int [][] MT){this.MT = MT;}
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
            int min = B[start];
            for (int i = start; i < end; i++) {
                min = Math.min(min, B[i]);
            }
            return min;
        }
    }



//    public static void calculateResultPart(int pi, int qi, int start, int end) {
//        int [] M = Data.multiplyVectorBySubMatrix(Data.resourcesMonitor.A, Data.resourcesMonitor.MB,start,end); //M
//        int [][] MT = Data.multiplyMatrixAndSubMatrix(Data.resourcesMonitor.MZ, Data.resourcesMonitor.MR,start,end, new int [N][N]); //MT
//        int [] L = Data.multiplySubVectorByConstant(pi, M,start,end); // L
//        int [] N = Data.multiplyVectorBySubMatrix(Data.resourcesMonitor.B, MT,start,end); //N
//        int c = Data.multiplyVectorAndSubVector(L,N,start,end);
//        System.out.println(Arrays.toString(L) + " L");
//        System.out.println(Arrays.toString(N) + " N");
//        int e = c+qi;
//
//        System.out.println(e + " e");
//        System.out.println(c + " c");
//    }



    //changed method to get only a part
    public static int[] multiplyVectorBySubMatrix(int[] A, int[][] MB, int start, int end) {
        int[] K = new int[H];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < H; k++) { //MB[j].length in k
                    K[k] += A[j] * MB[j][k];
                }
            }
        }
        return K;
    }

    public static synchronized int[][] multiplyMatrixAndSubMatrix(int[][] MZ, int[][] MR, int start, int end) {
//        int [][] Matrix = new int[N][N];

        for (int i = 0; i < N; i++) {
            int g = start;
            for (int j = start; j < end; j++) {
                Data.resourcesMonitor.MT[i][g] = 0;
                for (int k = 0; k < N; k++) {
                    Data.resourcesMonitor.MT[i][g] += MZ[i][k] * MR[k][j];
                }
                g++;
            }
        }
        return Data.resourcesMonitor.MT;
    }




    public static synchronized void writePartVector(int[] Vector, int SourcePos, int[] VectorDest, int DestPosition, int amount){
        System.arraycopy(Vector,SourcePos,VectorDest, DestPosition, amount );
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




    public static int[] multiplyConstantBySubVector(int a, int[] C, int start, int end) {
        for (int i = start; i < end; i++) {
            Data.resourcesMonitor.L[i] = C[i] * a;
        }
        return C;
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