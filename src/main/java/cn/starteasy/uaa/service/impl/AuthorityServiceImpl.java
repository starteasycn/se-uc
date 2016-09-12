package cn.starteasy.uaa.service.impl;

import cn.starteasy.uaa.service.AuthorityService;
import cn.starteasy.uaa.domain.Authority;
import cn.starteasy.uaa.repository.AuthorityRepository;
import cn.starteasy.uaa.service.dto.AuthorityDTO;
import cn.starteasy.uaa.service.mapper.AuthorityMapper;
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
 * Service Implementation for managing Authority.
 */
@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService{

    private final Logger log = LoggerFactory.getLogger(AuthorityServiceImpl.class);
    
    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private AuthorityMapper authorityMapper;

    /**
     * Save a authority.
     *
     * @param authorityDTO the entity to save
     * @return the persisted entity
     */
    public AuthorityDTO save(AuthorityDTO authorityDTO) {
        log.debug("Request to save Authority : {}", authorityDTO);
        Authority authority = authorityMapper.authorityDTOToAuthority(authorityDTO);
        authority = authorityRepository.save(authority);
        AuthorityDTO result = authorityMapper.authorityToAuthorityDTO(authority);
        return result;
    }

    /**
     *  Get all the authorities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<AuthorityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Authorities");
        Page<Authority> result = authorityRepository.findAll(pageable);
        return result.map(authority -> authorityMapper.authorityToAuthorityDTO(authority));
    }

    /**
     *  Get one authority by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public AuthorityDTO findOne(Long id) {
        log.debug("Request to get Authority : {}", id);
        Authority authority = authorityRepository.findOne(id);
        AuthorityDTO authorityDTO = authorityMapper.authorityToAuthorityDTO(authority);
        return authorityDTO;
    }

    /**
     *  Delete the  authority by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Authority : {}", id);
        authorityRepository.delete(id);
    }
}
