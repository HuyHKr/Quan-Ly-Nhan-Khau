package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.HoKhau;
import com.mycompany.myapp.repository.HoKhauRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.HoKhau}.
 */
@RestController
@RequestMapping("/api/ho-khaus")
@Transactional
public class HoKhauResource {

    private final Logger log = LoggerFactory.getLogger(HoKhauResource.class);

    private static final String ENTITY_NAME = "hoKhau";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HoKhauRepository hoKhauRepository;

    public HoKhauResource(HoKhauRepository hoKhauRepository) {
        this.hoKhauRepository = hoKhauRepository;
    }

    /**
     * {@code POST  /ho-khaus} : Create a new hoKhau.
     *
     * @param hoKhau the hoKhau to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hoKhau, or with status {@code 400 (Bad Request)} if the hoKhau has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HoKhau> createHoKhau(@Valid @RequestBody HoKhau hoKhau) throws URISyntaxException {
        log.debug("REST request to save HoKhau : {}", hoKhau);
        if (hoKhau.getId() != null) {
            throw new BadRequestAlertException("A new hoKhau cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HoKhau result = hoKhauRepository.save(hoKhau);
        return ResponseEntity
            .created(new URI("/api/ho-khaus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ho-khaus/:id} : Updates an existing hoKhau.
     *
     * @param id the id of the hoKhau to save.
     * @param hoKhau the hoKhau to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hoKhau,
     * or with status {@code 400 (Bad Request)} if the hoKhau is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hoKhau couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HoKhau> updateHoKhau(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HoKhau hoKhau
    ) throws URISyntaxException {
        log.debug("REST request to update HoKhau : {}, {}", id, hoKhau);
        if (hoKhau.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hoKhau.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hoKhauRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HoKhau result = hoKhauRepository.save(hoKhau);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hoKhau.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ho-khaus/:id} : Partial updates given fields of an existing hoKhau, field will ignore if it is null
     *
     * @param id the id of the hoKhau to save.
     * @param hoKhau the hoKhau to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hoKhau,
     * or with status {@code 400 (Bad Request)} if the hoKhau is not valid,
     * or with status {@code 404 (Not Found)} if the hoKhau is not found,
     * or with status {@code 500 (Internal Server Error)} if the hoKhau couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HoKhau> partialUpdateHoKhau(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HoKhau hoKhau
    ) throws URISyntaxException {
        log.debug("REST request to partial update HoKhau partially : {}, {}", id, hoKhau);
        if (hoKhau.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hoKhau.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hoKhauRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HoKhau> result = hoKhauRepository
            .findById(hoKhau.getId())
            .map(existingHoKhau -> {
                if (hoKhau.getMaHoKhau() != null) {
                    existingHoKhau.setMaHoKhau(hoKhau.getMaHoKhau());
                }
                if (hoKhau.getTenChuHo() != null) {
                    existingHoKhau.setTenChuHo(hoKhau.getTenChuHo());
                }
                if (hoKhau.getDiaChi() != null) {
                    existingHoKhau.setDiaChi(hoKhau.getDiaChi());
                }

                return existingHoKhau;
            })
            .map(hoKhauRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hoKhau.getId().toString())
        );
    }

    /**
     * {@code GET  /ho-khaus} : get all the hoKhaus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hoKhaus in body.
     */
    @GetMapping("")
    public List<HoKhau> getAllHoKhaus() {
        log.debug("REST request to get all HoKhaus");
        return hoKhauRepository.findAll();
    }

    /**
     * {@code GET  /ho-khaus/:id} : get the "id" hoKhau.
     *
     * @param id the id of the hoKhau to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hoKhau, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HoKhau> getHoKhau(@PathVariable Long id) {
        log.debug("REST request to get HoKhau : {}", id);
        Optional<HoKhau> hoKhau = hoKhauRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hoKhau);
    }

    /**
     * {@code DELETE  /ho-khaus/:id} : delete the "id" hoKhau.
     *
     * @param id the id of the hoKhau to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHoKhau(@PathVariable Long id) {
        log.debug("REST request to delete HoKhau : {}", id);
        hoKhauRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
