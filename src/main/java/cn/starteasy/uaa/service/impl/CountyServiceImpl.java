package cn.starteasy.uaa.service.impl;

import cn.starteasy.uaa.service.CountyService;
import cn.starteasy.uaa.domain.County;
import cn.starteasy.uaa.repository.CountyRepository;
import cn.starteasy.uaa.service.dto.CountyDTO;
import cn.starteasy.uaa.service.mapper.CountyMapper;
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
 * Service Implementation for managing County.
 */
@Service
@Transactional
public class CountyServiceImpl implements CountyService{

    private final Logger log = LoggerFactory.getLogger(CountyServiceImpl.class);
    
    @Inject
    private CountyRepository countyRepository;

    @Inject
    private CountyMapper countyMapper;

    /**
     * Save a county.
     *
     * @param countyDTO the entity to save
     * @return the persisted entity
     */
    public CountyDTO save(CountyDTO countyDTO) {
        log.debug("Request to save County : {}", countyDTO);
        County county = countyMapper.countyDTOToCounty(countyDTO);
        county = countyRepository.save(county);
        CountyDTO result = countyMapper.countyToCountyDTO(county);
        return result;
    }

    /**
     *  Get all the counties.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<CountyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Counties");
        Page<County> result = countyRepository.findAll(pageable);
        return result.map(county -> countyMapper.countyToCountyDTO(county));
    }

    /**
     *  Get one county by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CountyDTO findOne(Long id) {
        log.debug("Request to get County : {}", id);
        County county = countyRepository.findOne(id);
        CountyDTO countyDTO = countyMapper.countyToCountyDTO(county);
        return countyDTO;
    }

    /**
     *  Delete the  county by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete County : {}", id);
        countyRepository.delete(id);
    }
}
