package cn.starteasy.uaa.service.mapper;

import cn.starteasy.uaa.domain.*;
import cn.starteasy.uaa.service.dto.ProvinceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Province and its DTO ProvinceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProvinceMapper {

    @Mapping(source = "city.id", target = "cityId")
    ProvinceDTO provinceToProvinceDTO(Province province);

    List<ProvinceDTO> provincesToProvinceDTOs(List<Province> provinces);

    @Mapping(source = "cityId", target = "city")
    Province provinceDTOToProvince(ProvinceDTO provinceDTO);

    List<Province> provinceDTOsToProvinces(List<ProvinceDTO> provinceDTOs);

    default City cityFromId(Long id) {
        if (id == null) {
            return null;
        }
        City city = new City();
        city.setId(id);
        return city;
    }
}
