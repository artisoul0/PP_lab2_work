/**
 * Лабораторна робота №3
 * Група: ІО-03
 * Автор: Веруга Юрій Олександрович
 * Мій варіант: 11
 * Завдання: e = ((p*(A*MB)*(B*(MZ*MR)) + min(B)
 * T1: MZ
 * T2: e, A, MR
 * T3: MB, B, p
 * T4: -
 */


public class Main {
    public static void main(String[] args){
        System.out.println("Lab 3 started");
        Thread T1 = new Thread1();
        Thread T2 = new Thread2();
        Thread T3 = new Thread3();
        Thread T4 = new Thread4();
    }
}
