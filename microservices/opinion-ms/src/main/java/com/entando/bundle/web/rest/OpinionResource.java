package com.entando.bundle.web.rest;

import com.entando.bundle.repository.OpinionRepository;
import com.entando.bundle.service.OpinionQueryService;
import com.entando.bundle.service.OpinionService;
import com.entando.bundle.service.criteria.OpinionCriteria;
import com.entando.bundle.service.dto.OpinionDTO;
import com.entando.bundle.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.entando.bundle.domain.Opinion}.
 */
@RestController
@RequestMapping("/api")
public class OpinionResource {

    private final Logger log = LoggerFactory.getLogger(OpinionResource.class);

    private static final String ENTITY_NAME = "opinionOpinion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpinionService opinionService;

    private final OpinionRepository opinionRepository;

    private final OpinionQueryService opinionQueryService;

    public OpinionResource(OpinionService opinionService, OpinionRepository opinionRepository, OpinionQueryService opinionQueryService) {
        this.opinionService = opinionService;
        this.opinionRepository = opinionRepository;
        this.opinionQueryService = opinionQueryService;
    }

    /**
     * {@code POST  /opinions} : Create a new opinion.
     *
     * @param opinionDTO the opinionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opinionDTO, or with status {@code 400 (Bad Request)} if the opinion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/opinions")
    public ResponseEntity<OpinionDTO> createOpinion(@RequestBody OpinionDTO opinionDTO) throws URISyntaxException {
        log.debug("REST request to save Opinion : {}", opinionDTO);
        if (opinionDTO.getId() != null) {
            throw new BadRequestAlertException("A new opinion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OpinionDTO result = opinionService.save(opinionDTO);
        return ResponseEntity
            .created(new URI("/api/opinions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /opinions/:id} : Updates an existing opinion.
     *
     * @param id the id of the opinionDTO to save.
     * @param opinionDTO the opinionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opinionDTO,
     * or with status {@code 400 (Bad Request)} if the opinionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opinionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/opinions/{id}")
    public ResponseEntity<OpinionDTO> updateOpinion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OpinionDTO opinionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Opinion : {}, {}", id, opinionDTO);
        if (opinionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opinionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opinionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OpinionDTO result = opinionService.save(opinionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, opinionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /opinions/:id} : Partial updates given fields of an existing opinion, field will ignore if it is null
     *
     * @param id the id of the opinionDTO to save.
     * @param opinionDTO the opinionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opinionDTO,
     * or with status {@code 400 (Bad Request)} if the opinionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the opinionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the opinionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/opinions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OpinionDTO> partialUpdateOpinion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OpinionDTO opinionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Opinion partially : {}, {}", id, opinionDTO);
        if (opinionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opinionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opinionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OpinionDTO> result = opinionService.partialUpdate(opinionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, opinionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /opinions} : get all the opinions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opinions in body.
     */
    @GetMapping("/opinions")
    public ResponseEntity<List<OpinionDTO>> getAllOpinions(OpinionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Opinions by criteria: {}", criteria);
        Page<OpinionDTO> page = opinionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /opinions/count} : count all the opinions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/opinions/count")
    public ResponseEntity<Long> countOpinions(OpinionCriteria criteria) {
        log.debug("REST request to count Opinions by criteria: {}", criteria);
        return ResponseEntity.ok().body(opinionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /opinions/:id} : get the "id" opinion.
     *
     * @param id the id of the opinionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opinionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/opinions/{id}")
    public ResponseEntity<OpinionDTO> getOpinion(@PathVariable Long id) {
        log.debug("REST request to get Opinion : {}", id);
        Optional<OpinionDTO> opinionDTO = opinionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(opinionDTO);
    }

    /**
     * {@code DELETE  /opinions/:id} : delete the "id" opinion.
     *
     * @param id the id of the opinionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/opinions/{id}")
    public ResponseEntity<Void> deleteOpinion(@PathVariable Long id) {
        log.debug("REST request to delete Opinion : {}", id);
        opinionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
