package cn.starteasy.uaa.service;

import cn.starteasy.uaa.service.dto.CountyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing County.
 */
public interface CountyService {

    /**
     * Save a county.
     *
     * @param countyDTO the entity to save
     * @return the persisted entity
     */
    CountyDTO save(CountyDTO countyDTO);

    /**
     *  Get all the counties.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CountyDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" county.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CountyDTO findOne(Long id);

    /**
     *  Delete the "id" county.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
