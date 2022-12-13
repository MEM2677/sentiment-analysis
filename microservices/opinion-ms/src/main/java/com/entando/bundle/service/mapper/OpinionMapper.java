package com.entando.bundle.service.mapper;

import com.entando.bundle.domain.*;
import com.entando.bundle.service.dto.OpinionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Opinion} and its DTO {@link OpinionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OpinionMapper extends EntityMapper<OpinionDTO, Opinion> {}
