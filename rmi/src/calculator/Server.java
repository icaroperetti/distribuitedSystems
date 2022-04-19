package calculator;

import calculator.CalcInterface;
import calculator.Calculator;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server  {
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "localhost");
            Registry registry = LocateRegistry.createRegistry(12345);

            Calculator calc = new Calculator();
            CalcInterface calcRemote = (CalcInterface) UnicastRemoteObject.exportObject(calc, 0);
            registry.bind("CalcRemote", calcRemote);

            System.out.println("Server Calc ready");

        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
