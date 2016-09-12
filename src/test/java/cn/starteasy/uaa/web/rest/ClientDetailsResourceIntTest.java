package cn.starteasy.uaa.web.rest;

import cn.starteasy.uaa.SeuaaApp;
import cn.starteasy.uaa.domain.ClientDetails;
import cn.starteasy.uaa.repository.ClientDetailsRepository;
import cn.starteasy.uaa.service.ClientDetailsService;
import cn.starteasy.uaa.service.dto.ClientDetailsDTO;
import cn.starteasy.uaa.service.mapper.ClientDetailsMapper;

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
 * Test class for the ClientDetailsResource REST controller.
 *
 * @see ClientDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeuaaApp.class)
public class ClientDetailsResourceIntTest {
    private static final String DEFAULT_CLIENT_ID = "AAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBB";

    @Inject
    private ClientDetailsRepository clientDetailsRepository;

    @Inject
    private ClientDetailsMapper clientDetailsMapper;

    @Inject
    private ClientDetailsService clientDetailsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restClientDetailsMockMvc;

    private ClientDetails clientDetails;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClientDetailsResource clientDetailsResource = new ClientDetailsResource();
        ReflectionTestUtils.setField(clientDetailsResource, "clientDetailsService", clientDetailsService);
        this.restClientDetailsMockMvc = MockMvcBuilders.standaloneSetup(clientDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientDetails createEntity(EntityManager em) {
        ClientDetails clientDetails = new ClientDetails();
        clientDetails = new ClientDetails()
                .clientId(DEFAULT_CLIENT_ID);
        return clientDetails;
    }

    @Before
    public void initTest() {
        clientDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientDetails() throws Exception {
        int databaseSizeBeforeCreate = clientDetailsRepository.findAll().size();

        // Create the ClientDetails
        ClientDetailsDTO clientDetailsDTO = clientDetailsMapper.clientDetailsToClientDetailsDTO(clientDetails);

        restClientDetailsMockMvc.perform(post("/api/client-details")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientDetailsDTO)))
                .andExpect(status().isCreated());

        // Validate the ClientDetails in the database
        List<ClientDetails> clientDetails = clientDetailsRepository.findAll();
        assertThat(clientDetails).hasSize(databaseSizeBeforeCreate + 1);
        ClientDetails testClientDetails = clientDetails.get(clientDetails.size() - 1);
        assertThat(testClientDetails.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
    }

    @Test
    @Transactional
    public void getAllClientDetails() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetails
        restClientDetailsMockMvc.perform(get("/api/client-details?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(clientDetails.getId().intValue())))
                .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.toString())));
    }

    @Test
    @Transactional
    public void getClientDetails() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get the clientDetails
        restClientDetailsMockMvc.perform(get("/api/client-details/{id}", clientDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientDetails.getId().intValue()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClientDetails() throws Exception {
        // Get the clientDetails
        restClientDetailsMockMvc.perform(get("/api/client-details/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientDetails() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);
        int databaseSizeBeforeUpdate = clientDetailsRepository.findAll().size();

        // Update the clientDetails
        ClientDetails updatedClientDetails = clientDetailsRepository.findOne(clientDetails.getId());
        updatedClientDetails
                .clientId(UPDATED_CLIENT_ID);
        ClientDetailsDTO clientDetailsDTO = clientDetailsMapper.clientDetailsToClientDetailsDTO(updatedClientDetails);

        restClientDetailsMockMvc.perform(put("/api/client-details")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clientDetailsDTO)))
                .andExpect(status().isOk());

        // Validate the ClientDetails in the database
        List<ClientDetails> clientDetails = clientDetailsRepository.findAll();
        assertThat(clientDetails).hasSize(databaseSizeBeforeUpdate);
        ClientDetails testClientDetails = clientDetails.get(clientDetails.size() - 1);
        assertThat(testClientDetails.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
    }

    @Test
    @Transactional
    public void deleteClientDetails() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);
        int databaseSizeBeforeDelete = clientDetailsRepository.findAll().size();

        // Get the clientDetails
        restClientDetailsMockMvc.perform(delete("/api/client-details/{id}", clientDetails.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientDetails> clientDetails = clientDetailsRepository.findAll();
        assertThat(clientDetails).hasSize(databaseSizeBeforeDelete - 1);
    }
}
