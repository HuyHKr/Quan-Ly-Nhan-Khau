package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.HoKhau;
import com.mycompany.myapp.repository.HoKhauRepository;
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
 * Integration tests for the {@link HoKhauResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HoKhauResourceIT {

    private static final String DEFAULT_MA_HO_KHAU = "AAAAAAAAAA";
    private static final String UPDATED_MA_HO_KHAU = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_CHU_HO = "AAAAAAAAAA";
    private static final String UPDATED_TEN_CHU_HO = "BBBBBBBBBB";

    private static final String DEFAULT_DIA_CHI = "AAAAAAAAAA";
    private static final String UPDATED_DIA_CHI = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ho-khaus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HoKhauRepository hoKhauRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHoKhauMockMvc;

    private HoKhau hoKhau;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HoKhau createEntity(EntityManager em) {
        HoKhau hoKhau = new HoKhau().maHoKhau(DEFAULT_MA_HO_KHAU).tenChuHo(DEFAULT_TEN_CHU_HO).diaChi(DEFAULT_DIA_CHI);
        return hoKhau;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HoKhau createUpdatedEntity(EntityManager em) {
        HoKhau hoKhau = new HoKhau().maHoKhau(UPDATED_MA_HO_KHAU).tenChuHo(UPDATED_TEN_CHU_HO).diaChi(UPDATED_DIA_CHI);
        return hoKhau;
    }

    @BeforeEach
    public void initTest() {
        hoKhau = createEntity(em);
    }

    @Test
    @Transactional
    void createHoKhau() throws Exception {
        int databaseSizeBeforeCreate = hoKhauRepository.findAll().size();
        // Create the HoKhau
        restHoKhauMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoKhau)))
            .andExpect(status().isCreated());

        // Validate the HoKhau in the database
        List<HoKhau> hoKhauList = hoKhauRepository.findAll();
        assertThat(hoKhauList).hasSize(databaseSizeBeforeCreate + 1);
        HoKhau testHoKhau = hoKhauList.get(hoKhauList.size() - 1);
        assertThat(testHoKhau.getMaHoKhau()).isEqualTo(DEFAULT_MA_HO_KHAU);
        assertThat(testHoKhau.getTenChuHo()).isEqualTo(DEFAULT_TEN_CHU_HO);
        assertThat(testHoKhau.getDiaChi()).isEqualTo(DEFAULT_DIA_CHI);
    }

    @Test
    @Transactional
    void createHoKhauWithExistingId() throws Exception {
        // Create the HoKhau with an existing ID
        hoKhau.setId(1L);

        int databaseSizeBeforeCreate = hoKhauRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHoKhauMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoKhau)))
            .andExpect(status().isBadRequest());

        // Validate the HoKhau in the database
        List<HoKhau> hoKhauList = hoKhauRepository.findAll();
        assertThat(hoKhauList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkMaHoKhauIsRequired() throws Exception {
        int databaseSizeBeforeTest = hoKhauRepository.findAll().size();
        // set the field null
        hoKhau.setMaHoKhau(null);

        // Create the HoKhau, which fails.

        restHoKhauMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoKhau)))
            .andExpect(status().isBadRequest());

        List<HoKhau> hoKhauList = hoKhauRepository.findAll();
        assertThat(hoKhauList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTenChuHoIsRequired() throws Exception {
        int databaseSizeBeforeTest = hoKhauRepository.findAll().size();
        // set the field null
        hoKhau.setTenChuHo(null);

        // Create the HoKhau, which fails.

        restHoKhauMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoKhau)))
            .andExpect(status().isBadRequest());

        List<HoKhau> hoKhauList = hoKhauRepository.findAll();
        assertThat(hoKhauList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDiaChiIsRequired() throws Exception {
        int databaseSizeBeforeTest = hoKhauRepository.findAll().size();
        // set the field null
        hoKhau.setDiaChi(null);

        // Create the HoKhau, which fails.

        restHoKhauMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoKhau)))
            .andExpect(status().isBadRequest());

        List<HoKhau> hoKhauList = hoKhauRepository.findAll();
        assertThat(hoKhauList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHoKhaus() throws Exception {
        // Initialize the database
        hoKhauRepository.saveAndFlush(hoKhau);

        // Get all the hoKhauList
        restHoKhauMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hoKhau.getId().intValue())))
            .andExpect(jsonPath("$.[*].maHoKhau").value(hasItem(DEFAULT_MA_HO_KHAU)))
            .andExpect(jsonPath("$.[*].tenChuHo").value(hasItem(DEFAULT_TEN_CHU_HO)))
            .andExpect(jsonPath("$.[*].diaChi").value(hasItem(DEFAULT_DIA_CHI)));
    }

    @Test
    @Transactional
    void getHoKhau() throws Exception {
        // Initialize the database
        hoKhauRepository.saveAndFlush(hoKhau);

        // Get the hoKhau
        restHoKhauMockMvc
            .perform(get(ENTITY_API_URL_ID, hoKhau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hoKhau.getId().intValue()))
            .andExpect(jsonPath("$.maHoKhau").value(DEFAULT_MA_HO_KHAU))
            .andExpect(jsonPath("$.tenChuHo").value(DEFAULT_TEN_CHU_HO))
            .andExpect(jsonPath("$.diaChi").value(DEFAULT_DIA_CHI));
    }

    @Test
    @Transactional
    void getNonExistingHoKhau() throws Exception {
        // Get the hoKhau
        restHoKhauMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHoKhau() throws Exception {
        // Initialize the database
        hoKhauRepository.saveAndFlush(hoKhau);

        int databaseSizeBeforeUpdate = hoKhauRepository.findAll().size();

        // Update the hoKhau
        HoKhau updatedHoKhau = hoKhauRepository.findById(hoKhau.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHoKhau are not directly saved in db
        em.detach(updatedHoKhau);
        updatedHoKhau.maHoKhau(UPDATED_MA_HO_KHAU).tenChuHo(UPDATED_TEN_CHU_HO).diaChi(UPDATED_DIA_CHI);

        restHoKhauMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHoKhau.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHoKhau))
            )
            .andExpect(status().isOk());

        // Validate the HoKhau in the database
        List<HoKhau> hoKhauList = hoKhauRepository.findAll();
        assertThat(hoKhauList).hasSize(databaseSizeBeforeUpdate);
        HoKhau testHoKhau = hoKhauList.get(hoKhauList.size() - 1);
        assertThat(testHoKhau.getMaHoKhau()).isEqualTo(UPDATED_MA_HO_KHAU);
        assertThat(testHoKhau.getTenChuHo()).isEqualTo(UPDATED_TEN_CHU_HO);
        assertThat(testHoKhau.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
    }

    @Test
    @Transactional
    void putNonExistingHoKhau() throws Exception {
        int databaseSizeBeforeUpdate = hoKhauRepository.findAll().size();
        hoKhau.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoKhauMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hoKhau.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hoKhau))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoKhau in the database
        List<HoKhau> hoKhauList = hoKhauRepository.findAll();
        assertThat(hoKhauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHoKhau() throws Exception {
        int databaseSizeBeforeUpdate = hoKhauRepository.findAll().size();
        hoKhau.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoKhauMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hoKhau))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoKhau in the database
        List<HoKhau> hoKhauList = hoKhauRepository.findAll();
        assertThat(hoKhauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHoKhau() throws Exception {
        int databaseSizeBeforeUpdate = hoKhauRepository.findAll().size();
        hoKhau.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoKhauMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoKhau)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HoKhau in the database
        List<HoKhau> hoKhauList = hoKhauRepository.findAll();
        assertThat(hoKhauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHoKhauWithPatch() throws Exception {
        // Initialize the database
        hoKhauRepository.saveAndFlush(hoKhau);

        int databaseSizeBeforeUpdate = hoKhauRepository.findAll().size();

        // Update the hoKhau using partial update
        HoKhau partialUpdatedHoKhau = new HoKhau();
        partialUpdatedHoKhau.setId(hoKhau.getId());

        partialUpdatedHoKhau.maHoKhau(UPDATED_MA_HO_KHAU);

        restHoKhauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoKhau.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHoKhau))
            )
            .andExpect(status().isOk());

        // Validate the HoKhau in the database
        List<HoKhau> hoKhauList = hoKhauRepository.findAll();
        assertThat(hoKhauList).hasSize(databaseSizeBeforeUpdate);
        HoKhau testHoKhau = hoKhauList.get(hoKhauList.size() - 1);
        assertThat(testHoKhau.getMaHoKhau()).isEqualTo(UPDATED_MA_HO_KHAU);
        assertThat(testHoKhau.getTenChuHo()).isEqualTo(DEFAULT_TEN_CHU_HO);
        assertThat(testHoKhau.getDiaChi()).isEqualTo(DEFAULT_DIA_CHI);
    }

    @Test
    @Transactional
    void fullUpdateHoKhauWithPatch() throws Exception {
        // Initialize the database
        hoKhauRepository.saveAndFlush(hoKhau);

        int databaseSizeBeforeUpdate = hoKhauRepository.findAll().size();

        // Update the hoKhau using partial update
        HoKhau partialUpdatedHoKhau = new HoKhau();
        partialUpdatedHoKhau.setId(hoKhau.getId());

        partialUpdatedHoKhau.maHoKhau(UPDATED_MA_HO_KHAU).tenChuHo(UPDATED_TEN_CHU_HO).diaChi(UPDATED_DIA_CHI);

        restHoKhauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoKhau.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHoKhau))
            )
            .andExpect(status().isOk());

        // Validate the HoKhau in the database
        List<HoKhau> hoKhauList = hoKhauRepository.findAll();
        assertThat(hoKhauList).hasSize(databaseSizeBeforeUpdate);
        HoKhau testHoKhau = hoKhauList.get(hoKhauList.size() - 1);
        assertThat(testHoKhau.getMaHoKhau()).isEqualTo(UPDATED_MA_HO_KHAU);
        assertThat(testHoKhau.getTenChuHo()).isEqualTo(UPDATED_TEN_CHU_HO);
        assertThat(testHoKhau.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
    }

    @Test
    @Transactional
    void patchNonExistingHoKhau() throws Exception {
        int databaseSizeBeforeUpdate = hoKhauRepository.findAll().size();
        hoKhau.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoKhauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hoKhau.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hoKhau))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoKhau in the database
        List<HoKhau> hoKhauList = hoKhauRepository.findAll();
        assertThat(hoKhauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHoKhau() throws Exception {
        int databaseSizeBeforeUpdate = hoKhauRepository.findAll().size();
        hoKhau.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoKhauMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hoKhau))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoKhau in the database
        List<HoKhau> hoKhauList = hoKhauRepository.findAll();
        assertThat(hoKhauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHoKhau() throws Exception {
        int databaseSizeBeforeUpdate = hoKhauRepository.findAll().size();
        hoKhau.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoKhauMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hoKhau)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HoKhau in the database
        List<HoKhau> hoKhauList = hoKhauRepository.findAll();
        assertThat(hoKhauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHoKhau() throws Exception {
        // Initialize the database
        hoKhauRepository.saveAndFlush(hoKhau);

        int databaseSizeBeforeDelete = hoKhauRepository.findAll().size();

        // Delete the hoKhau
        restHoKhauMockMvc
            .perform(delete(ENTITY_API_URL_ID, hoKhau.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HoKhau> hoKhauList = hoKhauRepository.findAll();
        assertThat(hoKhauList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
