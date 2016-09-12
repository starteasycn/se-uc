package cn.starteasy.uaa.service;

import cn.starteasy.uaa.service.dto.ClientDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing ClientDetails.
 */
public interface ClientDetailsService {

    /**
     * Save a clientDetails.
     *
     * @param clientDetailsDTO the entity to save
     * @return the persisted entity
     */
    ClientDetailsDTO save(ClientDetailsDTO clientDetailsDTO);

    /**
     *  Get all the clientDetails.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClientDetailsDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" clientDetails.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClientDetailsDTO findOne(Long id);

    /**
     *  Delete the "id" clientDetails.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
