package zhuj.java.secure;

public class HexString {
    private static final char[] HEX_DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] HEX_DIGITS_UPPER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * Returns this byte string encoded in hexadecimal.
     */
    public String hex(byte[] bytes) {
        char[] result = new char[bytes.length * 2];
        int c = 0;
        for (byte b : bytes) {
            result[c++] = HEX_DIGITS[(b >> 4) & 0xf];
            result[c++] = HEX_DIGITS[b & 0xf];
        }
        return new String(result);
    }

    /**
     * Returns this byte string encoded in hexadecimal.
     */
    public String hexUpper(byte[] bytes) {
        char[] result = new char[bytes.length * 2];
        int c = 0;
        for (byte b : bytes) {
            result[c++] = HEX_DIGITS_UPPER[(b >> 4) & 0xf];
            result[c++] = HEX_DIGITS_UPPER[b & 0xf];
        }
        return new String(result);
    }

    /**
     * Decodes the hex-encoded bytes and returns their value a byte string.
     */
    public static byte[] decodeHex(String hex) {
        if (hex == null) throw new IllegalArgumentException("hex == null");
        if (hex.length() % 2 != 0)
            throw new IllegalArgumentException("Unexpected hex string: " + hex);

        byte[] result = new byte[hex.length() / 2];
        for (int i = 0; i < result.length; i++) {
            int d1 = decodeHexDigit(hex.charAt(i * 2)) << 4;
            int d2 = decodeHexDigit(hex.charAt(i * 2 + 1));
            result[i] = (byte) (d1 + d2);
        }
        return result;
    }

    private static int decodeHexDigit(char c) {
        if (c >= '0' && c <= '9') return c - '0';
        if (c >= 'a' && c <= 'f') return c - 'a' + 10;
        if (c >= 'A' && c <= 'F') return c - 'A' + 10;
        throw new IllegalArgumentException("Unexpected hex digit: " + c);
    }
}
