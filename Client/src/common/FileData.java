package common;

import java.io.Serializable;

/**
 * @author ������
 *
 * �����ļ��Ĵ�������
 */
public class FileData implements Serializable {

    /**
     *�ļ�id��Ĭ������
     */
    private int number;
    /**
     *�ļ����ݣ����ֽڴ���
     */
    private byte[] data;

    /**
     * @param number
     * @param data
     * ��ʼ������
     */
    public FileData(int number, byte[] data) {
        this.number = number;
        this.data = data;
    }

    /**
     * @return int
     * ��ȡid
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return {@link byte[]}
     * ��ȡ�ļ�����
     */
    public byte[] getData() {
        return data;
    }

}
