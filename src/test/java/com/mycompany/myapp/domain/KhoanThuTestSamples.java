package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KhoanThuTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static KhoanThu getKhoanThuSample1() {
        return new KhoanThu().id(1L).maKhoanThu("maKhoanThu1").tenKhoanThu("tenKhoanThu1").donGia("donGia1");
    }

    public static KhoanThu getKhoanThuSample2() {
        return new KhoanThu().id(2L).maKhoanThu("maKhoanThu2").tenKhoanThu("tenKhoanThu2").donGia("donGia2");
    }

    public static KhoanThu getKhoanThuRandomSampleGenerator() {
        return new KhoanThu()
            .id(longCount.incrementAndGet())
            .maKhoanThu(UUID.randomUUID().toString())
            .tenKhoanThu(UUID.randomUUID().toString())
            .donGia(UUID.randomUUID().toString());
    }
}
