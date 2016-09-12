package cn.starteasy.uaa.service.mapper;

import cn.starteasy.uaa.domain.*;
import cn.starteasy.uaa.service.dto.DistrictDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity District and its DTO DistrictDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DistrictMapper {

    DistrictDTO districtToDistrictDTO(District district);

    List<DistrictDTO> districtsToDistrictDTOs(List<District> districts);

    District districtDTOToDistrict(DistrictDTO districtDTO);

    List<District> districtDTOsToDistricts(List<DistrictDTO> districtDTOs);
}
