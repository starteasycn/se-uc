package cn.starteasy.uaa.service.mapper;

import cn.starteasy.uaa.domain.*;
import cn.starteasy.uaa.service.dto.CountyDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity County and its DTO CountyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CountyMapper {

    @Mapping(source = "district.id", target = "districtId")
    CountyDTO countyToCountyDTO(County county);

    List<CountyDTO> countiesToCountyDTOs(List<County> counties);

    @Mapping(source = "districtId", target = "district")
    County countyDTOToCounty(CountyDTO countyDTO);

    List<County> countyDTOsToCounties(List<CountyDTO> countyDTOs);

    default District districtFromId(Long id) {
        if (id == null) {
            return null;
        }
        District district = new District();
        district.setId(id);
        return district;
    }
}
