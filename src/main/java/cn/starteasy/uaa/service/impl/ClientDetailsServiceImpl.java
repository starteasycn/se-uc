package cn.starteasy.uaa.service.impl;

import cn.starteasy.uaa.service.ClientDetailsService;
import cn.starteasy.uaa.domain.ClientDetails;
import cn.starteasy.uaa.repository.ClientDetailsRepository;
import cn.starteasy.uaa.service.dto.ClientDetailsDTO;
import cn.starteasy.uaa.service.mapper.ClientDetailsMapper;
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
 * Service Implementation for managing ClientDetails.
 */
@Service
@Transactional
public class ClientDetailsServiceImpl implements ClientDetailsService{

    private final Logger log = LoggerFactory.getLogger(ClientDetailsServiceImpl.class);
    
    @Inject
    private ClientDetailsRepository clientDetailsRepository;

    @Inject
    private ClientDetailsMapper clientDetailsMapper;

    /**
     * Save a clientDetails.
     *
     * @param clientDetailsDTO the entity to save
     * @return the persisted entity
     */
    public ClientDetailsDTO save(ClientDetailsDTO clientDetailsDTO) {
        log.debug("Request to save ClientDetails : {}", clientDetailsDTO);
        ClientDetails clientDetails = clientDetailsMapper.clientDetailsDTOToClientDetails(clientDetailsDTO);
        clientDetails = clientDetailsRepository.save(clientDetails);
        ClientDetailsDTO result = clientDetailsMapper.clientDetailsToClientDetailsDTO(clientDetails);
        return result;
    }

    /**
     *  Get all the clientDetails.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ClientDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClientDetails");
        Page<ClientDetails> result = clientDetailsRepository.findAll(pageable);
        return result.map(clientDetails -> clientDetailsMapper.clientDetailsToClientDetailsDTO(clientDetails));
    }

    /**
     *  Get one clientDetails by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ClientDetailsDTO findOne(Long id) {
        log.debug("Request to get ClientDetails : {}", id);
        ClientDetails clientDetails = clientDetailsRepository.findOne(id);
        ClientDetailsDTO clientDetailsDTO = clientDetailsMapper.clientDetailsToClientDetailsDTO(clientDetails);
        return clientDetailsDTO;
    }

    /**
     *  Delete the  clientDetails by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ClientDetails : {}", id);
        clientDetailsRepository.delete(id);
    }
}
