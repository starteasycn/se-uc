package cn.starteasy.uaa.web.rest;

import cn.starteasy.uaa.SeuaaApp;
import cn.starteasy.uaa.domain.Company;
import cn.starteasy.uaa.repository.CompanyRepository;
import cn.starteasy.uaa.service.CompanyService;
import cn.starteasy.uaa.service.dto.CompanyDTO;
import cn.starteasy.uaa.service.mapper.CompanyMapper;

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

import cn.starteasy.uaa.domain.enumeration.OrgType;
/**
 * Test class for the CompanyResource REST controller.
 *
 * @see CompanyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeuaaApp.class)
public class CompanyResourceIntTest {
    private static final String DEFAULT_COMPANY_NAME = "AAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBB";
    private static final String DEFAULT_REG_ADDRESS = "AAAAA";
    private static final String UPDATED_REG_ADDRESS = "BBBBB";
    private static final String DEFAULT_LINK_ADDRESS = "AAAAA";
    private static final String UPDATED_LINK_ADDRESS = "BBBBB";
    private static final String DEFAULT_BUSINESS_LICENSE = "AAAAA";
    private static final String UPDATED_BUSINESS_LICENSE = "BBBBB";
    private static final String DEFAULT_CODE_CERTIFICATE = "AAAAA";
    private static final String UPDATED_CODE_CERTIFICATE = "BBBBB";

    private static final OrgType DEFAULT_ORG_TYPE = OrgType.COMPANY;
    private static final OrgType UPDATED_ORG_TYPE = OrgType.TEAM;

    @Inject
    private CompanyRepository companyRepository;

    @Inject
    private CompanyMapper companyMapper;

    @Inject
    private CompanyService companyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCompanyMockMvc;

    private Company company;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompanyResource companyResource = new CompanyResource();
        ReflectionTestUtils.setField(companyResource, "companyService", companyService);
        this.restCompanyMockMvc = MockMvcBuilders.standaloneSetup(companyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createEntity(EntityManager em) {
        Company company = new Company();
        company = new Company()
                .companyName(DEFAULT_COMPANY_NAME)
                .regAddress(DEFAULT_REG_ADDRESS)
                .linkAddress(DEFAULT_LINK_ADDRESS)
                .businessLicense(DEFAULT_BUSINESS_LICENSE)
                .codeCertificate(DEFAULT_CODE_CERTIFICATE)
                .orgType(DEFAULT_ORG_TYPE);
        return company;
    }

    @Before
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company
        CompanyDTO companyDTO = companyMapper.companyToCompanyDTO(company);

        restCompanyMockMvc.perform(post("/api/companies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
                .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companies = companyRepository.findAll();
        assertThat(companies).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companies.get(companies.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCompany.getRegAddress()).isEqualTo(DEFAULT_REG_ADDRESS);
        assertThat(testCompany.getLinkAddress()).isEqualTo(DEFAULT_LINK_ADDRESS);
        assertThat(testCompany.getBusinessLicense()).isEqualTo(DEFAULT_BUSINESS_LICENSE);
        assertThat(testCompany.getCodeCertificate()).isEqualTo(DEFAULT_CODE_CERTIFICATE);
        assertThat(testCompany.getOrgType()).isEqualTo(DEFAULT_ORG_TYPE);
    }

    @Test
    @Transactional
    public void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companies
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
                .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
                .andExpect(jsonPath("$.[*].regAddress").value(hasItem(DEFAULT_REG_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].linkAddress").value(hasItem(DEFAULT_LINK_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].businessLicense").value(hasItem(DEFAULT_BUSINESS_LICENSE.toString())))
                .andExpect(jsonPath("$.[*].codeCertificate").value(hasItem(DEFAULT_CODE_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].orgType").value(hasItem(DEFAULT_ORG_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.regAddress").value(DEFAULT_REG_ADDRESS.toString()))
            .andExpect(jsonPath("$.linkAddress").value(DEFAULT_LINK_ADDRESS.toString()))
            .andExpect(jsonPath("$.businessLicense").value(DEFAULT_BUSINESS_LICENSE.toString()))
            .andExpect(jsonPath("$.codeCertificate").value(DEFAULT_CODE_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.orgType").value(DEFAULT_ORG_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findOne(company.getId());
        updatedCompany
                .companyName(UPDATED_COMPANY_NAME)
                .regAddress(UPDATED_REG_ADDRESS)
                .linkAddress(UPDATED_LINK_ADDRESS)
                .businessLicense(UPDATED_BUSINESS_LICENSE)
                .codeCertificate(UPDATED_CODE_CERTIFICATE)
                .orgType(UPDATED_ORG_TYPE);
        CompanyDTO companyDTO = companyMapper.companyToCompanyDTO(updatedCompany);

        restCompanyMockMvc.perform(put("/api/companies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
                .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companies = companyRepository.findAll();
        assertThat(companies).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companies.get(companies.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompany.getRegAddress()).isEqualTo(UPDATED_REG_ADDRESS);
        assertThat(testCompany.getLinkAddress()).isEqualTo(UPDATED_LINK_ADDRESS);
        assertThat(testCompany.getBusinessLicense()).isEqualTo(UPDATED_BUSINESS_LICENSE);
        assertThat(testCompany.getCodeCertificate()).isEqualTo(UPDATED_CODE_CERTIFICATE);
        assertThat(testCompany.getOrgType()).isEqualTo(UPDATED_ORG_TYPE);
    }

    @Test
    @Transactional
    public void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Get the company
        restCompanyMockMvc.perform(delete("/api/companies/{id}", company.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Company> companies = companyRepository.findAll();
        assertThat(companies).hasSize(databaseSizeBeforeDelete - 1);
    }
}
