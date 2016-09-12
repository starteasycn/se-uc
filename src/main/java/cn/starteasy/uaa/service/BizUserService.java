package cn.starteasy.uaa.service;

import cn.starteasy.uaa.service.dto.BizUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing BizUser.
 */
public interface BizUserService {

    /**
     * Save a bizUser.
     *
     * @param bizUserDTO the entity to save
     * @return the persisted entity
     */
    BizUserDTO save(BizUserDTO bizUserDTO);

    /**
     *  Get all the bizUsers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<BizUserDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" bizUser.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BizUserDTO findOne(Long id);

    /**
     *  Delete the "id" bizUser.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
