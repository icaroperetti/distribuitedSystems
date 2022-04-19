package calculator;

import calculator.CalcInterface;

import java.rmi.RemoteException;

public class Calculator implements CalcInterface {

    public float sum(float a, float b) throws RemoteException {
        return a+b;
    }

    public float sub(float a, float b) throws RemoteException {
        return a-b;
    }

    public float mul(float a, float b) throws RemoteException {
        return a*b;
    }

    public float div(float a, float b) throws RemoteException {
        return a/b;
    }


}
