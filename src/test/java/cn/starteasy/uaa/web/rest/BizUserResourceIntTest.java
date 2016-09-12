package cn.starteasy.uaa.web.rest;

import cn.starteasy.uaa.SeuaaApp;
import cn.starteasy.uaa.domain.BizUser;
import cn.starteasy.uaa.repository.BizUserRepository;
import cn.starteasy.uaa.service.BizUserService;
import cn.starteasy.uaa.service.dto.BizUserDTO;
import cn.starteasy.uaa.service.mapper.BizUserMapper;

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
 * Test class for the BizUserResource REST controller.
 *
 * @see BizUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeuaaApp.class)
public class BizUserResourceIntTest {
    private static final String DEFAULT_USER_NAME = "AAAAA";
    private static final String UPDATED_USER_NAME = "BBBBB";

    @Inject
    private BizUserRepository bizUserRepository;

    @Inject
    private BizUserMapper bizUserMapper;

    @Inject
    private BizUserService bizUserService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restBizUserMockMvc;

    private BizUser bizUser;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BizUserResource bizUserResource = new BizUserResource();
        ReflectionTestUtils.setField(bizUserResource, "bizUserService", bizUserService);
        this.restBizUserMockMvc = MockMvcBuilders.standaloneSetup(bizUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BizUser createEntity(EntityManager em) {
        BizUser bizUser = new BizUser();
        bizUser = new BizUser()
                .userName(DEFAULT_USER_NAME);
        return bizUser;
    }

    @Before
    public void initTest() {
        bizUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createBizUser() throws Exception {
        int databaseSizeBeforeCreate = bizUserRepository.findAll().size();

        // Create the BizUser
        BizUserDTO bizUserDTO = bizUserMapper.bizUserToBizUserDTO(bizUser);

        restBizUserMockMvc.perform(post("/api/biz-users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bizUserDTO)))
                .andExpect(status().isCreated());

        // Validate the BizUser in the database
        List<BizUser> bizUsers = bizUserRepository.findAll();
        assertThat(bizUsers).hasSize(databaseSizeBeforeCreate + 1);
        BizUser testBizUser = bizUsers.get(bizUsers.size() - 1);
        assertThat(testBizUser.getUserName()).isEqualTo(DEFAULT_USER_NAME);
    }

    @Test
    @Transactional
    public void getAllBizUsers() throws Exception {
        // Initialize the database
        bizUserRepository.saveAndFlush(bizUser);

        // Get all the bizUsers
        restBizUserMockMvc.perform(get("/api/biz-users?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bizUser.getId().intValue())))
                .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())));
    }

    @Test
    @Transactional
    public void getBizUser() throws Exception {
        // Initialize the database
        bizUserRepository.saveAndFlush(bizUser);

        // Get the bizUser
        restBizUserMockMvc.perform(get("/api/biz-users/{id}", bizUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bizUser.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBizUser() throws Exception {
        // Get the bizUser
        restBizUserMockMvc.perform(get("/api/biz-users/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBizUser() throws Exception {
        // Initialize the database
        bizUserRepository.saveAndFlush(bizUser);
        int databaseSizeBeforeUpdate = bizUserRepository.findAll().size();

        // Update the bizUser
        BizUser updatedBizUser = bizUserRepository.findOne(bizUser.getId());
        updatedBizUser
                .userName(UPDATED_USER_NAME);
        BizUserDTO bizUserDTO = bizUserMapper.bizUserToBizUserDTO(updatedBizUser);

        restBizUserMockMvc.perform(put("/api/biz-users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(bizUserDTO)))
                .andExpect(status().isOk());

        // Validate the BizUser in the database
        List<BizUser> bizUsers = bizUserRepository.findAll();
        assertThat(bizUsers).hasSize(databaseSizeBeforeUpdate);
        BizUser testBizUser = bizUsers.get(bizUsers.size() - 1);
        assertThat(testBizUser.getUserName()).isEqualTo(UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void deleteBizUser() throws Exception {
        // Initialize the database
        bizUserRepository.saveAndFlush(bizUser);
        int databaseSizeBeforeDelete = bizUserRepository.findAll().size();

        // Get the bizUser
        restBizUserMockMvc.perform(delete("/api/biz-users/{id}", bizUser.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BizUser> bizUsers = bizUserRepository.findAll();
        assertThat(bizUsers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
