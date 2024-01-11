package common;

import java.io.Serializable;

/**
 * @author 张培灵
 *
 * 用于文件的传输类型
 */
public class FileData implements Serializable {

    /**
     *文件id，默认生成
     */
    private int number;
    /**
     *文件内容，用字节传输
     */
    private byte[] data;

    /**
     * @param number
     * @param data
     * 初始化方法
     */
    public FileData(int number, byte[] data) {
        this.number = number;
        this.data = data;
    }

    /**
     * @return int
     * 获取id
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return {@link byte[]}
     * 获取文件内容
     */
    public byte[] getData() {
        return data;
    }

}
