package cn.starteasy.uaa.web.rest;

import cn.starteasy.uaa.SeuaaApp;
import cn.starteasy.uaa.domain.Province;
import cn.starteasy.uaa.repository.ProvinceRepository;
import cn.starteasy.uaa.service.ProvinceService;
import cn.starteasy.uaa.service.dto.ProvinceDTO;
import cn.starteasy.uaa.service.mapper.ProvinceMapper;

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
 * Test class for the ProvinceResource REST controller.
 *
 * @see ProvinceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeuaaApp.class)
public class ProvinceResourceIntTest {
    private static final String DEFAULT_PROVINCE_NAME = "AAAAA";
    private static final String UPDATED_PROVINCE_NAME = "BBBBB";
    private static final String DEFAULT_PROVINCE_CODE = "AAAAA";
    private static final String UPDATED_PROVINCE_CODE = "BBBBB";

    @Inject
    private ProvinceRepository provinceRepository;

    @Inject
    private ProvinceMapper provinceMapper;

    @Inject
    private ProvinceService provinceService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProvinceMockMvc;

    private Province province;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProvinceResource provinceResource = new ProvinceResource();
        ReflectionTestUtils.setField(provinceResource, "provinceService", provinceService);
        this.restProvinceMockMvc = MockMvcBuilders.standaloneSetup(provinceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Province createEntity(EntityManager em) {
        Province province = new Province();
        province = new Province()
                .provinceName(DEFAULT_PROVINCE_NAME)
                .provinceCode(DEFAULT_PROVINCE_CODE);
        return province;
    }

    @Before
    public void initTest() {
        province = createEntity(em);
    }

    @Test
    @Transactional
    public void createProvince() throws Exception {
        int databaseSizeBeforeCreate = provinceRepository.findAll().size();

        // Create the Province
        ProvinceDTO provinceDTO = provinceMapper.provinceToProvinceDTO(province);

        restProvinceMockMvc.perform(post("/api/provinces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(provinceDTO)))
                .andExpect(status().isCreated());

        // Validate the Province in the database
        List<Province> provinces = provinceRepository.findAll();
        assertThat(provinces).hasSize(databaseSizeBeforeCreate + 1);
        Province testProvince = provinces.get(provinces.size() - 1);
        assertThat(testProvince.getProvinceName()).isEqualTo(DEFAULT_PROVINCE_NAME);
        assertThat(testProvince.getProvinceCode()).isEqualTo(DEFAULT_PROVINCE_CODE);
    }

    @Test
    @Transactional
    public void getAllProvinces() throws Exception {
        // Initialize the database
        provinceRepository.saveAndFlush(province);

        // Get all the provinces
        restProvinceMockMvc.perform(get("/api/provinces?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(province.getId().intValue())))
                .andExpect(jsonPath("$.[*].provinceName").value(hasItem(DEFAULT_PROVINCE_NAME.toString())))
                .andExpect(jsonPath("$.[*].provinceCode").value(hasItem(DEFAULT_PROVINCE_CODE.toString())));
    }

    @Test
    @Transactional
    public void getProvince() throws Exception {
        // Initialize the database
        provinceRepository.saveAndFlush(province);

        // Get the province
        restProvinceMockMvc.perform(get("/api/provinces/{id}", province.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(province.getId().intValue()))
            .andExpect(jsonPath("$.provinceName").value(DEFAULT_PROVINCE_NAME.toString()))
            .andExpect(jsonPath("$.provinceCode").value(DEFAULT_PROVINCE_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProvince() throws Exception {
        // Get the province
        restProvinceMockMvc.perform(get("/api/provinces/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProvince() throws Exception {
        // Initialize the database
        provinceRepository.saveAndFlush(province);
        int databaseSizeBeforeUpdate = provinceRepository.findAll().size();

        // Update the province
        Province updatedProvince = provinceRepository.findOne(province.getId());
        updatedProvince
                .provinceName(UPDATED_PROVINCE_NAME)
                .provinceCode(UPDATED_PROVINCE_CODE);
        ProvinceDTO provinceDTO = provinceMapper.provinceToProvinceDTO(updatedProvince);

        restProvinceMockMvc.perform(put("/api/provinces")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(provinceDTO)))
                .andExpect(status().isOk());

        // Validate the Province in the database
        List<Province> provinces = provinceRepository.findAll();
        assertThat(provinces).hasSize(databaseSizeBeforeUpdate);
        Province testProvince = provinces.get(provinces.size() - 1);
        assertThat(testProvince.getProvinceName()).isEqualTo(UPDATED_PROVINCE_NAME);
        assertThat(testProvince.getProvinceCode()).isEqualTo(UPDATED_PROVINCE_CODE);
    }

    @Test
    @Transactional
    public void deleteProvince() throws Exception {
        // Initialize the database
        provinceRepository.saveAndFlush(province);
        int databaseSizeBeforeDelete = provinceRepository.findAll().size();

        // Get the province
        restProvinceMockMvc.perform(delete("/api/provinces/{id}", province.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Province> provinces = provinceRepository.findAll();
        assertThat(provinces).hasSize(databaseSizeBeforeDelete - 1);
    }
}
