import java.util.Arrays;

public class Thread3 extends Thread{


    public void run(){
        System.out.println("T3 started");
        int H = Data.N/4;
        int [] B = new int[Data.N];
        int [][] MB = new int[Data.N][Data.N];
        int p = 2;
        Data.resourcesMonitor.setScalarP(p);
        Write.fillVectorByOne(B);
        Write.fillMatrixByOne(MB);

        Data.resourcesMonitor.setB(B);
        Data.resourcesMonitor.setMB(MB);

        Data.inputOutputMonitor.inputSignal();
        try {
            Data.inputOutputMonitor.waitForInputSignal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int q3 = Data.resourcesMonitor.minB(H*2,H*3);
        Data.resourcesMonitor.compareScalarQ(q3);
        Data.synchronizationMonitor.signalCalculatedScalarQ();
        Data.synchronizationMonitor.waitForCalculatedScalarQ();

        q3 = Data.resourcesMonitor.copyScalarQ();

        System.out.println(q3 + " q3");

        int p3 = Data.resourcesMonitor.copyScalarP();

//        Data.calculateResultPart(p3,q3,H*2,H*3);

        Data.inputOutputMonitor.OutputSignal();

        //set M
        int []partOfVectorM = Data.multiplyVectorBySubMatrix(Data.resourcesMonitor.getA(),Data.resourcesMonitor.getMB(),Data.H*2,Data.H*3);

        Data.writePartVector(partOfVectorM,0,Data.resourcesMonitor.M, H*2,H);

        Data.synchronizationMonitor.signalCalculatedVectorM();

        //set MT

        int [][] partOfMatrixMT = Data.multiplyMatrixAndSubMatrix(Data.resourcesMonitor.getMZ(),Data.resourcesMonitor.getMR(),Data.H*2, Data.H*3);

//        System.out.println(Arrays.deepToString(Data.resourcesMonitor.MT) + " MT in T3");

//        System.out.println(Arrays.toString(partOfVectorM) + " part in M");

        //set L
        Data.synchronizationMonitor.waitForCalculatedVectorM();
        int []partOfVectorL = Data.multiplyConstantBySubVector(p3,Data.resourcesMonitor.getM(),Data.H*2,Data.H*3);


        System.out.println(Arrays.toString(Data.resourcesMonitor.getL()) + " L in T3");

        System.out.println(Arrays.toString(Data.resourcesMonitor.getM()) + " M in Thread");

    }
}
