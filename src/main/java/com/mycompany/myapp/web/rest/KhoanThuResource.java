package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.KhoanThu;
import com.mycompany.myapp.repository.KhoanThuRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.KhoanThu}.
 */
@RestController
@RequestMapping("/api/khoan-thus")
@Transactional
public class KhoanThuResource {

    private final Logger log = LoggerFactory.getLogger(KhoanThuResource.class);

    private static final String ENTITY_NAME = "khoanThu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KhoanThuRepository khoanThuRepository;

    public KhoanThuResource(KhoanThuRepository khoanThuRepository) {
        this.khoanThuRepository = khoanThuRepository;
    }

    /**
     * {@code POST  /khoan-thus} : Create a new khoanThu.
     *
     * @param khoanThu the khoanThu to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new khoanThu, or with status {@code 400 (Bad Request)} if the khoanThu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<KhoanThu> createKhoanThu(@Valid @RequestBody KhoanThu khoanThu) throws URISyntaxException {
        log.debug("REST request to save KhoanThu : {}", khoanThu);
        if (khoanThu.getId() != null) {
            throw new BadRequestAlertException("A new khoanThu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KhoanThu result = khoanThuRepository.save(khoanThu);
        return ResponseEntity
            .created(new URI("/api/khoan-thus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /khoan-thus/:id} : Updates an existing khoanThu.
     *
     * @param id the id of the khoanThu to save.
     * @param khoanThu the khoanThu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated khoanThu,
     * or with status {@code 400 (Bad Request)} if the khoanThu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the khoanThu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<KhoanThu> updateKhoanThu(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody KhoanThu khoanThu
    ) throws URISyntaxException {
        log.debug("REST request to update KhoanThu : {}, {}", id, khoanThu);
        if (khoanThu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, khoanThu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!khoanThuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KhoanThu result = khoanThuRepository.save(khoanThu);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, khoanThu.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /khoan-thus/:id} : Partial updates given fields of an existing khoanThu, field will ignore if it is null
     *
     * @param id the id of the khoanThu to save.
     * @param khoanThu the khoanThu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated khoanThu,
     * or with status {@code 400 (Bad Request)} if the khoanThu is not valid,
     * or with status {@code 404 (Not Found)} if the khoanThu is not found,
     * or with status {@code 500 (Internal Server Error)} if the khoanThu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<KhoanThu> partialUpdateKhoanThu(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody KhoanThu khoanThu
    ) throws URISyntaxException {
        log.debug("REST request to partial update KhoanThu partially : {}, {}", id, khoanThu);
        if (khoanThu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, khoanThu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!khoanThuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<KhoanThu> result = khoanThuRepository
            .findById(khoanThu.getId())
            .map(existingKhoanThu -> {
                if (khoanThu.getMaKhoanThu() != null) {
                    existingKhoanThu.setMaKhoanThu(khoanThu.getMaKhoanThu());
                }
                if (khoanThu.getTenKhoanThu() != null) {
                    existingKhoanThu.setTenKhoanThu(khoanThu.getTenKhoanThu());
                }
                if (khoanThu.getDonGia() != null) {
                    existingKhoanThu.setDonGia(khoanThu.getDonGia());
                }
                if (khoanThu.getLoai() != null) {
                    existingKhoanThu.setLoai(khoanThu.getLoai());
                }

                return existingKhoanThu;
            })
            .map(khoanThuRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, khoanThu.getId().toString())
        );
    }

    /**
     * {@code GET  /khoan-thus} : get all the khoanThus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of khoanThus in body.
     */
    @GetMapping("")
    public List<KhoanThu> getAllKhoanThus() {
        log.debug("REST request to get all KhoanThus");
        return khoanThuRepository.findAll();
    }

    /**
     * {@code GET  /khoan-thus/:id} : get the "id" khoanThu.
     *
     * @param id the id of the khoanThu to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the khoanThu, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<KhoanThu> getKhoanThu(@PathVariable Long id) {
        log.debug("REST request to get KhoanThu : {}", id);
        Optional<KhoanThu> khoanThu = khoanThuRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(khoanThu);
    }

    /**
     * {@code DELETE  /khoan-thus/:id} : delete the "id" khoanThu.
     *
     * @param id the id of the khoanThu to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKhoanThu(@PathVariable Long id) {
        log.debug("REST request to delete KhoanThu : {}", id);
        khoanThuRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
