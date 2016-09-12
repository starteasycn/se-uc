package cn.starteasy.uaa.web.rest;

import cn.starteasy.uaa.SeuaaApp;
import cn.starteasy.uaa.domain.City;
import cn.starteasy.uaa.repository.CityRepository;
import cn.starteasy.uaa.service.CityService;
import cn.starteasy.uaa.service.dto.CityDTO;
import cn.starteasy.uaa.service.mapper.CityMapper;

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
 * Test class for the CityResource REST controller.
 *
 * @see CityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeuaaApp.class)
public class CityResourceIntTest {
    private static final String DEFAULT_CITY_NAME = "AAAAA";
    private static final String UPDATED_CITY_NAME = "BBBBB";
    private static final String DEFAULT_CITY_CODE = "AAAAA";
    private static final String UPDATED_CITY_CODE = "BBBBB";

    @Inject
    private CityRepository cityRepository;

    @Inject
    private CityMapper cityMapper;

    @Inject
    private CityService cityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCityMockMvc;

    private City city;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CityResource cityResource = new CityResource();
        ReflectionTestUtils.setField(cityResource, "cityService", cityService);
        this.restCityMockMvc = MockMvcBuilders.standaloneSetup(cityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static City createEntity(EntityManager em) {
        City city = new City();
        city = new City()
                .cityName(DEFAULT_CITY_NAME)
                .cityCode(DEFAULT_CITY_CODE);
        return city;
    }

    @Before
    public void initTest() {
        city = createEntity(em);
    }

    @Test
    @Transactional
    public void createCity() throws Exception {
        int databaseSizeBeforeCreate = cityRepository.findAll().size();

        // Create the City
        CityDTO cityDTO = cityMapper.cityToCityDTO(city);

        restCityMockMvc.perform(post("/api/cities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cityDTO)))
                .andExpect(status().isCreated());

        // Validate the City in the database
        List<City> cities = cityRepository.findAll();
        assertThat(cities).hasSize(databaseSizeBeforeCreate + 1);
        City testCity = cities.get(cities.size() - 1);
        assertThat(testCity.getCityName()).isEqualTo(DEFAULT_CITY_NAME);
        assertThat(testCity.getCityCode()).isEqualTo(DEFAULT_CITY_CODE);
    }

    @Test
    @Transactional
    public void getAllCities() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cities
        restCityMockMvc.perform(get("/api/cities?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(city.getId().intValue())))
                .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME.toString())))
                .andExpect(jsonPath("$.[*].cityCode").value(hasItem(DEFAULT_CITY_CODE.toString())));
    }

    @Test
    @Transactional
    public void getCity() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get the city
        restCityMockMvc.perform(get("/api/cities/{id}", city.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(city.getId().intValue()))
            .andExpect(jsonPath("$.cityName").value(DEFAULT_CITY_NAME.toString()))
            .andExpect(jsonPath("$.cityCode").value(DEFAULT_CITY_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCity() throws Exception {
        // Get the city
        restCityMockMvc.perform(get("/api/cities/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCity() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // Update the city
        City updatedCity = cityRepository.findOne(city.getId());
        updatedCity
                .cityName(UPDATED_CITY_NAME)
                .cityCode(UPDATED_CITY_CODE);
        CityDTO cityDTO = cityMapper.cityToCityDTO(updatedCity);

        restCityMockMvc.perform(put("/api/cities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cityDTO)))
                .andExpect(status().isOk());

        // Validate the City in the database
        List<City> cities = cityRepository.findAll();
        assertThat(cities).hasSize(databaseSizeBeforeUpdate);
        City testCity = cities.get(cities.size() - 1);
        assertThat(testCity.getCityName()).isEqualTo(UPDATED_CITY_NAME);
        assertThat(testCity.getCityCode()).isEqualTo(UPDATED_CITY_CODE);
    }

    @Test
    @Transactional
    public void deleteCity() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);
        int databaseSizeBeforeDelete = cityRepository.findAll().size();

        // Get the city
        restCityMockMvc.perform(delete("/api/cities/{id}", city.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<City> cities = cityRepository.findAll();
        assertThat(cities).hasSize(databaseSizeBeforeDelete - 1);
    }
}
