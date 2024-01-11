package utils;
/**
 * ID������������
 */
public class IdUtils
{
    /**
     * ��ȡ���UUID
     *
     * @return ���UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * �򻯵�UUID��ȥ���˺���
     *
     * @return �򻯵�UUID��ȥ���˺���
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString(true);
    }

    /**
     * ��ȡ���UUID��ʹ�����ܸ��õ�ThreadLocalRandom����UUID
     *
     * @return ���UUID
     */
    public static String fastUUID()
    {
        return UUID.fastUUID().toString();
    }

    /**
     * �򻯵�UUID��ȥ���˺��ߣ�ʹ�����ܸ��õ�ThreadLocalRandom����UUID
     *
     * @return �򻯵�UUID��ȥ���˺���
     */
    public static String fastSimpleUUID()
    {
        return UUID.fastUUID().toString(true);
    }
}