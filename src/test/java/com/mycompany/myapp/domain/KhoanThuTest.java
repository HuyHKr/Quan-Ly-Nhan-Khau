package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.KhoanThuTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KhoanThuTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KhoanThu.class);
        KhoanThu khoanThu1 = getKhoanThuSample1();
        KhoanThu khoanThu2 = new KhoanThu();
        assertThat(khoanThu1).isNotEqualTo(khoanThu2);

        khoanThu2.setId(khoanThu1.getId());
        assertThat(khoanThu1).isEqualTo(khoanThu2);

        khoanThu2 = getKhoanThuSample2();
        assertThat(khoanThu1).isNotEqualTo(khoanThu2);
    }
}
