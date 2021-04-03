package com.example.xhlang;

public class NumberUtil {

    public NumberUtil() {
    }

    public static final byte[] getBytes(short s, boolean asc) {
        byte[] buf = new byte[2];
        int i;
        if (asc) {
            for (i = buf.length - 1; i >= 0; --i) {
                buf[i] = (byte) (s & 255);
                s = (short) (s >> 8);
            }
        } else {
            for (i = 0; i < buf.length; ++i) {
                buf[i] = (byte) (s & 255);
                s = (short) (s >> 8);
            }
        }

        return buf;
    }

    public static final byte[] getBytes(int s, boolean asc) {
        byte[] buf = new byte[4];
        int i;
        if (asc) {
            for (i = buf.length - 1; i >= 0; --i) {
                buf[i] = (byte) (s & 255);
                s >>= 8;
            }
        } else {
            for (i = 0; i < buf.length; ++i) {
                buf[i] = (byte) (s & 255);
                s >>= 8;
            }
        }

        return buf;
    }

    public static final short getShort(byte[] buf, boolean asc) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        } else if (buf.length > 2) {
            throw new IllegalArgumentException("byte array size > 2 !");
        } else {
            short r = 0;
            int i;
            if (asc) {
                for (i = buf.length - 1; i >= 0; --i) {
                    r = (short) (r << 8);
                    r = (short) (r | buf[i] & 255);
                }
            } else {
                for (i = 0; i < buf.length; ++i) {
                    r = (short) (r << 8);
                    r = (short) (r | buf[i] & 255);
                }
            }

            return r;
        }
    }

    public static final int getInt(byte[] buf, boolean asc) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        } else if (buf.length > 4) {
            throw new IllegalArgumentException("byte array size > 4 !");
        } else {
            int r = 0;
            int i;
            if (asc) {
                for (i = buf.length - 1; i >= 0; --i) {
                    r <<= 8;
                    r |= buf[i] & 255;
                }
            } else {
                for (i = 0; i < buf.length; ++i) {
                    r <<= 8;
                    r |= buf[i] & 255;
                }
            }

            return r;
        }
    }
}
