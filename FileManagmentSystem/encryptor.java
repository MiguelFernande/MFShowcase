import java.util.*;

public class encryptor {

    private static long feedbackpolynomial = 0x87654321L;

    public static void main(String args[]) 
    {
        //input
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Insert Data:");
        String dataInput = scanner.nextLine();

        System.out.print("Insert Initial Value:");
        String initialValInput = scanner.nextLine();

        scanner.close();

        byte[] dataArray = new byte[dataInput.length()];

        //check for hexadecimal and remove "0x"
        long initialValue;
        if (initialValInput.startsWith("0x") || initialValInput.startsWith("0X"))
        {
            initialValue = Long.parseUnsignedLong(initialValInput.substring(2),16);
        }
            else
            {
                System.out.println(("Initial Value is not a Hexadecimal"));
                return;
            }

        //check to see if data is in a hex formmat. did not got to in depth.
        //just went with a simple solution since it was mentioned that this code will be thrown out anyways.

        if (dataInput.matches("(\\\\x[0-9A-Fa-f]{2})+"))
        {
            dataArray = hexStringToByteArray(dataInput);
        }
            else
            {
                for (int i = 0; i < dataInput.length(); i++)
                {
                    dataArray[i] = (byte) dataInput.charAt(i);
                }            
            }

        byte[] processedData = Crypt(dataArray, initialValue);

        if (dataInput.matches("(\\\\x[0-9A-Fa-f]{2})+"))
        {
            System.out.println("Output: " + new String(processedData));
        }
            else
            {
                for (byte b : processedData) 
                {
                    System.out.print(String.format("\\x%02X", b & 0xFF));
                }
            }
    }

    static byte[] hexStringToByteArray(String s)
    {
        String cleanInput = s.replace("\\x", "");

        int length = cleanInput.length();

        byte[] data = new byte[length/2];

        for (int i = 0; i < length; i += 2)
        {
            int high = Character.digit(cleanInput.charAt(i), 16);
            int low = Character.digit(cleanInput.charAt(i+1), 16);
            data[i/2] = (byte) ((high << 4) + low);
        }

        return data;
    }

    // algorithm below

    static byte[] Crypt(byte[] data, long initialValue)
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