package com.entando.bundle.service.impl;

import com.entando.bundle.domain.Opinion;
import com.entando.bundle.repository.OpinionRepository;
import com.entando.bundle.service.OpinionService;
import com.entando.bundle.service.dto.OpinionDTO;
import com.entando.bundle.service.mapper.OpinionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Opinion}.
 */
@Service
@Transactional
public class OpinionServiceImpl implements OpinionService {

    private final Logger log = LoggerFactory.getLogger(OpinionServiceImpl.class);

    private final OpinionRepository opinionRepository;

    private final OpinionMapper opinionMapper;

    public OpinionServiceImpl(OpinionRepository opinionRepository, OpinionMapper opinionMapper) {
        this.opinionRepository = opinionRepository;
        this.opinionMapper = opinionMapper;
    }

    @Override
    public OpinionDTO save(OpinionDTO opinionDTO) {
        log.debug("Request to save Opinion : {}", opinionDTO);
        Opinion opinion = opinionMapper.toEntity(opinionDTO);
        opinion = opinionRepository.save(opinion);
        return opinionMapper.toDto(opinion);
    }

    @Override
    public Optional<OpinionDTO> partialUpdate(OpinionDTO opinionDTO) {
        log.debug("Request to partially update Opinion : {}", opinionDTO);

        return opinionRepository
            .findById(opinionDTO.getId())
            .map(existingOpinion -> {
                opinionMapper.partialUpdate(existingOpinion, opinionDTO);

                return existingOpinion;
            })
            .map(opinionRepository::save)
            .map(opinionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OpinionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Opinions");
        return opinionRepository.findAll(pageable).map(opinionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OpinionDTO> findOne(Long id) {
        log.debug("Request to get Opinion : {}", id);
        return opinionRepository.findById(id).map(opinionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Opinion : {}", id);
        opinionRepository.deleteById(id);
    }
}
