package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.NopTien;
import com.mycompany.myapp.repository.NopTienRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NopTienResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NopTienResourceIT {

    private static final Long DEFAULT_SO_TIEN = 1L;
    private static final Long UPDATED_SO_TIEN = 2L;

    private static final Instant DEFAULT_NGAY_NOP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_NOP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/nop-tiens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NopTienRepository nopTienRepository;

    @Mock
    private NopTienRepository nopTienRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNopTienMockMvc;

    private NopTien nopTien;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NopTien createEntity(EntityManager em) {
        NopTien nopTien = new NopTien().soTien(DEFAULT_SO_TIEN).ngayNop(DEFAULT_NGAY_NOP);
        return nopTien;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NopTien createUpdatedEntity(EntityManager em) {
        NopTien nopTien = new NopTien().soTien(UPDATED_SO_TIEN).ngayNop(UPDATED_NGAY_NOP);
        return nopTien;
    }

    @BeforeEach
    public void initTest() {
        nopTien = createEntity(em);
    }

    @Test
    @Transactional
    void createNopTien() throws Exception {
        int databaseSizeBeforeCreate = nopTienRepository.findAll().size();
        // Create the NopTien
        restNopTienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nopTien)))
            .andExpect(status().isCreated());

        // Validate the NopTien in the database
        List<NopTien> nopTienList = nopTienRepository.findAll();
        assertThat(nopTienList).hasSize(databaseSizeBeforeCreate + 1);
        NopTien testNopTien = nopTienList.get(nopTienList.size() - 1);
        assertThat(testNopTien.getSoTien()).isEqualTo(DEFAULT_SO_TIEN);
        assertThat(testNopTien.getNgayNop()).isEqualTo(DEFAULT_NGAY_NOP);
    }

    @Test
    @Transactional
    void createNopTienWithExistingId() throws Exception {
        // Create the NopTien with an existing ID
        nopTien.setId(1L);

        int databaseSizeBeforeCreate = nopTienRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNopTienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nopTien)))
            .andExpect(status().isBadRequest());

        // Validate the NopTien in the database
        List<NopTien> nopTienList = nopTienRepository.findAll();
        assertThat(nopTienList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNopTiens() throws Exception {
        // Initialize the database
        nopTienRepository.saveAndFlush(nopTien);

        // Get all the nopTienList
        restNopTienMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nopTien.getId().intValue())))
            .andExpect(jsonPath("$.[*].soTien").value(hasItem(DEFAULT_SO_TIEN.intValue())))
            .andExpect(jsonPath("$.[*].ngayNop").value(hasItem(DEFAULT_NGAY_NOP.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNopTiensWithEagerRelationshipsIsEnabled() throws Exception {
        when(nopTienRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNopTienMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(nopTienRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNopTiensWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(nopTienRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNopTienMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(nopTienRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getNopTien() throws Exception {
        // Initialize the database
        nopTienRepository.saveAndFlush(nopTien);

        // Get the nopTien
        restNopTienMockMvc
            .perform(get(ENTITY_API_URL_ID, nopTien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nopTien.getId().intValue()))
            .andExpect(jsonPath("$.soTien").value(DEFAULT_SO_TIEN.intValue()))
            .andExpect(jsonPath("$.ngayNop").value(DEFAULT_NGAY_NOP.toString()));
    }

    @Test
    @Transactional
    void getNonExistingNopTien() throws Exception {
        // Get the nopTien
        restNopTienMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNopTien() throws Exception {
        // Initialize the database
        nopTienRepository.saveAndFlush(nopTien);

        int databaseSizeBeforeUpdate = nopTienRepository.findAll().size();

        // Update the nopTien
        NopTien updatedNopTien = nopTienRepository.findById(nopTien.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNopTien are not directly saved in db
        em.detach(updatedNopTien);
        updatedNopTien.soTien(UPDATED_SO_TIEN).ngayNop(UPDATED_NGAY_NOP);

        restNopTienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNopTien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNopTien))
            )
            .andExpect(status().isOk());

        // Validate the NopTien in the database
        List<NopTien> nopTienList = nopTienRepository.findAll();
        assertThat(nopTienList).hasSize(databaseSizeBeforeUpdate);
        NopTien testNopTien = nopTienList.get(nopTienList.size() - 1);
        assertThat(testNopTien.getSoTien()).isEqualTo(UPDATED_SO_TIEN);
        assertThat(testNopTien.getNgayNop()).isEqualTo(UPDATED_NGAY_NOP);
    }

    @Test
    @Transactional
    void putNonExistingNopTien() throws Exception {
        int databaseSizeBeforeUpdate = nopTienRepository.findAll().size();
        nopTien.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNopTienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nopTien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nopTien))
            )
            .andExpect(status().isBadRequest());

        // Validate the NopTien in the database
        List<NopTien> nopTienList = nopTienRepository.findAll();
        assertThat(nopTienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNopTien() throws Exception {
        int databaseSizeBeforeUpdate = nopTienRepository.findAll().size();
        nopTien.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNopTienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nopTien))
            )
            .andExpect(status().isBadRequest());

        // Validate the NopTien in the database
        List<NopTien> nopTienList = nopTienRepository.findAll();
        assertThat(nopTienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNopTien() throws Exception {
        int databaseSizeBeforeUpdate = nopTienRepository.findAll().size();
        nopTien.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNopTienMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nopTien)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NopTien in the database
        List<NopTien> nopTienList = nopTienRepository.findAll();
        assertThat(nopTienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNopTienWithPatch() throws Exception {
        // Initialize the database
        nopTienRepository.saveAndFlush(nopTien);

        int databaseSizeBeforeUpdate = nopTienRepository.findAll().size();

        // Update the nopTien using partial update
        NopTien partialUpdatedNopTien = new NopTien();
        partialUpdatedNopTien.setId(nopTien.getId());

        partialUpdatedNopTien.ngayNop(UPDATED_NGAY_NOP);

        restNopTienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNopTien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNopTien))
            )
            .andExpect(status().isOk());

        // Validate the NopTien in the database
        List<NopTien> nopTienList = nopTienRepository.findAll();
        assertThat(nopTienList).hasSize(databaseSizeBeforeUpdate);
        NopTien testNopTien = nopTienList.get(nopTienList.size() - 1);
        assertThat(testNopTien.getSoTien()).isEqualTo(DEFAULT_SO_TIEN);
        assertThat(testNopTien.getNgayNop()).isEqualTo(UPDATED_NGAY_NOP);
    }

    @Test
    @Transactional
    void fullUpdateNopTienWithPatch() throws Exception {
        // Initialize the database
        nopTienRepository.saveAndFlush(nopTien);

        int databaseSizeBeforeUpdate = nopTienRepository.findAll().size();

        // Update the nopTien using partial update
        NopTien partialUpdatedNopTien = new NopTien();
        partialUpdatedNopTien.setId(nopTien.getId());

        partialUpdatedNopTien.soTien(UPDATED_SO_TIEN).ngayNop(UPDATED_NGAY_NOP);

        restNopTienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNopTien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNopTien))
            )
            .andExpect(status().isOk());

        // Validate the NopTien in the database
        List<NopTien> nopTienList = nopTienRepository.findAll();
        assertThat(nopTienList).hasSize(databaseSizeBeforeUpdate);
        NopTien testNopTien = nopTienList.get(nopTienList.size() - 1);
        assertThat(testNopTien.getSoTien()).isEqualTo(UPDATED_SO_TIEN);
        assertThat(testNopTien.getNgayNop()).isEqualTo(UPDATED_NGAY_NOP);
    }

    @Test
    @Transactional
    void patchNonExistingNopTien() throws Exception {
        int databaseSizeBeforeUpdate = nopTienRepository.findAll().size();
        nopTien.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNopTienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nopTien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nopTien))
            )
            .andExpect(status().isBadRequest());

        // Validate the NopTien in the database
        List<NopTien> nopTienList = nopTienRepository.findAll();
        assertThat(nopTienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNopTien() throws Exception {
        int databaseSizeBeforeUpdate = nopTienRepository.findAll().size();
        nopTien.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNopTienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nopTien))
            )
            .andExpect(status().isBadRequest());

        // Validate the NopTien in the database
        List<NopTien> nopTienList = nopTienRepository.findAll();
        assertThat(nopTienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNopTien() throws Exception {
        int databaseSizeBeforeUpdate = nopTienRepository.findAll().size();
        nopTien.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNopTienMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nopTien)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NopTien in the database
        List<NopTien> nopTienList = nopTienRepository.findAll();
        assertThat(nopTienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNopTien() throws Exception {
        // Initialize the database
        nopTienRepository.saveAndFlush(nopTien);

        int databaseSizeBeforeDelete = nopTienRepository.findAll().size();

        // Delete the nopTien
        restNopTienMockMvc
            .perform(delete(ENTITY_API_URL_ID, nopTien.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NopTien> nopTienList = nopTienRepository.findAll();
        assertThat(nopTienList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
