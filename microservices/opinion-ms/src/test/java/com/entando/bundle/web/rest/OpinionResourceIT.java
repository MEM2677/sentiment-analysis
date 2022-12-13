package com.entando.bundle.web.rest;

import static com.entando.bundle.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.entando.bundle.IntegrationTest;
import com.entando.bundle.domain.Opinion;
import com.entando.bundle.domain.enumeration.Sentiment;
import com.entando.bundle.repository.OpinionRepository;
import com.entando.bundle.service.criteria.OpinionCriteria;
import com.entando.bundle.service.dto.OpinionDTO;
import com.entando.bundle.service.mapper.OpinionMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OpinionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OpinionResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PAGEID = "AAAAAAAAAA";
    private static final String UPDATED_PAGEID = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENTID = "AAAAAAAAAA";
    private static final String UPDATED_CONTENTID = "BBBBBBBBBB";

    private static final String DEFAULT_LANGCODE = "AAAAAAAAAA";
    private static final String UPDATED_LANGCODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Integer DEFAULT_SENTENCES = 1;
    private static final Integer UPDATED_SENTENCES = 2;
    private static final Integer SMALLER_SENTENCES = 1 - 1;

    private static final Float DEFAULT_SCORE = 1F;
    private static final Float UPDATED_SCORE = 2F;
    private static final Float SMALLER_SCORE = 1F - 1F;

    private static final Sentiment DEFAULT_RESULT = Sentiment.VERY_NEGATIVE;
    private static final Sentiment UPDATED_RESULT = Sentiment.NEGATIVE;

    private static final String ENTITY_API_URL = "/api/opinions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OpinionRepository opinionRepository;

    @Autowired
    private OpinionMapper opinionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpinionMockMvc;

    private Opinion opinion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opinion createEntity(EntityManager em) {
        Opinion opinion = new Opinion()
            .username(DEFAULT_USERNAME)
            .pageid(DEFAULT_PAGEID)
            .contentid(DEFAULT_CONTENTID)
            .langcode(DEFAULT_LANGCODE)
            .created(DEFAULT_CREATED)
            .text(DEFAULT_TEXT)
            .sentences(DEFAULT_SENTENCES)
            .score(DEFAULT_SCORE)
            .result(DEFAULT_RESULT);
        return opinion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opinion createUpdatedEntity(EntityManager em) {
        Opinion opinion = new Opinion()
            .username(UPDATED_USERNAME)
            .pageid(UPDATED_PAGEID)
            .contentid(UPDATED_CONTENTID)
            .langcode(UPDATED_LANGCODE)
            .created(UPDATED_CREATED)
            .text(UPDATED_TEXT)
            .sentences(UPDATED_SENTENCES)
            .score(UPDATED_SCORE)
            .result(UPDATED_RESULT);
        return opinion;
    }

    @BeforeEach
    public void initTest() {
        opinion = createEntity(em);
    }

    @Test
    @Transactional
    void createOpinion() throws Exception {
        int databaseSizeBeforeCreate = opinionRepository.findAll().size();
        // Create the Opinion
        OpinionDTO opinionDTO = opinionMapper.toDto(opinion);
        restOpinionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opinionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Opinion in the database
        List<Opinion> opinionList = opinionRepository.findAll();
        assertThat(opinionList).hasSize(databaseSizeBeforeCreate + 1);
        Opinion testOpinion = opinionList.get(opinionList.size() - 1);
        assertThat(testOpinion.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testOpinion.getPageid()).isEqualTo(DEFAULT_PAGEID);
        assertThat(testOpinion.getContentid()).isEqualTo(DEFAULT_CONTENTID);
        assertThat(testOpinion.getLangcode()).isEqualTo(DEFAULT_LANGCODE);
        assertThat(testOpinion.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testOpinion.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testOpinion.getSentences()).isEqualTo(DEFAULT_SENTENCES);
        assertThat(testOpinion.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testOpinion.getResult()).isEqualTo(DEFAULT_RESULT);
    }

    @Test
    @Transactional
    void createOpinionWithExistingId() throws Exception {
        // Create the Opinion with an existing ID
        opinion.setId(1L);
        OpinionDTO opinionDTO = opinionMapper.toDto(opinion);

        int databaseSizeBeforeCreate = opinionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpinionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opinionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opinion in the database
        List<Opinion> opinionList = opinionRepository.findAll();
        assertThat(opinionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOpinions() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList
        restOpinionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opinion.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].pageid").value(hasItem(DEFAULT_PAGEID)))
            .andExpect(jsonPath("$.[*].contentid").value(hasItem(DEFAULT_CONTENTID)))
            .andExpect(jsonPath("$.[*].langcode").value(hasItem(DEFAULT_LANGCODE)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].sentences").value(hasItem(DEFAULT_SENTENCES)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())));
    }

    @Test
    @Transactional
    void getOpinion() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get the opinion
        restOpinionMockMvc
            .perform(get(ENTITY_API_URL_ID, opinion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(opinion.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.pageid").value(DEFAULT_PAGEID))
            .andExpect(jsonPath("$.contentid").value(DEFAULT_CONTENTID))
            .andExpect(jsonPath("$.langcode").value(DEFAULT_LANGCODE))
            .andExpect(jsonPath("$.created").value(sameInstant(DEFAULT_CREATED)))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.sentences").value(DEFAULT_SENTENCES))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()));
    }

    @Test
    @Transactional
    void getOpinionsByIdFiltering() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        Long id = opinion.getId();

        defaultOpinionShouldBeFound("id.equals=" + id);
        defaultOpinionShouldNotBeFound("id.notEquals=" + id);

        defaultOpinionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOpinionShouldNotBeFound("id.greaterThan=" + id);

        defaultOpinionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOpinionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOpinionsByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where username equals to DEFAULT_USERNAME
        defaultOpinionShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the opinionList where username equals to UPDATED_USERNAME
        defaultOpinionShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllOpinionsByUsernameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where username not equals to DEFAULT_USERNAME
        defaultOpinionShouldNotBeFound("username.notEquals=" + DEFAULT_USERNAME);

        // Get all the opinionList where username not equals to UPDATED_USERNAME
        defaultOpinionShouldBeFound("username.notEquals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllOpinionsByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultOpinionShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the opinionList where username equals to UPDATED_USERNAME
        defaultOpinionShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllOpinionsByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where username is not null
        defaultOpinionShouldBeFound("username.specified=true");

        // Get all the opinionList where username is null
        defaultOpinionShouldNotBeFound("username.specified=false");
    }

    @Test
    @Transactional
    void getAllOpinionsByUsernameContainsSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where username contains DEFAULT_USERNAME
        defaultOpinionShouldBeFound("username.contains=" + DEFAULT_USERNAME);

        // Get all the opinionList where username contains UPDATED_USERNAME
        defaultOpinionShouldNotBeFound("username.contains=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllOpinionsByUsernameNotContainsSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where username does not contain DEFAULT_USERNAME
        defaultOpinionShouldNotBeFound("username.doesNotContain=" + DEFAULT_USERNAME);

        // Get all the opinionList where username does not contain UPDATED_USERNAME
        defaultOpinionShouldBeFound("username.doesNotContain=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllOpinionsByPageidIsEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where pageid equals to DEFAULT_PAGEID
        defaultOpinionShouldBeFound("pageid.equals=" + DEFAULT_PAGEID);

        // Get all the opinionList where pageid equals to UPDATED_PAGEID
        defaultOpinionShouldNotBeFound("pageid.equals=" + UPDATED_PAGEID);
    }

    @Test
    @Transactional
    void getAllOpinionsByPageidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where pageid not equals to DEFAULT_PAGEID
        defaultOpinionShouldNotBeFound("pageid.notEquals=" + DEFAULT_PAGEID);

        // Get all the opinionList where pageid not equals to UPDATED_PAGEID
        defaultOpinionShouldBeFound("pageid.notEquals=" + UPDATED_PAGEID);
    }

    @Test
    @Transactional
    void getAllOpinionsByPageidIsInShouldWork() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where pageid in DEFAULT_PAGEID or UPDATED_PAGEID
        defaultOpinionShouldBeFound("pageid.in=" + DEFAULT_PAGEID + "," + UPDATED_PAGEID);

        // Get all the opinionList where pageid equals to UPDATED_PAGEID
        defaultOpinionShouldNotBeFound("pageid.in=" + UPDATED_PAGEID);
    }

    @Test
    @Transactional
    void getAllOpinionsByPageidIsNullOrNotNull() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where pageid is not null
        defaultOpinionShouldBeFound("pageid.specified=true");

        // Get all the opinionList where pageid is null
        defaultOpinionShouldNotBeFound("pageid.specified=false");
    }

    @Test
    @Transactional
    void getAllOpinionsByPageidContainsSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where pageid contains DEFAULT_PAGEID
        defaultOpinionShouldBeFound("pageid.contains=" + DEFAULT_PAGEID);

        // Get all the opinionList where pageid contains UPDATED_PAGEID
        defaultOpinionShouldNotBeFound("pageid.contains=" + UPDATED_PAGEID);
    }

    @Test
    @Transactional
    void getAllOpinionsByPageidNotContainsSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where pageid does not contain DEFAULT_PAGEID
        defaultOpinionShouldNotBeFound("pageid.doesNotContain=" + DEFAULT_PAGEID);

        // Get all the opinionList where pageid does not contain UPDATED_PAGEID
        defaultOpinionShouldBeFound("pageid.doesNotContain=" + UPDATED_PAGEID);
    }

    @Test
    @Transactional
    void getAllOpinionsByContentidIsEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where contentid equals to DEFAULT_CONTENTID
        defaultOpinionShouldBeFound("contentid.equals=" + DEFAULT_CONTENTID);

        // Get all the opinionList where contentid equals to UPDATED_CONTENTID
        defaultOpinionShouldNotBeFound("contentid.equals=" + UPDATED_CONTENTID);
    }

    @Test
    @Transactional
    void getAllOpinionsByContentidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where contentid not equals to DEFAULT_CONTENTID
        defaultOpinionShouldNotBeFound("contentid.notEquals=" + DEFAULT_CONTENTID);

        // Get all the opinionList where contentid not equals to UPDATED_CONTENTID
        defaultOpinionShouldBeFound("contentid.notEquals=" + UPDATED_CONTENTID);
    }

    @Test
    @Transactional
    void getAllOpinionsByContentidIsInShouldWork() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where contentid in DEFAULT_CONTENTID or UPDATED_CONTENTID
        defaultOpinionShouldBeFound("contentid.in=" + DEFAULT_CONTENTID + "," + UPDATED_CONTENTID);

        // Get all the opinionList where contentid equals to UPDATED_CONTENTID
        defaultOpinionShouldNotBeFound("contentid.in=" + UPDATED_CONTENTID);
    }

    @Test
    @Transactional
    void getAllOpinionsByContentidIsNullOrNotNull() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where contentid is not null
        defaultOpinionShouldBeFound("contentid.specified=true");

        // Get all the opinionList where contentid is null
        defaultOpinionShouldNotBeFound("contentid.specified=false");
    }

    @Test
    @Transactional
    void getAllOpinionsByContentidContainsSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where contentid contains DEFAULT_CONTENTID
        defaultOpinionShouldBeFound("contentid.contains=" + DEFAULT_CONTENTID);

        // Get all the opinionList where contentid contains UPDATED_CONTENTID
        defaultOpinionShouldNotBeFound("contentid.contains=" + UPDATED_CONTENTID);
    }

    @Test
    @Transactional
    void getAllOpinionsByContentidNotContainsSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where contentid does not contain DEFAULT_CONTENTID
        defaultOpinionShouldNotBeFound("contentid.doesNotContain=" + DEFAULT_CONTENTID);

        // Get all the opinionList where contentid does not contain UPDATED_CONTENTID
        defaultOpinionShouldBeFound("contentid.doesNotContain=" + UPDATED_CONTENTID);
    }

    @Test
    @Transactional
    void getAllOpinionsByLangcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where langcode equals to DEFAULT_LANGCODE
        defaultOpinionShouldBeFound("langcode.equals=" + DEFAULT_LANGCODE);

        // Get all the opinionList where langcode equals to UPDATED_LANGCODE
        defaultOpinionShouldNotBeFound("langcode.equals=" + UPDATED_LANGCODE);
    }

    @Test
    @Transactional
    void getAllOpinionsByLangcodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where langcode not equals to DEFAULT_LANGCODE
        defaultOpinionShouldNotBeFound("langcode.notEquals=" + DEFAULT_LANGCODE);

        // Get all the opinionList where langcode not equals to UPDATED_LANGCODE
        defaultOpinionShouldBeFound("langcode.notEquals=" + UPDATED_LANGCODE);
    }

    @Test
    @Transactional
    void getAllOpinionsByLangcodeIsInShouldWork() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where langcode in DEFAULT_LANGCODE or UPDATED_LANGCODE
        defaultOpinionShouldBeFound("langcode.in=" + DEFAULT_LANGCODE + "," + UPDATED_LANGCODE);

        // Get all the opinionList where langcode equals to UPDATED_LANGCODE
        defaultOpinionShouldNotBeFound("langcode.in=" + UPDATED_LANGCODE);
    }

    @Test
    @Transactional
    void getAllOpinionsByLangcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where langcode is not null
        defaultOpinionShouldBeFound("langcode.specified=true");

        // Get all the opinionList where langcode is null
        defaultOpinionShouldNotBeFound("langcode.specified=false");
    }

    @Test
    @Transactional
    void getAllOpinionsByLangcodeContainsSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where langcode contains DEFAULT_LANGCODE
        defaultOpinionShouldBeFound("langcode.contains=" + DEFAULT_LANGCODE);

        // Get all the opinionList where langcode contains UPDATED_LANGCODE
        defaultOpinionShouldNotBeFound("langcode.contains=" + UPDATED_LANGCODE);
    }

    @Test
    @Transactional
    void getAllOpinionsByLangcodeNotContainsSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where langcode does not contain DEFAULT_LANGCODE
        defaultOpinionShouldNotBeFound("langcode.doesNotContain=" + DEFAULT_LANGCODE);

        // Get all the opinionList where langcode does not contain UPDATED_LANGCODE
        defaultOpinionShouldBeFound("langcode.doesNotContain=" + UPDATED_LANGCODE);
    }

    @Test
    @Transactional
    void getAllOpinionsByCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where created equals to DEFAULT_CREATED
        defaultOpinionShouldBeFound("created.equals=" + DEFAULT_CREATED);

        // Get all the opinionList where created equals to UPDATED_CREATED
        defaultOpinionShouldNotBeFound("created.equals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    void getAllOpinionsByCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where created not equals to DEFAULT_CREATED
        defaultOpinionShouldNotBeFound("created.notEquals=" + DEFAULT_CREATED);

        // Get all the opinionList where created not equals to UPDATED_CREATED
        defaultOpinionShouldBeFound("created.notEquals=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    void getAllOpinionsByCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where created in DEFAULT_CREATED or UPDATED_CREATED
        defaultOpinionShouldBeFound("created.in=" + DEFAULT_CREATED + "," + UPDATED_CREATED);

        // Get all the opinionList where created equals to UPDATED_CREATED
        defaultOpinionShouldNotBeFound("created.in=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    void getAllOpinionsByCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where created is not null
        defaultOpinionShouldBeFound("created.specified=true");

        // Get all the opinionList where created is null
        defaultOpinionShouldNotBeFound("created.specified=false");
    }

    @Test
    @Transactional
    void getAllOpinionsByCreatedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where created is greater than or equal to DEFAULT_CREATED
        defaultOpinionShouldBeFound("created.greaterThanOrEqual=" + DEFAULT_CREATED);

        // Get all the opinionList where created is greater than or equal to UPDATED_CREATED
        defaultOpinionShouldNotBeFound("created.greaterThanOrEqual=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    void getAllOpinionsByCreatedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where created is less than or equal to DEFAULT_CREATED
        defaultOpinionShouldBeFound("created.lessThanOrEqual=" + DEFAULT_CREATED);

        // Get all the opinionList where created is less than or equal to SMALLER_CREATED
        defaultOpinionShouldNotBeFound("created.lessThanOrEqual=" + SMALLER_CREATED);
    }

    @Test
    @Transactional
    void getAllOpinionsByCreatedIsLessThanSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where created is less than DEFAULT_CREATED
        defaultOpinionShouldNotBeFound("created.lessThan=" + DEFAULT_CREATED);

        // Get all the opinionList where created is less than UPDATED_CREATED
        defaultOpinionShouldBeFound("created.lessThan=" + UPDATED_CREATED);
    }

    @Test
    @Transactional
    void getAllOpinionsByCreatedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where created is greater than DEFAULT_CREATED
        defaultOpinionShouldNotBeFound("created.greaterThan=" + DEFAULT_CREATED);

        // Get all the opinionList where created is greater than SMALLER_CREATED
        defaultOpinionShouldBeFound("created.greaterThan=" + SMALLER_CREATED);
    }

    @Test
    @Transactional
    void getAllOpinionsByTextIsEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where text equals to DEFAULT_TEXT
        defaultOpinionShouldBeFound("text.equals=" + DEFAULT_TEXT);

        // Get all the opinionList where text equals to UPDATED_TEXT
        defaultOpinionShouldNotBeFound("text.equals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllOpinionsByTextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where text not equals to DEFAULT_TEXT
        defaultOpinionShouldNotBeFound("text.notEquals=" + DEFAULT_TEXT);

        // Get all the opinionList where text not equals to UPDATED_TEXT
        defaultOpinionShouldBeFound("text.notEquals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllOpinionsByTextIsInShouldWork() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where text in DEFAULT_TEXT or UPDATED_TEXT
        defaultOpinionShouldBeFound("text.in=" + DEFAULT_TEXT + "," + UPDATED_TEXT);

        // Get all the opinionList where text equals to UPDATED_TEXT
        defaultOpinionShouldNotBeFound("text.in=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllOpinionsByTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where text is not null
        defaultOpinionShouldBeFound("text.specified=true");

        // Get all the opinionList where text is null
        defaultOpinionShouldNotBeFound("text.specified=false");
    }

    @Test
    @Transactional
    void getAllOpinionsByTextContainsSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where text contains DEFAULT_TEXT
        defaultOpinionShouldBeFound("text.contains=" + DEFAULT_TEXT);

        // Get all the opinionList where text contains UPDATED_TEXT
        defaultOpinionShouldNotBeFound("text.contains=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllOpinionsByTextNotContainsSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where text does not contain DEFAULT_TEXT
        defaultOpinionShouldNotBeFound("text.doesNotContain=" + DEFAULT_TEXT);

        // Get all the opinionList where text does not contain UPDATED_TEXT
        defaultOpinionShouldBeFound("text.doesNotContain=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllOpinionsBySentencesIsEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where sentences equals to DEFAULT_SENTENCES
        defaultOpinionShouldBeFound("sentences.equals=" + DEFAULT_SENTENCES);

        // Get all the opinionList where sentences equals to UPDATED_SENTENCES
        defaultOpinionShouldNotBeFound("sentences.equals=" + UPDATED_SENTENCES);
    }

    @Test
    @Transactional
    void getAllOpinionsBySentencesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where sentences not equals to DEFAULT_SENTENCES
        defaultOpinionShouldNotBeFound("sentences.notEquals=" + DEFAULT_SENTENCES);

        // Get all the opinionList where sentences not equals to UPDATED_SENTENCES
        defaultOpinionShouldBeFound("sentences.notEquals=" + UPDATED_SENTENCES);
    }

    @Test
    @Transactional
    void getAllOpinionsBySentencesIsInShouldWork() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where sentences in DEFAULT_SENTENCES or UPDATED_SENTENCES
        defaultOpinionShouldBeFound("sentences.in=" + DEFAULT_SENTENCES + "," + UPDATED_SENTENCES);

        // Get all the opinionList where sentences equals to UPDATED_SENTENCES
        defaultOpinionShouldNotBeFound("sentences.in=" + UPDATED_SENTENCES);
    }

    @Test
    @Transactional
    void getAllOpinionsBySentencesIsNullOrNotNull() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where sentences is not null
        defaultOpinionShouldBeFound("sentences.specified=true");

        // Get all the opinionList where sentences is null
        defaultOpinionShouldNotBeFound("sentences.specified=false");
    }

    @Test
    @Transactional
    void getAllOpinionsBySentencesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where sentences is greater than or equal to DEFAULT_SENTENCES
        defaultOpinionShouldBeFound("sentences.greaterThanOrEqual=" + DEFAULT_SENTENCES);

        // Get all the opinionList where sentences is greater than or equal to UPDATED_SENTENCES
        defaultOpinionShouldNotBeFound("sentences.greaterThanOrEqual=" + UPDATED_SENTENCES);
    }

    @Test
    @Transactional
    void getAllOpinionsBySentencesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where sentences is less than or equal to DEFAULT_SENTENCES
        defaultOpinionShouldBeFound("sentences.lessThanOrEqual=" + DEFAULT_SENTENCES);

        // Get all the opinionList where sentences is less than or equal to SMALLER_SENTENCES
        defaultOpinionShouldNotBeFound("sentences.lessThanOrEqual=" + SMALLER_SENTENCES);
    }

    @Test
    @Transactional
    void getAllOpinionsBySentencesIsLessThanSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where sentences is less than DEFAULT_SENTENCES
        defaultOpinionShouldNotBeFound("sentences.lessThan=" + DEFAULT_SENTENCES);

        // Get all the opinionList where sentences is less than UPDATED_SENTENCES
        defaultOpinionShouldBeFound("sentences.lessThan=" + UPDATED_SENTENCES);
    }

    @Test
    @Transactional
    void getAllOpinionsBySentencesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where sentences is greater than DEFAULT_SENTENCES
        defaultOpinionShouldNotBeFound("sentences.greaterThan=" + DEFAULT_SENTENCES);

        // Get all the opinionList where sentences is greater than SMALLER_SENTENCES
        defaultOpinionShouldBeFound("sentences.greaterThan=" + SMALLER_SENTENCES);
    }

    @Test
    @Transactional
    void getAllOpinionsByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where score equals to DEFAULT_SCORE
        defaultOpinionShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the opinionList where score equals to UPDATED_SCORE
        defaultOpinionShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllOpinionsByScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where score not equals to DEFAULT_SCORE
        defaultOpinionShouldNotBeFound("score.notEquals=" + DEFAULT_SCORE);

        // Get all the opinionList where score not equals to UPDATED_SCORE
        defaultOpinionShouldBeFound("score.notEquals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllOpinionsByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultOpinionShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the opinionList where score equals to UPDATED_SCORE
        defaultOpinionShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllOpinionsByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where score is not null
        defaultOpinionShouldBeFound("score.specified=true");

        // Get all the opinionList where score is null
        defaultOpinionShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    void getAllOpinionsByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where score is greater than or equal to DEFAULT_SCORE
        defaultOpinionShouldBeFound("score.greaterThanOrEqual=" + DEFAULT_SCORE);

        // Get all the opinionList where score is greater than or equal to UPDATED_SCORE
        defaultOpinionShouldNotBeFound("score.greaterThanOrEqual=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllOpinionsByScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where score is less than or equal to DEFAULT_SCORE
        defaultOpinionShouldBeFound("score.lessThanOrEqual=" + DEFAULT_SCORE);

        // Get all the opinionList where score is less than or equal to SMALLER_SCORE
        defaultOpinionShouldNotBeFound("score.lessThanOrEqual=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    void getAllOpinionsByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where score is less than DEFAULT_SCORE
        defaultOpinionShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the opinionList where score is less than UPDATED_SCORE
        defaultOpinionShouldBeFound("score.lessThan=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    void getAllOpinionsByScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where score is greater than DEFAULT_SCORE
        defaultOpinionShouldNotBeFound("score.greaterThan=" + DEFAULT_SCORE);

        // Get all the opinionList where score is greater than SMALLER_SCORE
        defaultOpinionShouldBeFound("score.greaterThan=" + SMALLER_SCORE);
    }

    @Test
    @Transactional
    void getAllOpinionsByResultIsEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where result equals to DEFAULT_RESULT
        defaultOpinionShouldBeFound("result.equals=" + DEFAULT_RESULT);

        // Get all the opinionList where result equals to UPDATED_RESULT
        defaultOpinionShouldNotBeFound("result.equals=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    void getAllOpinionsByResultIsNotEqualToSomething() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where result not equals to DEFAULT_RESULT
        defaultOpinionShouldNotBeFound("result.notEquals=" + DEFAULT_RESULT);

        // Get all the opinionList where result not equals to UPDATED_RESULT
        defaultOpinionShouldBeFound("result.notEquals=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    void getAllOpinionsByResultIsInShouldWork() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where result in DEFAULT_RESULT or UPDATED_RESULT
        defaultOpinionShouldBeFound("result.in=" + DEFAULT_RESULT + "," + UPDATED_RESULT);

        // Get all the opinionList where result equals to UPDATED_RESULT
        defaultOpinionShouldNotBeFound("result.in=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    void getAllOpinionsByResultIsNullOrNotNull() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        // Get all the opinionList where result is not null
        defaultOpinionShouldBeFound("result.specified=true");

        // Get all the opinionList where result is null
        defaultOpinionShouldNotBeFound("result.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOpinionShouldBeFound(String filter) throws Exception {
        restOpinionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opinion.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].pageid").value(hasItem(DEFAULT_PAGEID)))
            .andExpect(jsonPath("$.[*].contentid").value(hasItem(DEFAULT_CONTENTID)))
            .andExpect(jsonPath("$.[*].langcode").value(hasItem(DEFAULT_LANGCODE)))
            .andExpect(jsonPath("$.[*].created").value(hasItem(sameInstant(DEFAULT_CREATED))))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].sentences").value(hasItem(DEFAULT_SENTENCES)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())));

        // Check, that the count call also returns 1
        restOpinionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOpinionShouldNotBeFound(String filter) throws Exception {
        restOpinionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOpinionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOpinion() throws Exception {
        // Get the opinion
        restOpinionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOpinion() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        int databaseSizeBeforeUpdate = opinionRepository.findAll().size();

        // Update the opinion
        Opinion updatedOpinion = opinionRepository.findById(opinion.getId()).get();
        // Disconnect from session so that the updates on updatedOpinion are not directly saved in db
        em.detach(updatedOpinion);
        updatedOpinion
            .username(UPDATED_USERNAME)
            .pageid(UPDATED_PAGEID)
            .contentid(UPDATED_CONTENTID)
            .langcode(UPDATED_LANGCODE)
            .created(UPDATED_CREATED)
            .text(UPDATED_TEXT)
            .sentences(UPDATED_SENTENCES)
            .score(UPDATED_SCORE)
            .result(UPDATED_RESULT);
        OpinionDTO opinionDTO = opinionMapper.toDto(updatedOpinion);

        restOpinionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opinionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opinionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Opinion in the database
        List<Opinion> opinionList = opinionRepository.findAll();
        assertThat(opinionList).hasSize(databaseSizeBeforeUpdate);
        Opinion testOpinion = opinionList.get(opinionList.size() - 1);
        assertThat(testOpinion.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testOpinion.getPageid()).isEqualTo(UPDATED_PAGEID);
        assertThat(testOpinion.getContentid()).isEqualTo(UPDATED_CONTENTID);
        assertThat(testOpinion.getLangcode()).isEqualTo(UPDATED_LANGCODE);
        assertThat(testOpinion.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testOpinion.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testOpinion.getSentences()).isEqualTo(UPDATED_SENTENCES);
        assertThat(testOpinion.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testOpinion.getResult()).isEqualTo(UPDATED_RESULT);
    }

    @Test
    @Transactional
    void putNonExistingOpinion() throws Exception {
        int databaseSizeBeforeUpdate = opinionRepository.findAll().size();
        opinion.setId(count.incrementAndGet());

        // Create the Opinion
        OpinionDTO opinionDTO = opinionMapper.toDto(opinion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpinionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opinionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opinionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opinion in the database
        List<Opinion> opinionList = opinionRepository.findAll();
        assertThat(opinionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOpinion() throws Exception {
        int databaseSizeBeforeUpdate = opinionRepository.findAll().size();
        opinion.setId(count.incrementAndGet());

        // Create the Opinion
        OpinionDTO opinionDTO = opinionMapper.toDto(opinion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpinionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opinionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opinion in the database
        List<Opinion> opinionList = opinionRepository.findAll();
        assertThat(opinionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOpinion() throws Exception {
        int databaseSizeBeforeUpdate = opinionRepository.findAll().size();
        opinion.setId(count.incrementAndGet());

        // Create the Opinion
        OpinionDTO opinionDTO = opinionMapper.toDto(opinion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpinionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opinionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Opinion in the database
        List<Opinion> opinionList = opinionRepository.findAll();
        assertThat(opinionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOpinionWithPatch() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        int databaseSizeBeforeUpdate = opinionRepository.findAll().size();

        // Update the opinion using partial update
        Opinion partialUpdatedOpinion = new Opinion();
        partialUpdatedOpinion.setId(opinion.getId());

        partialUpdatedOpinion
            .username(UPDATED_USERNAME)
            .langcode(UPDATED_LANGCODE)
            .sentences(UPDATED_SENTENCES)
            .score(UPDATED_SCORE)
            .result(UPDATED_RESULT);

        restOpinionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpinion.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOpinion))
            )
            .andExpect(status().isOk());

        // Validate the Opinion in the database
        List<Opinion> opinionList = opinionRepository.findAll();
        assertThat(opinionList).hasSize(databaseSizeBeforeUpdate);
        Opinion testOpinion = opinionList.get(opinionList.size() - 1);
        assertThat(testOpinion.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testOpinion.getPageid()).isEqualTo(DEFAULT_PAGEID);
        assertThat(testOpinion.getContentid()).isEqualTo(DEFAULT_CONTENTID);
        assertThat(testOpinion.getLangcode()).isEqualTo(UPDATED_LANGCODE);
        assertThat(testOpinion.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testOpinion.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testOpinion.getSentences()).isEqualTo(UPDATED_SENTENCES);
        assertThat(testOpinion.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testOpinion.getResult()).isEqualTo(UPDATED_RESULT);
    }

    @Test
    @Transactional
    void fullUpdateOpinionWithPatch() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        int databaseSizeBeforeUpdate = opinionRepository.findAll().size();

        // Update the opinion using partial update
        Opinion partialUpdatedOpinion = new Opinion();
        partialUpdatedOpinion.setId(opinion.getId());

        partialUpdatedOpinion
            .username(UPDATED_USERNAME)
            .pageid(UPDATED_PAGEID)
            .contentid(UPDATED_CONTENTID)
            .langcode(UPDATED_LANGCODE)
            .created(UPDATED_CREATED)
            .text(UPDATED_TEXT)
            .sentences(UPDATED_SENTENCES)
            .score(UPDATED_SCORE)
            .result(UPDATED_RESULT);

        restOpinionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpinion.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOpinion))
            )
            .andExpect(status().isOk());

        // Validate the Opinion in the database
        List<Opinion> opinionList = opinionRepository.findAll();
        assertThat(opinionList).hasSize(databaseSizeBeforeUpdate);
        Opinion testOpinion = opinionList.get(opinionList.size() - 1);
        assertThat(testOpinion.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testOpinion.getPageid()).isEqualTo(UPDATED_PAGEID);
        assertThat(testOpinion.getContentid()).isEqualTo(UPDATED_CONTENTID);
        assertThat(testOpinion.getLangcode()).isEqualTo(UPDATED_LANGCODE);
        assertThat(testOpinion.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testOpinion.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testOpinion.getSentences()).isEqualTo(UPDATED_SENTENCES);
        assertThat(testOpinion.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testOpinion.getResult()).isEqualTo(UPDATED_RESULT);
    }

    @Test
    @Transactional
    void patchNonExistingOpinion() throws Exception {
        int databaseSizeBeforeUpdate = opinionRepository.findAll().size();
        opinion.setId(count.incrementAndGet());

        // Create the Opinion
        OpinionDTO opinionDTO = opinionMapper.toDto(opinion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpinionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, opinionDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opinionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opinion in the database
        List<Opinion> opinionList = opinionRepository.findAll();
        assertThat(opinionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOpinion() throws Exception {
        int databaseSizeBeforeUpdate = opinionRepository.findAll().size();
        opinion.setId(count.incrementAndGet());

        // Create the Opinion
        OpinionDTO opinionDTO = opinionMapper.toDto(opinion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpinionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opinionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opinion in the database
        List<Opinion> opinionList = opinionRepository.findAll();
        assertThat(opinionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOpinion() throws Exception {
        int databaseSizeBeforeUpdate = opinionRepository.findAll().size();
        opinion.setId(count.incrementAndGet());

        // Create the Opinion
        OpinionDTO opinionDTO = opinionMapper.toDto(opinion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpinionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opinionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Opinion in the database
        List<Opinion> opinionList = opinionRepository.findAll();
        assertThat(opinionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOpinion() throws Exception {
        // Initialize the database
        opinionRepository.saveAndFlush(opinion);

        int databaseSizeBeforeDelete = opinionRepository.findAll().size();

        // Delete the opinion
        restOpinionMockMvc
            .perform(delete(ENTITY_API_URL_ID, opinion.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Opinion> opinionList = opinionRepository.findAll();
        assertThat(opinionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
