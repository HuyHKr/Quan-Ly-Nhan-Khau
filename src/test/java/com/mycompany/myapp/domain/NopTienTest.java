package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.HoKhauTestSamples.*;
import static com.mycompany.myapp.domain.KhoanThuTestSamples.*;
import static com.mycompany.myapp.domain.NopTienTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NopTienTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NopTien.class);
        NopTien nopTien1 = getNopTienSample1();
        NopTien nopTien2 = new NopTien();
        assertThat(nopTien1).isNotEqualTo(nopTien2);

        nopTien2.setId(nopTien1.getId());
        assertThat(nopTien1).isEqualTo(nopTien2);

        nopTien2 = getNopTienSample2();
        assertThat(nopTien1).isNotEqualTo(nopTien2);
    }

    @Test
    void hoKhauTest() throws Exception {
        NopTien nopTien = getNopTienRandomSampleGenerator();
        HoKhau hoKhauBack = getHoKhauRandomSampleGenerator();

        nopTien.setHoKhau(hoKhauBack);
        assertThat(nopTien.getHoKhau()).isEqualTo(hoKhauBack);

        nopTien.hoKhau(null);
        assertThat(nopTien.getHoKhau()).isNull();
    }

    @Test
    void khoanThuTest() throws Exception {
        NopTien nopTien = getNopTienRandomSampleGenerator();
        KhoanThu khoanThuBack = getKhoanThuRandomSampleGenerator();

        nopTien.setKhoanThu(khoanThuBack);
        assertThat(nopTien.getKhoanThu()).isEqualTo(khoanThuBack);

        nopTien.khoanThu(null);
        assertThat(nopTien.getKhoanThu()).isNull();
    }
}
