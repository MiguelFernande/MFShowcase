/*
Entry data type
 */
import java.io.*;
import java.util.*;

public class Entry
{
    private String name;
    private List<Block> blocks;

    public Entry(String name)
    {
        this.name = name;
        this.blocks = new ArrayList<Block>();
    }
    
    public void addBlock(Block blocks)
    {
        if (this.blocks == null)
        {
            this.blocks = new ArrayList<>();
        }
        this.blocks.add(blocks);
    }

    public String getName() 
    {
        return name;
    }

    public List<Block> getBlocks() 
    {
        return blocks;
    }

    //This method combines the entires to be properly displayed.
    public byte[] decryptEntryData() 
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        for (Block block : blocks)
        {
            try 
            {
                outputStream.write(block.getData());
            }
                catch (IOException e)
                {
                    throw new RuntimeException("Error writing Block data", e);
                }
        }

        byte[] decryptedData = outputStream.toByteArray();

        return decryptedData;
    }
}