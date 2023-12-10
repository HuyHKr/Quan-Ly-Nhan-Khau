package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class NopTienTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static NopTien getNopTienSample1() {
        return new NopTien().id(1L).soTien(1L);
    }

    public static NopTien getNopTienSample2() {
        return new NopTien().id(2L).soTien(2L);
    }

    public static NopTien getNopTienRandomSampleGenerator() {
        return new NopTien().id(longCount.incrementAndGet()).soTien(longCount.incrementAndGet());
    }
}
