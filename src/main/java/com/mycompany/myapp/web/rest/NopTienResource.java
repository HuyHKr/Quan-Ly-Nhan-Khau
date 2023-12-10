package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.NopTien;
import com.mycompany.myapp.repository.NopTienRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.NopTien}.
 */
@RestController
@RequestMapping("/api/nop-tiens")
@Transactional
public class NopTienResource {

    private final Logger log = LoggerFactory.getLogger(NopTienResource.class);

    private static final String ENTITY_NAME = "nopTien";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NopTienRepository nopTienRepository;

    public NopTienResource(NopTienRepository nopTienRepository) {
        this.nopTienRepository = nopTienRepository;
    }

    /**
     * {@code POST  /nop-tiens} : Create a new nopTien.
     *
     * @param nopTien the nopTien to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nopTien, or with status {@code 400 (Bad Request)} if the nopTien has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NopTien> createNopTien(@RequestBody NopTien nopTien) throws URISyntaxException {
        log.debug("REST request to save NopTien : {}", nopTien);
        if (nopTien.getId() != null) {
            throw new BadRequestAlertException("A new nopTien cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NopTien result = nopTienRepository.save(nopTien);
        return ResponseEntity
            .created(new URI("/api/nop-tiens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nop-tiens/:id} : Updates an existing nopTien.
     *
     * @param id the id of the nopTien to save.
     * @param nopTien the nopTien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nopTien,
     * or with status {@code 400 (Bad Request)} if the nopTien is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nopTien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NopTien> updateNopTien(@PathVariable(value = "id", required = false) final Long id, @RequestBody NopTien nopTien)
        throws URISyntaxException {
        log.debug("REST request to update NopTien : {}, {}", id, nopTien);
        if (nopTien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nopTien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nopTienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NopTien result = nopTienRepository.save(nopTien);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nopTien.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nop-tiens/:id} : Partial updates given fields of an existing nopTien, field will ignore if it is null
     *
     * @param id the id of the nopTien to save.
     * @param nopTien the nopTien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nopTien,
     * or with status {@code 400 (Bad Request)} if the nopTien is not valid,
     * or with status {@code 404 (Not Found)} if the nopTien is not found,
     * or with status {@code 500 (Internal Server Error)} if the nopTien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NopTien> partialUpdateNopTien(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NopTien nopTien
    ) throws URISyntaxException {
        log.debug("REST request to partial update NopTien partially : {}, {}", id, nopTien);
        if (nopTien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nopTien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nopTienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NopTien> result = nopTienRepository
            .findById(nopTien.getId())
            .map(existingNopTien -> {
                if (nopTien.getSoTien() != null) {
                    existingNopTien.setSoTien(nopTien.getSoTien());
                }
                if (nopTien.getNgayNop() != null) {
                    existingNopTien.setNgayNop(nopTien.getNgayNop());
                }

                return existingNopTien;
            })
            .map(nopTienRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nopTien.getId().toString())
        );
    }

    /**
     * {@code GET  /nop-tiens} : get all the nopTiens.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nopTiens in body.
     */
    @GetMapping("")
    public List<NopTien> getAllNopTiens(@RequestParam(required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all NopTiens");
        if (eagerload) {
            return nopTienRepository.findAllWithEagerRelationships();
        } else {
            return nopTienRepository.findAll();
        }
    }

    /**
     * {@code GET  /nop-tiens/:id} : get the "id" nopTien.
     *
     * @param id the id of the nopTien to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nopTien, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NopTien> getNopTien(@PathVariable Long id) {
        log.debug("REST request to get NopTien : {}", id);
        Optional<NopTien> nopTien = nopTienRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(nopTien);
    }

    /**
     * {@code DELETE  /nop-tiens/:id} : delete the "id" nopTien.
     *
     * @param id the id of the nopTien to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNopTien(@PathVariable Long id) {
        log.debug("REST request to delete NopTien : {}", id);
        nopTienRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
