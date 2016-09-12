package cn.starteasy.uaa.service.mapper;

import cn.starteasy.uaa.domain.*;
import cn.starteasy.uaa.service.dto.CountryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Country and its DTO CountryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CountryMapper {

    @Mapping(source = "province.id", target = "provinceId")
    CountryDTO countryToCountryDTO(Country country);

    List<CountryDTO> countriesToCountryDTOs(List<Country> countries);

    @Mapping(source = "provinceId", target = "province")
    Country countryDTOToCountry(CountryDTO countryDTO);

    List<Country> countryDTOsToCountries(List<CountryDTO> countryDTOs);

    default Province provinceFromId(Long id) {
        if (id == null) {
            return null;
        }
        Province province = new Province();
        province.setId(id);
        return province;
    }
}
