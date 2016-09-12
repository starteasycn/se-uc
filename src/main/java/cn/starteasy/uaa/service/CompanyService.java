package cn.starteasy.uaa.service;

import cn.starteasy.uaa.service.dto.CompanyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Company.
 */
public interface CompanyService {

    /**
     * Save a company.
     *
     * @param companyDTO the entity to save
     * @return the persisted entity
     */
    CompanyDTO save(CompanyDTO companyDTO);

    /**
     *  Get all the companies.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CompanyDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" company.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CompanyDTO findOne(Long id);

    /**
     *  Delete the "id" company.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
