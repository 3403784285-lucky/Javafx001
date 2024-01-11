package common;

import java.io.Serializable;

public class FileData implements Serializable {

    private int number;
    private byte[] data;

    public FileData(int number, byte[] data) {
        this.number = number;
        this.data = data;
    }

    public int getNumber() {
        return number;
    }

    public byte[] getData() {
        return data;
    }

}
