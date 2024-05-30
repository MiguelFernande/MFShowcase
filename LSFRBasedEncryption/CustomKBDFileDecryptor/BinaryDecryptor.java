/*
This code is responsible for properly displaying the decrypted data.
It also contains the Crypt method from challenege 1.
 */


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class BinaryDecryptor {

    private String inputFilePath;
    private static long feedbackpolynomial = 0x87654321L;
    public static final long initialValue = 0x4F574154;

    public BinaryDecryptor(String inputFilePath) {
        this.inputFilePath = inputFilePath;

    }

    /*
    Calls the parse method to parse the file.
    Takes the entries and outputs them in a clean format.
    */
    public void decryptFile() throws IOException 
    {
        try 
        {
            KDBFileParser parser = new KDBFileParser(inputFilePath);

            List<Entry> entries = parser.parse();

            for (Entry entry : entries)
            {
                byte[] decryptedData = entry.decryptEntryData();

                String outputText = new String(decryptedData, StandardCharsets.UTF_8);

                System.out.println(entry.getName());
                System.out.println("Decrypted Content: " + outputText);
                System.out.println("--------------------------------------------------");
            }
        }
            catch (InvalidFormatException e)
            {
                System.out.println("Invalid file format: " + e.getMessage());
            }
    }

    //LSFR Algorithm
    public static byte[] Crypt(byte[] data, long initialValue)
    {

        byte[] result = new byte[data.length];

        for (int i = 0; i < data.length; i++)
        {

            //step 8 times
            for (int j = 0; j < 8; j++)
            {
                if ((initialValue & 1) == 0)
                {
                    //disacrd lowest bit if its zero (even)
                    initialValue = initialValue >>> 1;
                }
                    else
                    {
                        //if its 1 (odd) XOR it with the given feedback number
                        initialValue = (initialValue >>> 1) ^ feedbackpolynomial;
                    }
            }

            //set key stream to the lower 8 bits of the stepped initial value
            byte keystream = (byte) (initialValue & 0xFF);

            //XOR data with key stream 
            result[i] = (byte) (data[i] ^ keystream);
        }

        return result;
    }
}