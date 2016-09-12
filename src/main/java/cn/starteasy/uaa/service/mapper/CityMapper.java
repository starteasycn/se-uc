package cn.starteasy.uaa.service.mapper;

import cn.starteasy.uaa.domain.*;
import cn.starteasy.uaa.service.dto.CityDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity City and its DTO CityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CityMapper {

    @Mapping(source = "county.id", target = "countyId")
    CityDTO cityToCityDTO(City city);

    List<CityDTO> citiesToCityDTOs(List<City> cities);

    @Mapping(source = "countyId", target = "county")
    City cityDTOToCity(CityDTO cityDTO);

    List<City> cityDTOsToCities(List<CityDTO> cityDTOs);

    default County countyFromId(Long id) {
        if (id == null) {
            return null;
        }
        County county = new County();
        county.setId(id);
        return county;
    }
}
