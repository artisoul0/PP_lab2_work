import java.util.Arrays;

public class Thread4 extends Thread{


    public void run(){
        int H = Data.N/4;
        System.out.println("T4 started");

        try {
            Data.inputOutputMonitor.waitForInputSignal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int q4 = Data.resourcesMonitor.minB(H*3,H*4);
        Data.resourcesMonitor.compareScalarQ(q4);
        Data.synchronizationMonitor.signalCalculatedScalarQ();
        Data.synchronizationMonitor.waitForCalculatedScalarQ();

        q4 = Data.resourcesMonitor.copyScalarQ();

        int p4 = Data.resourcesMonitor.copyScalarP();


        System.out.println(q4 + " q4");

//        Data.calculateResultPart(p4,q4,H*3,H*4);

        try {
            Data.inputOutputMonitor.WaitForOutputSignal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //set M
        int []partOfVectorM = Data.multiplyVectorBySubMatrix(Data.resourcesMonitor.getA(),Data.resourcesMonitor.getMB(),Data.H*3,Data.H*4);
        Data.writePartVector(partOfVectorM,0,Data.resourcesMonitor.M, H*3,H);

        Data.synchronizationMonitor.signalCalculatedVectorM();

        //set MT
        int [][] partOfMatrixMT = Data.multiplyMatrixAndSubMatrix(Data.resourcesMonitor.getMZ(),Data.resourcesMonitor.getMR(),Data.H*3, Data.H*4);

//        System.out.println(Arrays.deepToString(Data.resourcesMonitor.MT) + " MT in T4");

//        System.out.println(Arrays.toString(partOfVectorM) + " part in M");
//        System.out.println(Arrays.toString(Data.resourcesMonitor.M));


        //set L
        Data.synchronizationMonitor.waitForCalculatedVectorM();
        int []partOfVectorL = Data.multiplyConstantBySubVector(p4,Data.resourcesMonitor.getM(),Data.H*3,Data.H*4);


        System.out.println(Arrays.toString(Data.resourcesMonitor.getL()) + " L in T4");

        System.out.println(Arrays.toString(Data.resourcesMonitor.getM()) + " M in Thread");

    }
}
