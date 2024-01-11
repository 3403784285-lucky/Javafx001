package utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


/**
 * �ṩͨ��Ψһʶ���루universally unique identifier����UUID��ʵ��
 *
 */
public final class UUID implements java.io.Serializable, Comparable<UUID>
{
    private static final long serialVersionUID = -1185015143654744140L;

    /**
     * SecureRandom �ĵ���
     *
     */
    private static class Holder
    {
        static final SecureRandom numberGenerator = getSecureRandom();
    }

    /** ��UUID�����64��Чλ */
    private final long mostSigBits;

    /** ��UUID�����64��Чλ */
    private final long leastSigBits;

    /**
     * ˽�й���
     *
     * @param data ����
     */
    private UUID(byte[] data)
    {
        long msb = 0;
        long lsb = 0;
        assert data.length == 16 : "data must be 16 bytes in length";
        for (int i = 0; i < 8; i++)
        {
            msb = (msb << 8) | (data[i] & 0xff);
        }
        for (int i = 8; i < 16; i++)
        {
            lsb = (lsb << 8) | (data[i] & 0xff);
        }
        this.mostSigBits = msb;
        this.leastSigBits = lsb;
    }

    /**
     * ʹ��ָ�������ݹ����µ� UUID��
     *
     * @param mostSigBits ���� {@code UUID} �������Ч 64 λ
     * @param leastSigBits ���� {@code UUID} �������Ч 64 λ
     */
    public UUID(long mostSigBits, long leastSigBits)
    {
        this.mostSigBits = mostSigBits;
        this.leastSigBits = leastSigBits;
    }

    /**
     * ��ȡ���� 4��α������ɵģ�UUID �ľ�̬������ ʹ�ü��ܵı����߳�α��������������ɸ� UUID��
     *
     * @return ������ɵ� {@code UUID}
     */
    public static UUID fastUUID()
    {
        return randomUUID(false);
    }

    /**
     * ��ȡ���� 4��α������ɵģ�UUID �ľ�̬������ ʹ�ü��ܵ�ǿα��������������ɸ� UUID��
     *
     * @return ������ɵ� {@code UUID}
     */
    public static UUID randomUUID()
    {
        return randomUUID(true);
    }

    /**
     * ��ȡ���� 4��α������ɵģ�UUID �ľ�̬������ ʹ�ü��ܵ�ǿα��������������ɸ� UUID��
     *
     * @param isSecure �Ƿ�ʹ��{@link SecureRandom}����ǿ��Ի�ø���ȫ������룬������Եõ����õ�����
     * @return ������ɵ� {@code UUID}
     */
    public static UUID randomUUID(boolean isSecure)
    {
        final Random ng = isSecure ? Holder.numberGenerator : getRandom();

        byte[] randomBytes = new byte[16];
        ng.nextBytes(randomBytes);
        randomBytes[6] &= 0x0f; /* clear version */
        randomBytes[6] |= 0x40; /* set to version 4 */
        randomBytes[8] &= 0x3f; /* clear variant */
        randomBytes[8] |= 0x80; /* set to IETF variant */
        return new UUID(randomBytes);
    }

    /**
     * ����ָ�����ֽ������ȡ���� 3���������Ƶģ�UUID �ľ�̬������
     *
     * @param name ���ڹ��� UUID ���ֽ����顣
     *
     * @return ����ָ���������ɵ� {@code UUID}
     */
    public static UUID nameUUIDFromBytes(byte[] name)
    {
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException nsae)
        {
            throw new InternalError("MD5 not supported");
        }
        byte[] md5Bytes = md.digest(name);
        md5Bytes[6] &= 0x0f; /* clear version */
        md5Bytes[6] |= 0x30; /* set to version 3 */
        md5Bytes[8] &= 0x3f; /* clear variant */
        md5Bytes[8] |= 0x80; /* set to IETF variant */
        return new UUID(md5Bytes);
    }

    /**
     * ���� {@link #toString()} �������������ַ�����׼��ʾ��ʽ����{@code UUID}��
     *
     * @param name ָ�� {@code UUID} �ַ���
     * @return ����ָ��ֵ�� {@code UUID}
     * @throws IllegalArgumentException ��� name �� {@link #toString} ���������ַ�����ʾ��ʽ�����׳����쳣
     *
     */
    public static UUID fromString(String name)
    {
        String[] components = name.split("-");
        if (components.length != 5)
        {
            throw new IllegalArgumentException("Invalid UUID string: " + name);
        }
        for (int i = 0; i < 5; i++)
        {
            components[i] = "0x" + components[i];
        }

        long mostSigBits = Long.decode(components[0]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(components[1]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(components[2]).longValue();

        long leastSigBits = Long.decode(components[3]).longValue();
        leastSigBits <<= 48;
        leastSigBits |= Long.decode(components[4]).longValue();

        return new UUID(mostSigBits, leastSigBits);
    }

    /**
     * ���ش� UUID �� 128 λֵ�е������Ч 64 λ��
     *
     * @return �� UUID �� 128 λֵ�е������Ч 64 λ��
     */
    public long getLeastSignificantBits()
    {
        return leastSigBits;
    }

    /**
     * ���ش� UUID �� 128 λֵ�е������Ч 64 λ��
     *
     * @return �� UUID �� 128 λֵ�������Ч 64 λ��
     */
    public long getMostSignificantBits()
    {
        return mostSigBits;
    }


    public int version()
    {
        // Version is bits masked by 0x000000000000F000 in MS long
        return (int) ((mostSigBits >> 12) & 0x0f);
    }


    public int variant()
    {
        // This field is composed of a varying number of bits.
        // 0 - - Reserved for NCS backward compatibility
        // 1 0 - The IETF aka Leach-Salz variant (used by this class)
        // 1 1 0 Reserved, Microsoft backward compatibility
        // 1 1 1 Reserved for future definition.
        return (int) ((leastSigBits >>> (64 - (leastSigBits >>> 62))) & (leastSigBits >> 63));
    }
    /**
     * ��� UUID �������ʱ���ֵ��
     *
     * <p>
     * 60 λ��ʱ���ֵ���ݴ� {@code UUID} �� time_low��time_mid �� time_hi �ֶι��졣<br>
     * ���õ���ʱ����� 100 ��΢��Ϊ��λ���� UTC��ͨ��Э��ʱ�䣩 1582 �� 10 �� 15 ����ʱ��ʼ��
     *
     * <p>
     * ʱ���ֵ�����ڻ���ʱ��� UUID���� version ����Ϊ 1���в������塣<br>
     * ����� {@code UUID} ���ǻ���ʱ��� UUID����˷����׳� UnsupportedOperationException��
     *
     * @throws UnsupportedOperationException ����� {@code UUID} ���� version Ϊ 1 �� UUID��
     */
    public long timestamp() throws UnsupportedOperationException
    {
        checkTimeBase();
        return (mostSigBits & 0x0FFFL) << 48//
                | ((mostSigBits >> 16) & 0x0FFFFL) << 32//
                | mostSigBits >>> 32;
    }
    /**
     * ��� UUID �������ʱ������ֵ��
     *
     * <p>
     * 14 λ��ʱ������ֵ���ݴ� UUID �� clock_seq �ֶι��졣clock_seq �ֶ����ڱ�֤�ڻ���ʱ��� UUID �е�ʱ��Ψһ�ԡ�
     * <p>
     * {@code clockSequence} ֵ���ڻ���ʱ��� UUID���� version ����Ϊ 1���в������塣 ����� UUID ���ǻ���ʱ��� UUID����˷����׳�
     * UnsupportedOperationException��
     *
     * @return �� {@code UUID} ��ʱ������
     *
     * @throws UnsupportedOperationException ����� UUID �� version ��Ϊ 1
     */
    public int clockSequence() throws UnsupportedOperationException
    {
        checkTimeBase();
        return (int) ((leastSigBits & 0x3FFF000000000000L) >>> 48);
    }
    /**
     * ��� UUID ��صĽڵ�ֵ��
     *
     * <p>
     * 48 λ�Ľڵ�ֵ���ݴ� UUID �� node �ֶι��졣���ֶ�ּ�����ڱ�������� IEEE 802 ��ַ���õ�ַ�������ɴ� UUID �Ա�֤�ռ�Ψһ�ԡ�
     * <p>
     * �ڵ�ֵ���ڻ���ʱ��� UUID���� version ����Ϊ 1���в������塣<br>
     * ����� UUID ���ǻ���ʱ��� UUID����˷����׳� UnsupportedOperationException��
     *
     * @return �� {@code UUID} �Ľڵ�ֵ
     *
     * @throws UnsupportedOperationException ����� UUID �� version ��Ϊ 1
     */
    public long node() throws UnsupportedOperationException
    {
        checkTimeBase();
        return leastSigBits & 0x0000FFFFFFFFFFFFL;
    }

    @Override
    public String toString()
    {
        return toString(false);
    }

    public String toString(boolean isSimple)
    {
        final StringBuilder builder = new StringBuilder(isSimple ? 32 : 36);
        // time_low
        builder.append(digits(mostSigBits >> 32, 8));
        if (false == isSimple)
        {
            builder.append('-');
        }
        // time_mid
        builder.append(digits(mostSigBits >> 16, 4));
        if (false == isSimple)
        {
            builder.append('-');
        }
        // time_high_and_version
        builder.append(digits(mostSigBits, 4));
        if (false == isSimple)
        {
            builder.append('-');
        }
        // variant_and_sequence
        builder.append(digits(leastSigBits >> 48, 4));
        if (false == isSimple)
        {
            builder.append('-');
        }
        // node
        builder.append(digits(leastSigBits, 12));
        return builder.toString();
    }
    /**
     * ���ش� UUID �Ĺ�ϣ�롣
     *
     * @return UUID �Ĺ�ϣ��ֵ��
     */
    @Override
    public int hashCode()
    {
        long hilo = mostSigBits ^ leastSigBits;
        return ((int) (hilo >> 32)) ^ (int) hilo;
    }
    /**
     * ���˶�����ָ������Ƚϡ�
     * <p>
     * ���ҽ���������Ϊ {@code null}������һ�� UUID ���󡢾������ UUID ��ͬ�� varriant��������ͬ��ֵ��ÿһλ����ͬ��ʱ�������Ϊ {@code true}��
     *
     * @param obj Ҫ��֮�ȽϵĶ���
     *
     * @return ���������ͬ���򷵻� {@code true}�����򷵻� {@code false}
     */
    @Override
    public boolean equals(Object obj)
    {
        if ((null == obj) || (obj.getClass() != UUID.class))
        {
            return false;
        }
        UUID id = (UUID) obj;
        return (mostSigBits == id.mostSigBits && leastSigBits == id.leastSigBits);
    }
    // Comparison Operations
    /**
     * ���� UUID ��ָ���� UUID �Ƚϡ�
     *
     * <p>
     * ������� UUID ��ͬ���ҵ�һ�� UUID �������Ч�ֶδ��ڵڶ��� UUID �Ķ�Ӧ�ֶΣ����һ�� UUID ���ڵڶ��� UUID��
     *
     * @param val ��� UUID �Ƚϵ� UUID
     *
     * @return �ڴ� UUID С�ڡ����ڻ���� val ʱ���ֱ𷵻� -1��0 �� 1��
     *
     */
    @Override
    public int compareTo(UUID val)
    {
        // The ordering is intentionally set up so that the UUIDs
        // can simply be numerically compared as two numbers
        return (this.mostSigBits < val.mostSigBits ? -1 : //
                (this.mostSigBits > val.mostSigBits ? 1 : //
                        (this.leastSigBits < val.leastSigBits ? -1 : //
                                (this.leastSigBits > val.leastSigBits ? 1 : //
                                        0))));
    }
    // -------------------------------------------------------------------------------------------------------------------
    // Private method start
    /**
     * ����ָ�����ֶ�Ӧ��hexֵ
     *
     * @param val ֵ
     * @param digits λ
     * @return ֵ
     */
    private static String digits(long val, int digits)
    {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }
    /**
     * ����Ƿ�Ϊtime-based�汾UUID
     */
    private void checkTimeBase()
    {
        if (version() != 1)
        {
            throw new UnsupportedOperationException("Not a time-based UUID");
        }
    }
    /**
     * ��ȡ{@link SecureRandom}�����ṩ���ܵ�ǿ����������� (RNG)
     *
     * @return {@link SecureRandom}
     */
    public static SecureRandom getSecureRandom()
    {
        try
        {
            return SecureRandom.getInstance("SHA1PRNG");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new UtilException(e);
        }
    }
    /**
     * ��ȡ���������������<br>
     * ThreadLocalRandom��JDK 7֮���ṩ����������������ܹ��������̷߳����ľ������ᡣ
     *
     * @return {@link ThreadLocalRandom}
     */
    public static ThreadLocalRandom getRandom()
    {
        return ThreadLocalRandom.current();
    }
}