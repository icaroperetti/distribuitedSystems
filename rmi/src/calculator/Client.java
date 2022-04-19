package calculator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 12345);
            CalcInterface calc = (CalcInterface) registry.lookup("CalcRemote");
            Scanner in = new Scanner(System.in);
            int opt = -1;
            Scanner scanner = new Scanner(System.in);
            do{
                System.out.println("Escolha um opção:");
                System.out.println("1 - Somar");
                System.out.println("2 - Subtrair");
                System.out.println("3 - Multiplicar");
                System.out.println("4 - Dividir");
                System.out.println("0 - Sair");
                opt = scanner.nextInt();

                if(opt == 0) break;

                System.out.println("Forneça dois números");
                float a = scanner.nextFloat();
                float b = scanner.nextFloat();
                float resp = 0;

                switch (opt){
                    case 1:
                        resp = calc.sum(a, b);
                        break;
                    case 2:
                        resp = calc.sub(a, b);
                        break;
                    case 3:
                        resp = calc.mul(a, b);
                        break;
                    case 4:
                        resp = calc.div(a, b);
                        break;
                    default:
                        break;

                }
                System.out.println("Resposta: " + resp);
            }while(true);
        }catch (RemoteException | NotBoundException e ){
            e.printStackTrace();
        }
    }
}
