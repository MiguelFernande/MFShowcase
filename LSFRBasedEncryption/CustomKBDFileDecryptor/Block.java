//Block data type

public class Block 
{
    private byte[] data;
    private int size;

    public Block(byte[] data)
    {
        this.data = data;
        this.size = data.length;
    }

    public byte[] getData()
    {
        return this.data;
    }

    public int getSize()
    {
        return this.size;
    }
}
