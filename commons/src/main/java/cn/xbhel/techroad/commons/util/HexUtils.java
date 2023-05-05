package cn.xbhel.techroad.commons.util;

/**
 * 16 进制编码和解码
 * @author xbhel
 */
public final class HexUtils {

    private static final byte[] HEX_CHARS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private HexUtils() {
    }

    public static byte[] encode(byte[] data) {
        // 初始化容量为 2 倍 (1 byte = 2 hex)
        var out = new byte[data.length << 1];
        for (int i = 0, cursor = 0; i < data.length; i++) {
            // 高四位 (& 0xf 的作用就高位清 0)；hex >>> 4 运算时 java 会把 byte 转成了 int(占 4 byte)
            //以二进制形式看 byte，在 byte 的首位为 1 的时候 (即负数时)，转换过程中前面会多出三个 0xff
            //进行 &0xff可以在这种情况下去掉前面多余的 0xff，所以我们把 byte & 0xff
            out[cursor++] = HEX_CHARS[(data[i] >>> 4) & 0xf];
            // 低四位
            out[cursor++] = HEX_CHARS[data[i] & 0xf];
        }
        return out;
    }

    public static byte[] decode(byte[] data) {
        var out = new byte[data.length >> 1];
        // 另一种简单处理方式：还原高、低半字节 int byte = Integer.parseInt(hexString.substring(i, i + 2), 16)
        for (int i = 0, cursor = 0; i < out.length; i++) {
            // 还原高四位
            int high = toDigit(data[cursor], cursor) << 4;
            cursor++;
            // 还原低四位
            int low = toDigit(data[cursor], cursor);
            cursor++;
            out[i] = (byte) ((high | low) & 0xff);
        }
        return out;
    }

    public static boolean isHexSequence(byte[] hexSequence) {
        // 序列的长度为奇数，要求 16 进制成对出现
        // 另一种处理方式：不要求成对出现，如果序列为奇数，则 decode 时在开头补 ‘0’.
        // 但要保持整个 api 风格一致，既允许奇数 decode 就补 ‘0’，否则奇数长度就不算 16进制序列
        if ((hexSequence.length & 0x01) != 0) {
            return false;
        }
        for (var hex : hexSequence) {
            if(Character.digit((char) hex, 16) == -1) {
                return false;
            }
        }
        return true;
    }

    private static int toDigit(byte ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new IllegalArgumentException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }
}
