package cn.starteasy.uaa.service.mapper;

import cn.starteasy.uaa.domain.*;
import cn.starteasy.uaa.service.dto.CompanyDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Company and its DTO CompanyDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, BizUserMapper.class, ProductMapper.class, })
public interface CompanyMapper {

    @Mapping(source = "province.id", target = "provinceId")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "county.id", target = "countyId")
    @Mapping(source = "district.id", target = "districtId")
    CompanyDTO companyToCompanyDTO(Company company);

    List<CompanyDTO> companiesToCompanyDTOs(List<Company> companies);

    @Mapping(source = "provinceId", target = "province")
    @Mapping(source = "cityId", target = "city")
    @Mapping(source = "countyId", target = "county")
    @Mapping(source = "districtId", target = "district")
    Company companyDTOToCompany(CompanyDTO companyDTO);

    List<Company> companyDTOsToCompanies(List<CompanyDTO> companyDTOs);

    default Province provinceFromId(Long id) {
        if (id == null) {
            return null;
        }
        Province province = new Province();
        province.setId(id);
        return province;
    }

    default City cityFromId(Long id) {
        if (id == null) {
            return null;
        }
        City city = new City();
        city.setId(id);
        return city;
    }

    default County countyFromId(Long id) {
        if (id == null) {
            return null;
        }
        County county = new County();
        county.setId(id);
        return county;
    }

    default District districtFromId(Long id) {
        if (id == null) {
            return null;
        }
        District district = new District();
        district.setId(id);
        return district;
    }

    default Employee employeeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }

    default BizUser bizUserFromId(Long id) {
        if (id == null) {
            return null;
        }
        BizUser bizUser = new BizUser();
        bizUser.setId(id);
        return bizUser;
    }

    default Product productFromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
