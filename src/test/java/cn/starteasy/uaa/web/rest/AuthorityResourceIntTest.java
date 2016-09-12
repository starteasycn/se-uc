package cn.starteasy.uaa.web.rest;

import cn.starteasy.uaa.SeuaaApp;
import cn.starteasy.uaa.domain.Authority;
import cn.starteasy.uaa.repository.AuthorityRepository;
import cn.starteasy.uaa.service.AuthorityService;
import cn.starteasy.uaa.service.dto.AuthorityDTO;
import cn.starteasy.uaa.service.mapper.AuthorityMapper;

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
 * Test class for the AuthorityResource REST controller.
 *
 * @see AuthorityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeuaaApp.class)
public class AuthorityResourceIntTest {
    private static final String DEFAULT_AUTH_NAME = "AAAAA";
    private static final String UPDATED_AUTH_NAME = "BBBBB";

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private AuthorityMapper authorityMapper;

    @Inject
    private AuthorityService authorityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAuthorityMockMvc;

    private Authority authority;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AuthorityResource authorityResource = new AuthorityResource();
        ReflectionTestUtils.setField(authorityResource, "authorityService", authorityService);
        this.restAuthorityMockMvc = MockMvcBuilders.standaloneSetup(authorityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Authority createEntity(EntityManager em) {
        Authority authority = new Authority();
        authority = new Authority()
                .authName(DEFAULT_AUTH_NAME);
        return authority;
    }

    @Before
    public void initTest() {
        authority = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuthority() throws Exception {
        int databaseSizeBeforeCreate = authorityRepository.findAll().size();

        // Create the Authority
        AuthorityDTO authorityDTO = authorityMapper.authorityToAuthorityDTO(authority);

        restAuthorityMockMvc.perform(post("/api/authorities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(authorityDTO)))
                .andExpect(status().isCreated());

        // Validate the Authority in the database
        List<Authority> authorities = authorityRepository.findAll();
        assertThat(authorities).hasSize(databaseSizeBeforeCreate + 1);
        Authority testAuthority = authorities.get(authorities.size() - 1);
        assertThat(testAuthority.getAuthName()).isEqualTo(DEFAULT_AUTH_NAME);
    }

    @Test
    @Transactional
    public void getAllAuthorities() throws Exception {
        // Initialize the database
        authorityRepository.saveAndFlush(authority);

        // Get all the authorities
        restAuthorityMockMvc.perform(get("/api/authorities?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(authority.getId().intValue())))
                .andExpect(jsonPath("$.[*].authName").value(hasItem(DEFAULT_AUTH_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAuthority() throws Exception {
        // Initialize the database
        authorityRepository.saveAndFlush(authority);

        // Get the authority
        restAuthorityMockMvc.perform(get("/api/authorities/{id}", authority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(authority.getId().intValue()))
            .andExpect(jsonPath("$.authName").value(DEFAULT_AUTH_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAuthority() throws Exception {
        // Get the authority
        restAuthorityMockMvc.perform(get("/api/authorities/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuthority() throws Exception {
        // Initialize the database
        authorityRepository.saveAndFlush(authority);
        int databaseSizeBeforeUpdate = authorityRepository.findAll().size();

        // Update the authority
        Authority updatedAuthority = authorityRepository.findOne(authority.getId());
        updatedAuthority
                .authName(UPDATED_AUTH_NAME);
        AuthorityDTO authorityDTO = authorityMapper.authorityToAuthorityDTO(updatedAuthority);

        restAuthorityMockMvc.perform(put("/api/authorities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(authorityDTO)))
                .andExpect(status().isOk());

        // Validate the Authority in the database
        List<Authority> authorities = authorityRepository.findAll();
        assertThat(authorities).hasSize(databaseSizeBeforeUpdate);
        Authority testAuthority = authorities.get(authorities.size() - 1);
        assertThat(testAuthority.getAuthName()).isEqualTo(UPDATED_AUTH_NAME);
    }

    @Test
    @Transactional
    public void deleteAuthority() throws Exception {
        // Initialize the database
        authorityRepository.saveAndFlush(authority);
        int databaseSizeBeforeDelete = authorityRepository.findAll().size();

        // Get the authority
        restAuthorityMockMvc.perform(delete("/api/authorities/{id}", authority.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Authority> authorities = authorityRepository.findAll();
        assertThat(authorities).hasSize(databaseSizeBeforeDelete - 1);
    }
}
