package com.entando.bundle.service;

import com.entando.bundle.service.dto.OpinionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.entando.bundle.domain.Opinion}.
 */
public interface OpinionService {
    /**
     * Save a opinion.
     *
     * @param opinionDTO the entity to save.
     * @return the persisted entity.
     */
    OpinionDTO save(OpinionDTO opinionDTO);

    /**
     * Partially updates a opinion.
     *
     * @param opinionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OpinionDTO> partialUpdate(OpinionDTO opinionDTO);

    /**
     * Get all the opinions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OpinionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" opinion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OpinionDTO> findOne(Long id);

    /**
     * Delete the "id" opinion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
