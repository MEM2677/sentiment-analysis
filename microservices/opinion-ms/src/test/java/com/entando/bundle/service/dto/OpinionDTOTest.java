package com.entando.bundle.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.entando.bundle.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpinionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OpinionDTO.class);
        OpinionDTO opinionDTO1 = new OpinionDTO();
        opinionDTO1.setId(1L);
        OpinionDTO opinionDTO2 = new OpinionDTO();
        assertThat(opinionDTO1).isNotEqualTo(opinionDTO2);
        opinionDTO2.setId(opinionDTO1.getId());
        assertThat(opinionDTO1).isEqualTo(opinionDTO2);
        opinionDTO2.setId(2L);
        assertThat(opinionDTO1).isNotEqualTo(opinionDTO2);
        opinionDTO1.setId(null);
        assertThat(opinionDTO1).isNotEqualTo(opinionDTO2);
    }
}
