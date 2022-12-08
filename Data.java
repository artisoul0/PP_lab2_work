public class Data {
    public  static int N = 8;
    public static int c;
    public static int p;
    public static int q;
    public static int []A, B, L, V, M;
    public static int [][] MB, MZ, MR;
    public static int e = 0;



    public static synchronized void setMB(int[][] MB) {
        Data.MB = MB;
    }

    public static synchronized void setMR(int[][] MR) {
        Data.MR = MR;
    }

    public static synchronized void setM(int[] M) {
        Data.M = M;
    }



    public static synchronized void setL(int[] L) {
        Data.L = L;
    }

    public static synchronized void setV(int[] V) {
        Data.V = V;
    }

    public static synchronized void setB(int[] B) {
        Data.B = B;
    }

    public static synchronized void setScalarQ(int q) {
        Data.q = q;
    }
    public static synchronized void setMZ(int[][] MZ) {
        Data.MZ = MZ;
    }
    public static synchronized void setA(int[] A) {
        Data.A = A;
    }
    public static synchronized void setScalarP(int p) {
        Data.p = p;
    }


    public static MonitorForSynchronization synchroMonitor = new MonitorForSynchronization();

    public static class MonitorForSynchronization {

        private static boolean first = false;
        private static int F1 = 0;
        private static int F2 = 0;
        private static int F3 = 0;

        public synchronized void waitForInput() {
            try {
                if (F1 != 3)
                    wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        public synchronized void signalInput() {
            ++F1;
            if (F1 == 3)
                notifyAll();
        }

//        public synchronized void findMinQ(int qi) {
//            if (Data.q > qi)
//                Data.q = qi;
//        }

        public synchronized void waitForMinQ() {
            try {
                System.out.println(F2 + " F2 in wait min");
                if (F2 != 4)
                    wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        public synchronized void signalMinQ() {

            F2++;
            System.out.println(F2 + " F2 in signal min");
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

        public int minQ(int start, int end) {
            int min = B[start];
            for (int i = start; i < end; i++) {
                min = Math.min(min, B[i]);
            }
            return min;
        }

        public synchronized void findMinQ(int qi) {
            if (Data.q > qi)
                setScalarQ(qi);
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

        public static int[] multiplyVectorBySubMatrix(int[] A, int[][] MD, int
                start, int end) {
            int[] K = new int[N];
            for (int i = 0; i < end - start; i++) {
                for (int j = 0; j < N; j++) {
                    for (int k = 0; k < MD[j].length; k++) {
                        K[k] += A[j] * MD[j][k];
                    }
                }
            }
            return K;
        }

        private static int[] multiplySubVectorByConstant(int a, int[] C, int
                start, int end) {
            for (int i = start; i < end; i++) {
                C[i] *= a;
            }
            return C;
        }

        public synchronized void compareScalarQ(int qi) {
            if (first) {
                if (qi < Data.q) {
                    Data.q = qi;
                }
            } else {
                Data.q = qi;
                first = true;
            }
        }
    }
    }

