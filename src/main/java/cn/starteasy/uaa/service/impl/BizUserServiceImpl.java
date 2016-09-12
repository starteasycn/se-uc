package cn.starteasy.uaa.service.impl;

import cn.starteasy.uaa.service.BizUserService;
import cn.starteasy.uaa.domain.BizUser;
import cn.starteasy.uaa.repository.BizUserRepository;
import cn.starteasy.uaa.service.dto.BizUserDTO;
import cn.starteasy.uaa.service.mapper.BizUserMapper;
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
 * Service Implementation for managing BizUser.
 */
@Service
@Transactional
public class BizUserServiceImpl implements BizUserService{

    private final Logger log = LoggerFactory.getLogger(BizUserServiceImpl.class);
    
    @Inject
    private BizUserRepository bizUserRepository;

    @Inject
    private BizUserMapper bizUserMapper;

    /**
     * Save a bizUser.
     *
     * @param bizUserDTO the entity to save
     * @return the persisted entity
     */
    public BizUserDTO save(BizUserDTO bizUserDTO) {
        log.debug("Request to save BizUser : {}", bizUserDTO);
        BizUser bizUser = bizUserMapper.bizUserDTOToBizUser(bizUserDTO);
        bizUser = bizUserRepository.save(bizUser);
        BizUserDTO result = bizUserMapper.bizUserToBizUserDTO(bizUser);
        return result;
    }

    /**
     *  Get all the bizUsers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<BizUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BizUsers");
        Page<BizUser> result = bizUserRepository.findAll(pageable);
        return result.map(bizUser -> bizUserMapper.bizUserToBizUserDTO(bizUser));
    }

    /**
     *  Get one bizUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public BizUserDTO findOne(Long id) {
        log.debug("Request to get BizUser : {}", id);
        BizUser bizUser = bizUserRepository.findOneWithEagerRelationships(id);
        BizUserDTO bizUserDTO = bizUserMapper.bizUserToBizUserDTO(bizUser);
        return bizUserDTO;
    }

    /**
     *  Delete the  bizUser by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BizUser : {}", id);
        bizUserRepository.delete(id);
    }
}
