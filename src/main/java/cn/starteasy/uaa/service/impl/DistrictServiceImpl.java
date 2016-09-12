package cn.starteasy.uaa.service.impl;

import cn.starteasy.uaa.service.DistrictService;
import cn.starteasy.uaa.domain.District;
import cn.starteasy.uaa.repository.DistrictRepository;
import cn.starteasy.uaa.service.dto.DistrictDTO;
import cn.starteasy.uaa.service.mapper.DistrictMapper;
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
 * Service Implementation for managing District.
 */
@Service
@Transactional
public class DistrictServiceImpl implements DistrictService{

    private final Logger log = LoggerFactory.getLogger(DistrictServiceImpl.class);
    
    @Inject
    private DistrictRepository districtRepository;

    @Inject
    private DistrictMapper districtMapper;

    /**
     * Save a district.
     *
     * @param districtDTO the entity to save
     * @return the persisted entity
     */
    public DistrictDTO save(DistrictDTO districtDTO) {
        log.debug("Request to save District : {}", districtDTO);
        District district = districtMapper.districtDTOToDistrict(districtDTO);
        district = districtRepository.save(district);
        DistrictDTO result = districtMapper.districtToDistrictDTO(district);
        return result;
    }

    /**
     *  Get all the districts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<DistrictDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Districts");
        Page<District> result = districtRepository.findAll(pageable);
        return result.map(district -> districtMapper.districtToDistrictDTO(district));
    }

    /**
     *  Get one district by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DistrictDTO findOne(Long id) {
        log.debug("Request to get District : {}", id);
        District district = districtRepository.findOne(id);
        DistrictDTO districtDTO = districtMapper.districtToDistrictDTO(district);
        return districtDTO;
    }

    /**
     *  Delete the  district by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete District : {}", id);
        districtRepository.delete(id);
    }
}
