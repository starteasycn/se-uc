package cn.starteasy.uaa.service;

import cn.starteasy.uaa.service.dto.DistrictDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing District.
 */
public interface DistrictService {

    /**
     * Save a district.
     *
     * @param districtDTO the entity to save
     * @return the persisted entity
     */
    DistrictDTO save(DistrictDTO districtDTO);

    /**
     *  Get all the districts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DistrictDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" district.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DistrictDTO findOne(Long id);

    /**
     *  Delete the "id" district.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
