package cn.starteasy.uaa.service;

import cn.starteasy.uaa.service.dto.AuthorityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Authority.
 */
public interface AuthorityService {

    /**
     * Save a authority.
     *
     * @param authorityDTO the entity to save
     * @return the persisted entity
     */
    AuthorityDTO save(AuthorityDTO authorityDTO);

    /**
     *  Get all the authorities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AuthorityDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" authority.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AuthorityDTO findOne(Long id);

    /**
     *  Delete the "id" authority.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
