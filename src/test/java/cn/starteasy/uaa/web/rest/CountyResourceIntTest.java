package cn.starteasy.uaa.web.rest;

import cn.starteasy.uaa.SeuaaApp;
import cn.starteasy.uaa.domain.County;
import cn.starteasy.uaa.repository.CountyRepository;
import cn.starteasy.uaa.service.CountyService;
import cn.starteasy.uaa.service.dto.CountyDTO;
import cn.starteasy.uaa.service.mapper.CountyMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CountyResource REST controller.
 *
 * @see CountyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeuaaApp.class)
public class CountyResourceIntTest {
    private static final String DEFAULT_COUNTY_NAME = "AAAAA";
    private static final String UPDATED_COUNTY_NAME = "BBBBB";
    private static final String DEFAULT_COUNTY_CODE = "AAAAA";
    private static final String UPDATED_COUNTY_CODE = "BBBBB";

    @Inject
    private CountyRepository countyRepository;

    @Inject
    private CountyMapper countyMapper;

    @Inject
    private CountyService countyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCountyMockMvc;

    private County county;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CountyResource countyResource = new CountyResource();
        ReflectionTestUtils.setField(countyResource, "countyService", countyService);
        this.restCountyMockMvc = MockMvcBuilders.standaloneSetup(countyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static County createEntity(EntityManager em) {
        County county = new County();
        county = new County()
                .countyName(DEFAULT_COUNTY_NAME)
                .countyCode(DEFAULT_COUNTY_CODE);
        return county;
    }

    @Before
    public void initTest() {
        county = createEntity(em);
    }

    @Test
    @Transactional
    public void createCounty() throws Exception {
        int databaseSizeBeforeCreate = countyRepository.findAll().size();

        // Create the County
        CountyDTO countyDTO = countyMapper.countyToCountyDTO(county);

        restCountyMockMvc.perform(post("/api/counties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(countyDTO)))
                .andExpect(status().isCreated());

        // Validate the County in the database
        List<County> counties = countyRepository.findAll();
        assertThat(counties).hasSize(databaseSizeBeforeCreate + 1);
        County testCounty = counties.get(counties.size() - 1);
        assertThat(testCounty.getCountyName()).isEqualTo(DEFAULT_COUNTY_NAME);
        assertThat(testCounty.getCountyCode()).isEqualTo(DEFAULT_COUNTY_CODE);
    }

    @Test
    @Transactional
    public void getAllCounties() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);

        // Get all the counties
        restCountyMockMvc.perform(get("/api/counties?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(county.getId().intValue())))
                .andExpect(jsonPath("$.[*].countyName").value(hasItem(DEFAULT_COUNTY_NAME.toString())))
                .andExpect(jsonPath("$.[*].countyCode").value(hasItem(DEFAULT_COUNTY_CODE.toString())));
    }

    @Test
    @Transactional
    public void getCounty() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);

        // Get the county
        restCountyMockMvc.perform(get("/api/counties/{id}", county.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(county.getId().intValue()))
            .andExpect(jsonPath("$.countyName").value(DEFAULT_COUNTY_NAME.toString()))
            .andExpect(jsonPath("$.countyCode").value(DEFAULT_COUNTY_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCounty() throws Exception {
        // Get the county
        restCountyMockMvc.perform(get("/api/counties/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCounty() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);
        int databaseSizeBeforeUpdate = countyRepository.findAll().size();

        // Update the county
        County updatedCounty = countyRepository.findOne(county.getId());
        updatedCounty
                .countyName(UPDATED_COUNTY_NAME)
                .countyCode(UPDATED_COUNTY_CODE);
        CountyDTO countyDTO = countyMapper.countyToCountyDTO(updatedCounty);

        restCountyMockMvc.perform(put("/api/counties")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(countyDTO)))
                .andExpect(status().isOk());

        // Validate the County in the database
        List<County> counties = countyRepository.findAll();
        assertThat(counties).hasSize(databaseSizeBeforeUpdate);
        County testCounty = counties.get(counties.size() - 1);
        assertThat(testCounty.getCountyName()).isEqualTo(UPDATED_COUNTY_NAME);
        assertThat(testCounty.getCountyCode()).isEqualTo(UPDATED_COUNTY_CODE);
    }

    @Test
    @Transactional
    public void deleteCounty() throws Exception {
        // Initialize the database
        countyRepository.saveAndFlush(county);
        int databaseSizeBeforeDelete = countyRepository.findAll().size();

        // Get the county
        restCountyMockMvc.perform(delete("/api/counties/{id}", county.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<County> counties = countyRepository.findAll();
        assertThat(counties).hasSize(databaseSizeBeforeDelete - 1);
    }
}
