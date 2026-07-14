package de.afrouper.compression;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.BitSet;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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

    public String compressBits(int[] numbers) {
        BitSet set = new BitSet();
        for (int number : numbers) {
            set.set(number);
        }
        return encoder.encodeToString(compress(set.toByteArray()));
    }

    public String compress(int[] nums) {
        if (nums == null || nums.length == 0) {
            return "";
        }

        // 1. Array klonen (verhindert Side-Effects) und sortieren
        int[] sortedNums = nums.clone();
        Arrays.sort(sortedNums);

        // 2. ByteBuffer allokieren (2 Bytes pro Zahl)
        ByteBuffer buffer = ByteBuffer.allocate(sortedNums.length * 2);

        // 3. Deltas berechnen und als 16-Bit (short) schreiben
        int prev = 0;
        for (int num : sortedNums) {
            short delta = (short) (num - prev);
            buffer.putShort(delta);
            prev = num;
        }

        // 4. Als Base64 kodieren
        return Base64.getEncoder().encodeToString(buffer.array());
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

    public int[] umcompressBits(String data) {
        byte[] bytes = decoder.decode(data.getBytes(StandardCharsets.UTF_8));
        bytes = uncompress(bytes);
        BitSet bitSet = BitSet.valueOf(bytes);
        return bitSet.stream().toArray();
    }

    public int[] uncompress(String base64Str) {
        if (base64Str == null || base64Str.trim().isEmpty()) {
            return new int[0];
        }

        // 1. Base64 zu Byte-Array dekodieren
        byte[] bytes = Base64.getDecoder().decode(base64Str);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        // 2. Ziel-Array initialisieren (Ein Short = 2 Bytes, also Länge / 2)
        int[] result = new int[bytes.length / 2];

        // 3. Deltas auflösen und Array befüllen
        int current = 0;
        for (int i = 0; i < result.length; i++) {
            current += buffer.getShort();
            result[i] = current;
        }

        return result;
    }

    private byte[] compress(byte[] bytes) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(baos);
            gzipOutputStream.write(bytes);
            gzipOutputStream.close();
            return baos.toByteArray();
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("Could not compress bytes", ex);
        }
    }

    private byte[] uncompress(byte[] bytes) {
        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(bytes));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[128];
            int read;
            while ((read = gzipInputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, read);
            }
            baos.close();
            return baos.toByteArray();
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("Could not uncompress bytes", ex);
        }
    }
}
