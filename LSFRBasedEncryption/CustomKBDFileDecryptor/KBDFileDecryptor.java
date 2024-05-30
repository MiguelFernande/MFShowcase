import java.io.*;
import java.util.*;

public class KBDFileDecryptor {
    public static void main(String args[]) 
    {
        //input
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Insert File Path:");
        String filePath = scanner.nextLine();

        scanner.close();

        try {
            BinaryDecryptor decryptor = new BinaryDecryptor(filePath);

            decryptor.decryptFile();
            } 
                catch (FileNotFoundException e) 
                {
                    System.out.println("Error: file not found");
                } 
                catch (IOException e) 
                {
                    System.out.println("Error reading file");
                    e.printStackTrace();
                }
    }
}