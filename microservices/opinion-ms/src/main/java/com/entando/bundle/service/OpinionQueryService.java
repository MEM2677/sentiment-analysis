package com.entando.bundle.service;

import com.entando.bundle.domain.*; // for static metamodels
import com.entando.bundle.domain.Opinion;
import com.entando.bundle.repository.OpinionRepository;
import com.entando.bundle.service.criteria.OpinionCriteria;
import com.entando.bundle.service.dto.OpinionDTO;
import com.entando.bundle.service.mapper.OpinionMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Opinion} entities in the database.
 * The main input is a {@link OpinionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OpinionDTO} or a {@link Page} of {@link OpinionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OpinionQueryService extends QueryService<Opinion> {

    private final Logger log = LoggerFactory.getLogger(OpinionQueryService.class);

    private final OpinionRepository opinionRepository;

    private final OpinionMapper opinionMapper;

    public OpinionQueryService(OpinionRepository opinionRepository, OpinionMapper opinionMapper) {
        this.opinionRepository = opinionRepository;
        this.opinionMapper = opinionMapper;
    }

    /**
     * Return a {@link List} of {@link OpinionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OpinionDTO> findByCriteria(OpinionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Opinion> specification = createSpecification(criteria);
        return opinionMapper.toDto(opinionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OpinionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OpinionDTO> findByCriteria(OpinionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Opinion> specification = createSpecification(criteria);
        return opinionRepository.findAll(specification, page).map(opinionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OpinionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Opinion> specification = createSpecification(criteria);
        return opinionRepository.count(specification);
    }

    /**
     * Function to convert {@link OpinionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Opinion> createSpecification(OpinionCriteria criteria) {
        Specification<Opinion> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Opinion_.id));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), Opinion_.username));
            }
            if (criteria.getPageid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPageid(), Opinion_.pageid));
            }
            if (criteria.getContentid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContentid(), Opinion_.contentid));
            }
            if (criteria.getLangcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLangcode(), Opinion_.langcode));
            }
            if (criteria.getCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreated(), Opinion_.created));
            }
            if (criteria.getText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getText(), Opinion_.text));
            }
            if (criteria.getSentences() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSentences(), Opinion_.sentences));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore(), Opinion_.score));
            }
            if (criteria.getResult() != null) {
                specification = specification.and(buildSpecification(criteria.getResult(), Opinion_.result));
            }
        }
        return specification;
    }
}
