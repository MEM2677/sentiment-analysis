package com.entando.bundle.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpinionMapperTest {

    private OpinionMapper opinionMapper;

    @BeforeEach
    public void setUp() {
        opinionMapper = new OpinionMapperImpl();
    }
}
