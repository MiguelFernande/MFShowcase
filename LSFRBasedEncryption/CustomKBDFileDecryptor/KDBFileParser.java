//This code is Responsible for parsing the KBD file.

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

class KDBFileParser 
{

    private String filePath;
    private RandomAccessFile file;

    public KDBFileParser(String filePath)  
    {
        this.filePath = filePath;
    }

    public List<Entry> parse() throws InvalidFormatException, FileNotFoundException, IOException
    {
        openFile();
        
        if (!verifyMagicBytes())  
        {
            throw new InvalidFormatException("Invalid magic bytes");
        }

        List<Entry> entries = parseEntryList();

        closeFile();

        return entries;
    }

    private void openFile() throws FileNotFoundException 
    {
        file = new RandomAccessFile(new File(filePath), "r");
    }

    private boolean verifyMagicBytes() throws IOException
    {
        final String expectedMagic = "CT2018";

        byte[] magicBytes = new byte[expectedMagic.length()];

        file.seek(0);
        file.readFully(magicBytes);

        String magicString = new String(magicBytes);

        return expectedMagic.equals(magicString);
    }

    /*
    This method will read through the list of entries. 
    For each entry it will call parse block list method.
    Those blocks are then added to the now named entry. 
    After the sentinal is found it then returns all entries.
    */

    private List<Entry> parseEntryList() throws IOException
    {
        List<Entry> entries = new ArrayList<>();

        file.seek(6);

        int pointerToEntryList = file.readInt();
        pointerToEntryList = Integer.reverseBytes(pointerToEntryList);

        file.seek(pointerToEntryList);

        while (true)
        {
            long entryStartPos = file.getFilePointer();

            int sentinalCheck = file.readInt();
            if (sentinalCheck == -1)
            {
                break;
            }

            file.seek(entryStartPos);

            String entryName = readNullTerminatedString(16);

            int blockListPointer = file.readInt();
            blockListPointer = Integer.reverseBytes(blockListPointer);

            Entry entry = new Entry(entryName);

            List<Block> blocks = parseBlockList(blockListPointer);

            for (Block block : blocks) 
            {
                entry.addBlock(block);
            }
            entries.add(entry);

            long positionAfterEntry  = entryStartPos + 20;
            file.seek(positionAfterEntry);
        }
        return entries;
    }

    /*
    This method is responsible for parsing a block list. 
    Reading the size as a Short and the data pointer as an Int.
    The data is then read to the array "blockData".
    The Crypt method from challenge 1 is called here with "blockData".
    */

    private List<Block> parseBlockList(long pointerToBlockList) throws IOException 
    {
        List<Block> blocks = new ArrayList<>();

        file.seek(pointerToBlockList);

        while (true)
        {
            long blockStartPos = file.getFilePointer();

            int sentinalCheck = file.readInt();
            if (sentinalCheck == -1)
            {
                break;
            }

            file.seek(blockStartPos);

            short blockSizeShort = file.readShort();
            int blockSize = Short.toUnsignedInt(Short.reverseBytes(blockSizeShort));

            int dataPointerInt = file.readInt();
            dataPointerInt = Integer.reverseBytes(dataPointerInt);

            byte[] blockData = new byte[blockSize];
            file.seek(dataPointerInt);
            file.readFully(blockData);

            blockData = BinaryDecryptor.Crypt(blockData, BinaryDecryptor.initialValue);

            Block block = new Block(blockData);
            blocks.add(block);

            long positionAfterBlock = blockStartPos + 6;
            file.seek(positionAfterBlock);
        }
        return blocks;
    }

    /*
    Responsible for reading the name. of the entry.
     */
    
    private String readNullTerminatedString(int maxLength) throws IOException
    {
        byte[] bytes = new byte[maxLength];
        file.readFully(bytes);

        int i = 0;
        while (i < bytes.length && bytes[i] != 0)
        {
            i++;
        }

        return new String(bytes, 0, i, StandardCharsets.US_ASCII);
    }

    private void closeFile() throws IOException
    {
        if (file != null) 
        {
            file.close();
        }    
    }
}