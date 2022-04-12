package arraylist;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileRead {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\Icaro\\Documents\\Dev\\java\\sockets\\src\\questions.txt");


            Scanner fileMem = new Scanner(file);

            while(fileMem.hasNextLine()){
                System.out.println(fileMem.toString());
            }
{
        }
    }
}
