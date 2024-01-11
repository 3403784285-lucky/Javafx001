package utils;
public class SnowFlakeId {
    /** ��ʼʱ��� (������Լ�ҵ��ϵͳ���ߵ�ʱ��) */
    private final long twepoch = 1575365018000L;

    /** ����id��ռ��λ�� */
    private final long workerIdBits = 10L;

    /** ֧�ֵ�������id�������31 (�����λ�㷨���Ժܿ�ļ������λ�����������ܱ�ʾ�����ʮ������) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** ������id��ռ��λ�� */
    private final long sequenceBits = 12L;

    /** ����ID������12λ */
    private final long workerIdShift = sequenceBits;

    /** ʱ���������22λ(10+12) */
    private final long timestampLeftShift = sequenceBits + workerIdBits;

    /** �������е����룬����Ϊ4095 (0b111111111111=0xfff=4095) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** ��������ID(0~1024) */
    private long workerId;

    /** ����������(0~4095) */
    private long sequence = 0L;

    /** �ϴ�����ID��ʱ��� */
    private long lastTimestamp = -1L;

    //==============================Constructors=====================================
    /**
     * ���캯��
     * @param workerId ����ID (0~1024)
     */
    public SnowFlakeId(long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("workerId can't be greater than %d or less than 0", maxWorkerId));
        }
        this.workerId = workerId;
    }

    // ==============================Methods==========================================
    /**
     * �����һ��ID (�÷������̰߳�ȫ��)
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //�����ǰʱ��С����һ��ID���ɵ�ʱ�����˵��ϵͳʱ�ӻ��˹����ʱ��Ӧ���׳��쳣
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //�����ͬһʱ�����ɵģ�����к���������
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //�������������
            if (sequence == 0) {
                //��������һ������,����µ�ʱ���
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //ʱ����ı䣬��������������
        else {
            sequence = 0L;
        }

        //�ϴ�����ID��ʱ���
        lastTimestamp = timestamp;

        //��λ��ͨ��������ƴ��һ�����64λ��ID
        return ((timestamp - twepoch) << timestampLeftShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }

    /**
     * ��������һ�����룬ֱ������µ�ʱ���
     * @param lastTimestamp �ϴ�����ID��ʱ���
     * @return ��ǰʱ���
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * �����Ժ���Ϊ��λ�ĵ�ǰʱ��
     * @return ��ǰʱ��(����)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
