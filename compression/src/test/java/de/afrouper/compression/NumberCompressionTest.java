package de.afrouper.compression;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class NumberCompressionTest {

    private static final short[] NUMBERS_SHORT = new short[]{4711,815,88,1,9999,8000,7514,6941,1254,-500,0};
    private static final int[] NUMBERS_INTEGER = new int[]{4711,815,88,1,9999,8000,7514,6941,1254, 326547,-500,0};
    private static int[] NUMBERS_INTEGER_POSITIVE = new int[]{4711,815,88,1,9999,8000,7514,6941,1254, 9876,0};

    private final NumberCompression numberCompression = new NumberCompression();

    @Test
    void testCompressionCycleShort() {
        String compressed = numberCompression.compressShort(NUMBERS_SHORT);
        assertNotNull(compressed);
        short[] uncompressed = numberCompression.uncompressShort(compressed);
        assertArrayEquals(NUMBERS_SHORT, uncompressed);
    }

    @Test
    void testCompressionCycleInteger() {
        String compressed = numberCompression.compressInteger(NUMBERS_INTEGER);
        assertNotNull(compressed);
        int[] uncompressed = numberCompression.uncompressInteger(compressed);
        assertArrayEquals(NUMBERS_INTEGER, uncompressed);
    }

    @Test
    void testCompressionCycleBits() {
        String compressed = numberCompression.compressBits(NUMBERS_INTEGER_POSITIVE);
        assertNotNull(compressed);
        int[] ints = numberCompression.umcompressBits(compressed);
        Arrays.sort(NUMBERS_INTEGER_POSITIVE);
        assertArrayEquals(NUMBERS_INTEGER_POSITIVE, ints);
    }

    @Test
    void testPerformance() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            testCompressionCycleInteger();
        }
        System.out.println("Integer Packing: " + (System.currentTimeMillis() - start) + " ms.");

        start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            testCompressionCycleShort();
        }
        System.out.println("Short Packing: " + (System.currentTimeMillis() - start) + " ms.");

        start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            testCompressionCycleBits();
        }
        System.out.println("Bits Packing: " + (System.currentTimeMillis() - start) + " ms.");
    }
}