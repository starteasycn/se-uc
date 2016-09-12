package cn.starteasy.uaa.service.impl;

import cn.starteasy.uaa.service.ProvinceService;
import cn.starteasy.uaa.domain.Province;
import cn.starteasy.uaa.repository.ProvinceRepository;
import cn.starteasy.uaa.service.dto.ProvinceDTO;
import cn.starteasy.uaa.service.mapper.ProvinceMapper;
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
 * Service Implementation for managing Province.
 */
@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService{

    private final Logger log = LoggerFactory.getLogger(ProvinceServiceImpl.class);
    
    @Inject
    private ProvinceRepository provinceRepository;

    @Inject
    private ProvinceMapper provinceMapper;

    /**
     * Save a province.
     *
     * @param provinceDTO the entity to save
     * @return the persisted entity
     */
    public ProvinceDTO save(ProvinceDTO provinceDTO) {
        log.debug("Request to save Province : {}", provinceDTO);
        Province province = provinceMapper.provinceDTOToProvince(provinceDTO);
        province = provinceRepository.save(province);
        ProvinceDTO result = provinceMapper.provinceToProvinceDTO(province);
        return result;
    }

    /**
     *  Get all the provinces.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ProvinceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Provinces");
        Page<Province> result = provinceRepository.findAll(pageable);
        return result.map(province -> provinceMapper.provinceToProvinceDTO(province));
    }

    /**
     *  Get one province by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ProvinceDTO findOne(Long id) {
        log.debug("Request to get Province : {}", id);
        Province province = provinceRepository.findOne(id);
        ProvinceDTO provinceDTO = provinceMapper.provinceToProvinceDTO(province);
        return provinceDTO;
    }

    /**
     *  Delete the  province by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Province : {}", id);
        provinceRepository.delete(id);
    }
}
