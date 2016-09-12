package cn.starteasy.uaa.service.impl;

import cn.starteasy.uaa.service.CompanyService;
import cn.starteasy.uaa.domain.Company;
import cn.starteasy.uaa.repository.CompanyRepository;
import cn.starteasy.uaa.service.dto.CompanyDTO;
import cn.starteasy.uaa.service.mapper.CompanyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Company.
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService{

    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);
    
    @Inject
    private CompanyRepository companyRepository;

    @Inject
    private CompanyMapper companyMapper;

    /**
     * Save a company.
     *
     * @param companyDTO the entity to save
     * @return the persisted entity
     */
    public CompanyDTO save(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        Company company = companyMapper.companyDTOToCompany(companyDTO);
        company = companyRepository.save(company);
        CompanyDTO result = companyMapper.companyToCompanyDTO(company);
        return result;
    }

    /**
     *  Get all the companies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<CompanyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Companies");
        Page<Company> result = companyRepository.findAll(pageable);
        return result.map(company -> companyMapper.companyToCompanyDTO(company));
    }

    /**
     *  Get one company by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CompanyDTO findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        Company company = companyRepository.findOneWithEagerRelationships(id);
        CompanyDTO companyDTO = companyMapper.companyToCompanyDTO(company);
        return companyDTO;
    }

    /**
     *  Delete the  company by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.delete(id);
    }
}
