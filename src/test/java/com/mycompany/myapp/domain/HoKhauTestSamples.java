package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HoKhauTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static HoKhau getHoKhauSample1() {
        return new HoKhau().id(1L).maHoKhau("maHoKhau1").tenChuHo("tenChuHo1").diaChi("diaChi1");
    }

    public static HoKhau getHoKhauSample2() {
        return new HoKhau().id(2L).maHoKhau("maHoKhau2").tenChuHo("tenChuHo2").diaChi("diaChi2");
    }

    public static HoKhau getHoKhauRandomSampleGenerator() {
        return new HoKhau()
            .id(longCount.incrementAndGet())
            .maHoKhau(UUID.randomUUID().toString())
            .tenChuHo(UUID.randomUUID().toString())
            .diaChi(UUID.randomUUID().toString());
    }
}
