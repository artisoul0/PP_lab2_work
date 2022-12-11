import java.util.Arrays;

public class Thread1 extends Thread{
    public void run(){
        System.out.println("T1 started");
        int H = Data.N/4;
        int [][] MZ = new int[Data.N][Data.N];
        Write.fillMatrixByOne(MZ);


        Data.resourcesMonitor.setMZ(MZ);

        Data.inputOutputMonitor.inputSignal();
        try {
            Data.inputOutputMonitor.waitForInputSignal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //minimal in vector B scalar q
        int q1 = Data.resourcesMonitor.minB(0,Data.H);
        Data.resourcesMonitor.compareScalarQ(q1);
        Data.synchronizationMonitor.signalCalculatedScalarQ();
        Data.synchronizationMonitor.waitForCalculatedScalarQ();

        q1 = Data.resourcesMonitor.copyScalarQ();

        System.out.println(q1 + " q1");

        int p1 = Data.resourcesMonitor.copyScalarP();

        System.out.println(p1 + " p1");


        //set M

        int []partOfVectorM = Data.multiplyVectorBySubMatrix(Data.resourcesMonitor.A,Data.resourcesMonitor.MB,0,Data.H);

        Data.writePartVector(partOfVectorM,0,Data.resourcesMonitor.M,0,H);

//        System.out.println(Arrays.toString(partOfVectorM) + " part in M");

        //set MT

        int [][] partOfMatrixMT = Data.multiplyMatrixAndSubMatrix(Data.resourcesMonitor.MZ,Data.resourcesMonitor.MR,0, Data.H);

//        System.out.println(Arrays.deepToString(Data.resourcesMonitor.MT) + " MT in T1");

//        Data.calculateResultPart(p1,q1,0,Data.H);

        //set L

        int []partOfVectorL = Data.multiplyConstantBySubVector(p1,partOfVectorM,0,Data.H);

        System.out.println(Arrays.toString(partOfVectorL) + " Part L in T1");

        System.out.println(Arrays.toString(Data.resourcesMonitor.L) + " L in T1");

        Data.inputOutputMonitor.OutputSignal();
    }
}
