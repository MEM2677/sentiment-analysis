package com.entando.bundle.repository;

import com.entando.bundle.domain.Opinion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Opinion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long>, JpaSpecificationExecutor<Opinion> {}
