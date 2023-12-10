package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.KhoanThu;
import com.mycompany.myapp.domain.enumeration.Loai;
import com.mycompany.myapp.repository.KhoanThuRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link KhoanThuResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class KhoanThuResourceIT {

    private static final String DEFAULT_MA_KHOAN_THU = "AAAAAAAAAA";
    private static final String UPDATED_MA_KHOAN_THU = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_KHOAN_THU = "AAAAAAAAAA";
    private static final String UPDATED_TEN_KHOAN_THU = "BBBBBBBBBB";

    private static final String DEFAULT_DON_GIA = "AAAAAAAAAA";
    private static final String UPDATED_DON_GIA = "BBBBBBBBBB";

    private static final Loai DEFAULT_LOAI = Loai.BAT_BUOC;
    private static final Loai UPDATED_LOAI = Loai.TU_NGUYEN;

    private static final String ENTITY_API_URL = "/api/khoan-thus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private KhoanThuRepository khoanThuRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKhoanThuMockMvc;

    private KhoanThu khoanThu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KhoanThu createEntity(EntityManager em) {
        KhoanThu khoanThu = new KhoanThu()
            .maKhoanThu(DEFAULT_MA_KHOAN_THU)
            .tenKhoanThu(DEFAULT_TEN_KHOAN_THU)
            .donGia(DEFAULT_DON_GIA)
            .loai(DEFAULT_LOAI);
        return khoanThu;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KhoanThu createUpdatedEntity(EntityManager em) {
        KhoanThu khoanThu = new KhoanThu()
            .maKhoanThu(UPDATED_MA_KHOAN_THU)
            .tenKhoanThu(UPDATED_TEN_KHOAN_THU)
            .donGia(UPDATED_DON_GIA)
            .loai(UPDATED_LOAI);
        return khoanThu;
    }

    @BeforeEach
    public void initTest() {
        khoanThu = createEntity(em);
    }

    @Test
    @Transactional
    void createKhoanThu() throws Exception {
        int databaseSizeBeforeCreate = khoanThuRepository.findAll().size();
        // Create the KhoanThu
        restKhoanThuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khoanThu)))
            .andExpect(status().isCreated());

        // Validate the KhoanThu in the database
        List<KhoanThu> khoanThuList = khoanThuRepository.findAll();
        assertThat(khoanThuList).hasSize(databaseSizeBeforeCreate + 1);
        KhoanThu testKhoanThu = khoanThuList.get(khoanThuList.size() - 1);
        assertThat(testKhoanThu.getMaKhoanThu()).isEqualTo(DEFAULT_MA_KHOAN_THU);
        assertThat(testKhoanThu.getTenKhoanThu()).isEqualTo(DEFAULT_TEN_KHOAN_THU);
        assertThat(testKhoanThu.getDonGia()).isEqualTo(DEFAULT_DON_GIA);
        assertThat(testKhoanThu.getLoai()).isEqualTo(DEFAULT_LOAI);
    }

    @Test
    @Transactional
    void createKhoanThuWithExistingId() throws Exception {
        // Create the KhoanThu with an existing ID
        khoanThu.setId(1L);

        int databaseSizeBeforeCreate = khoanThuRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restKhoanThuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khoanThu)))
            .andExpect(status().isBadRequest());

        // Validate the KhoanThu in the database
        List<KhoanThu> khoanThuList = khoanThuRepository.findAll();
        assertThat(khoanThuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMaKhoanThuIsRequired() throws Exception {
        int databaseSizeBeforeTest = khoanThuRepository.findAll().size();
        // set the field null
        khoanThu.setMaKhoanThu(null);

        // Create the KhoanThu, which fails.

        restKhoanThuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khoanThu)))
            .andExpect(status().isBadRequest());

        List<KhoanThu> khoanThuList = khoanThuRepository.findAll();
        assertThat(khoanThuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTenKhoanThuIsRequired() throws Exception {
        int databaseSizeBeforeTest = khoanThuRepository.findAll().size();
        // set the field null
        khoanThu.setTenKhoanThu(null);

        // Create the KhoanThu, which fails.

        restKhoanThuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khoanThu)))
            .andExpect(status().isBadRequest());

        List<KhoanThu> khoanThuList = khoanThuRepository.findAll();
        assertThat(khoanThuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDonGiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = khoanThuRepository.findAll().size();
        // set the field null
        khoanThu.setDonGia(null);

        // Create the KhoanThu, which fails.

        restKhoanThuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khoanThu)))
            .andExpect(status().isBadRequest());

        List<KhoanThu> khoanThuList = khoanThuRepository.findAll();
        assertThat(khoanThuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllKhoanThus() throws Exception {
        // Initialize the database
        khoanThuRepository.saveAndFlush(khoanThu);

        // Get all the khoanThuList
        restKhoanThuMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(khoanThu.getId().intValue())))
            .andExpect(jsonPath("$.[*].maKhoanThu").value(hasItem(DEFAULT_MA_KHOAN_THU)))
            .andExpect(jsonPath("$.[*].tenKhoanThu").value(hasItem(DEFAULT_TEN_KHOAN_THU)))
            .andExpect(jsonPath("$.[*].donGia").value(hasItem(DEFAULT_DON_GIA)))
            .andExpect(jsonPath("$.[*].loai").value(hasItem(DEFAULT_LOAI.toString())));
    }

    @Test
    @Transactional
    void getKhoanThu() throws Exception {
        // Initialize the database
        khoanThuRepository.saveAndFlush(khoanThu);

        // Get the khoanThu
        restKhoanThuMockMvc
            .perform(get(ENTITY_API_URL_ID, khoanThu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(khoanThu.getId().intValue()))
            .andExpect(jsonPath("$.maKhoanThu").value(DEFAULT_MA_KHOAN_THU))
            .andExpect(jsonPath("$.tenKhoanThu").value(DEFAULT_TEN_KHOAN_THU))
            .andExpect(jsonPath("$.donGia").value(DEFAULT_DON_GIA))
            .andExpect(jsonPath("$.loai").value(DEFAULT_LOAI.toString()));
    }

    @Test
    @Transactional
    void getNonExistingKhoanThu() throws Exception {
        // Get the khoanThu
        restKhoanThuMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingKhoanThu() throws Exception {
        // Initialize the database
        khoanThuRepository.saveAndFlush(khoanThu);

        int databaseSizeBeforeUpdate = khoanThuRepository.findAll().size();

        // Update the khoanThu
        KhoanThu updatedKhoanThu = khoanThuRepository.findById(khoanThu.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedKhoanThu are not directly saved in db
        em.detach(updatedKhoanThu);
        updatedKhoanThu.maKhoanThu(UPDATED_MA_KHOAN_THU).tenKhoanThu(UPDATED_TEN_KHOAN_THU).donGia(UPDATED_DON_GIA).loai(UPDATED_LOAI);

        restKhoanThuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedKhoanThu.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedKhoanThu))
            )
            .andExpect(status().isOk());

        // Validate the KhoanThu in the database
        List<KhoanThu> khoanThuList = khoanThuRepository.findAll();
        assertThat(khoanThuList).hasSize(databaseSizeBeforeUpdate);
        KhoanThu testKhoanThu = khoanThuList.get(khoanThuList.size() - 1);
        assertThat(testKhoanThu.getMaKhoanThu()).isEqualTo(UPDATED_MA_KHOAN_THU);
        assertThat(testKhoanThu.getTenKhoanThu()).isEqualTo(UPDATED_TEN_KHOAN_THU);
        assertThat(testKhoanThu.getDonGia()).isEqualTo(UPDATED_DON_GIA);
        assertThat(testKhoanThu.getLoai()).isEqualTo(UPDATED_LOAI);
    }

    @Test
    @Transactional
    void putNonExistingKhoanThu() throws Exception {
        int databaseSizeBeforeUpdate = khoanThuRepository.findAll().size();
        khoanThu.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKhoanThuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, khoanThu.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(khoanThu))
            )
            .andExpect(status().isBadRequest());

        // Validate the KhoanThu in the database
        List<KhoanThu> khoanThuList = khoanThuRepository.findAll();
        assertThat(khoanThuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchKhoanThu() throws Exception {
        int databaseSizeBeforeUpdate = khoanThuRepository.findAll().size();
        khoanThu.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKhoanThuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(khoanThu))
            )
            .andExpect(status().isBadRequest());

        // Validate the KhoanThu in the database
        List<KhoanThu> khoanThuList = khoanThuRepository.findAll();
        assertThat(khoanThuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamKhoanThu() throws Exception {
        int databaseSizeBeforeUpdate = khoanThuRepository.findAll().size();
        khoanThu.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKhoanThuMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(khoanThu)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KhoanThu in the database
        List<KhoanThu> khoanThuList = khoanThuRepository.findAll();
        assertThat(khoanThuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateKhoanThuWithPatch() throws Exception {
        // Initialize the database
        khoanThuRepository.saveAndFlush(khoanThu);

        int databaseSizeBeforeUpdate = khoanThuRepository.findAll().size();

        // Update the khoanThu using partial update
        KhoanThu partialUpdatedKhoanThu = new KhoanThu();
        partialUpdatedKhoanThu.setId(khoanThu.getId());

        partialUpdatedKhoanThu.tenKhoanThu(UPDATED_TEN_KHOAN_THU).donGia(UPDATED_DON_GIA).loai(UPDATED_LOAI);

        restKhoanThuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKhoanThu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKhoanThu))
            )
            .andExpect(status().isOk());

        // Validate the KhoanThu in the database
        List<KhoanThu> khoanThuList = khoanThuRepository.findAll();
        assertThat(khoanThuList).hasSize(databaseSizeBeforeUpdate);
        KhoanThu testKhoanThu = khoanThuList.get(khoanThuList.size() - 1);
        assertThat(testKhoanThu.getMaKhoanThu()).isEqualTo(DEFAULT_MA_KHOAN_THU);
        assertThat(testKhoanThu.getTenKhoanThu()).isEqualTo(UPDATED_TEN_KHOAN_THU);
        assertThat(testKhoanThu.getDonGia()).isEqualTo(UPDATED_DON_GIA);
        assertThat(testKhoanThu.getLoai()).isEqualTo(UPDATED_LOAI);
    }

    @Test
    @Transactional
    void fullUpdateKhoanThuWithPatch() throws Exception {
        // Initialize the database
        khoanThuRepository.saveAndFlush(khoanThu);

        int databaseSizeBeforeUpdate = khoanThuRepository.findAll().size();

        // Update the khoanThu using partial update
        KhoanThu partialUpdatedKhoanThu = new KhoanThu();
        partialUpdatedKhoanThu.setId(khoanThu.getId());

        partialUpdatedKhoanThu
            .maKhoanThu(UPDATED_MA_KHOAN_THU)
            .tenKhoanThu(UPDATED_TEN_KHOAN_THU)
            .donGia(UPDATED_DON_GIA)
            .loai(UPDATED_LOAI);

        restKhoanThuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedKhoanThu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedKhoanThu))
            )
            .andExpect(status().isOk());

        // Validate the KhoanThu in the database
        List<KhoanThu> khoanThuList = khoanThuRepository.findAll();
        assertThat(khoanThuList).hasSize(databaseSizeBeforeUpdate);
        KhoanThu testKhoanThu = khoanThuList.get(khoanThuList.size() - 1);
        assertThat(testKhoanThu.getMaKhoanThu()).isEqualTo(UPDATED_MA_KHOAN_THU);
        assertThat(testKhoanThu.getTenKhoanThu()).isEqualTo(UPDATED_TEN_KHOAN_THU);
        assertThat(testKhoanThu.getDonGia()).isEqualTo(UPDATED_DON_GIA);
        assertThat(testKhoanThu.getLoai()).isEqualTo(UPDATED_LOAI);
    }

    @Test
    @Transactional
    void patchNonExistingKhoanThu() throws Exception {
        int databaseSizeBeforeUpdate = khoanThuRepository.findAll().size();
        khoanThu.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKhoanThuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, khoanThu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(khoanThu))
            )
            .andExpect(status().isBadRequest());

        // Validate the KhoanThu in the database
        List<KhoanThu> khoanThuList = khoanThuRepository.findAll();
        assertThat(khoanThuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchKhoanThu() throws Exception {
        int databaseSizeBeforeUpdate = khoanThuRepository.findAll().size();
        khoanThu.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKhoanThuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(khoanThu))
            )
            .andExpect(status().isBadRequest());

        // Validate the KhoanThu in the database
        List<KhoanThu> khoanThuList = khoanThuRepository.findAll();
        assertThat(khoanThuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamKhoanThu() throws Exception {
        int databaseSizeBeforeUpdate = khoanThuRepository.findAll().size();
        khoanThu.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restKhoanThuMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(khoanThu)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the KhoanThu in the database
        List<KhoanThu> khoanThuList = khoanThuRepository.findAll();
        assertThat(khoanThuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteKhoanThu() throws Exception {
        // Initialize the database
        khoanThuRepository.saveAndFlush(khoanThu);

        int databaseSizeBeforeDelete = khoanThuRepository.findAll().size();

        // Delete the khoanThu
        restKhoanThuMockMvc
            .perform(delete(ENTITY_API_URL_ID, khoanThu.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KhoanThu> khoanThuList = khoanThuRepository.findAll();
        assertThat(khoanThuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
