public class Write {

    public static void fillMatrixByOne(int [][] Matrix){
        for (int i = 0; i < Data.N; i++) {
            for (int j = 0; j < Data.N; j++) {
                Matrix[i][j] = 1;
            }
        }
    }

    public static int [] fillVectorByOne(int [] Vector){
        for (int i = 0; i < Data.N; i++) {
            Vector[i] = 1;
        }
        return Vector;
    }

    public static void writePartVector(int [] VectorSource, int [] VectorDestination, int start, int end){
        System.arraycopy(VectorSource,start,VectorDestination,end,Data.H);
    }



}
