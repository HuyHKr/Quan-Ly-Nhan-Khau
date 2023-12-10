package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.HoKhauTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HoKhauTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HoKhau.class);
        HoKhau hoKhau1 = getHoKhauSample1();
        HoKhau hoKhau2 = new HoKhau();
        assertThat(hoKhau1).isNotEqualTo(hoKhau2);

        hoKhau2.setId(hoKhau1.getId());
        assertThat(hoKhau1).isEqualTo(hoKhau2);

        hoKhau2 = getHoKhauSample2();
        assertThat(hoKhau1).isNotEqualTo(hoKhau2);
    }
}
