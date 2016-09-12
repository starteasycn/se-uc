package cn.starteasy.uaa.service;

import cn.starteasy.uaa.service.dto.CountryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Country.
 */
public interface CountryService {

    /**
     * Save a country.
     *
     * @param countryDTO the entity to save
     * @return the persisted entity
     */
    CountryDTO save(CountryDTO countryDTO);

    /**
     *  Get all the countries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CountryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" country.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CountryDTO findOne(Long id);

    /**
     *  Delete the "id" country.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
