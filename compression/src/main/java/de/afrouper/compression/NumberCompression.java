package de.afrouper.compression;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class NumberCompression {

    private static final byte BYTES_SHORT = 2;
    private static final byte BYTES_INTEGER = 4;

    private final Base64.Encoder encoder = Base64.getEncoder();
    private final Base64.Decoder decoder = Base64.getDecoder();

    public String compressShort(short[] numbers) {
        byte[] bytes = new byte[numbers.length * BYTES_SHORT];
        for (int numberIndex = 0, byteIndex = 0; numberIndex < numbers.length; numberIndex++, byteIndex = byteIndex + BYTES_SHORT) {
            bytes[byteIndex] = (byte)(numbers[numberIndex] >> 8);
            bytes[byteIndex+1] = (byte)numbers[numberIndex];
        }
        return encoder.encodeToString(bytes);
    }

    public String compressInteger(int[] numbers) {
        byte[] bytes = new byte[numbers.length * BYTES_INTEGER];
        for (int numberIndex = 0, byteIndex = 0; numberIndex < numbers.length; numberIndex++, byteIndex = byteIndex + BYTES_INTEGER) {
            bytes[byteIndex] = (byte)(numbers[numberIndex] >> 24);
            bytes[byteIndex+1] = (byte)(numbers[numberIndex] >> 16);
            bytes[byteIndex+2] = (byte)(numbers[numberIndex] >> 8);
            bytes[byteIndex+3] = (byte)numbers[numberIndex];
        }
        return encoder.encodeToString(bytes);
    }

    public short[] uncompressShort(String data) {
        byte[] bytes = decoder.decode(data.getBytes(StandardCharsets.UTF_8));
        short[] numbers = new short[bytes.length / BYTES_SHORT];
        for (int numberIndex = 0, byteIndex = 0; numberIndex < numbers.length; numberIndex++, byteIndex = byteIndex + BYTES_SHORT) {
            numbers[numberIndex] = (short) (((bytes[byteIndex] & 0xFF) << 8 ) | (bytes[byteIndex+1] & 0xFF) );
        }
        return numbers;
    }

    public int[] uncompressInteger(String data) {
        byte[] bytes = decoder.decode(data.getBytes(StandardCharsets.UTF_8));
        int[] numbers = new int[bytes.length / BYTES_INTEGER];
        for (int numberIndex = 0, byteIndex = 0; numberIndex < numbers.length; numberIndex++, byteIndex = byteIndex + BYTES_INTEGER) {
            numbers[numberIndex] = (
                    ((bytes[byteIndex] & 0xFF) << 24 ) |
                    ((bytes[byteIndex+1] & 0xFF) << 16 ) |
                    ((bytes[byteIndex+2] & 0xFF) << 8 ) |
                    (bytes[byteIndex+3] & 0xFF));
        }
        return numbers;
    }
}
