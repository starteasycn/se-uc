package cn.starteasy.uaa.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import cn.starteasy.uaa.domain.enumeration.OrgType;

/**
 * A DTO for the Company entity.
 */
public class CompanyDTO implements Serializable {

    private Long id;

    private String companyName;

    private String regAddress;

    private String linkAddress;

    private String businessLicense;

    private String codeCertificate;

    private OrgType orgType;


    private Long provinceId;
    
    private Long cityId;
    
    private Long countyId;
    
    private Long districtId;
    
    private Set<EmployeeDTO> employees = new HashSet<>();

    private Set<BizUserDTO> users = new HashSet<>();

    private Set<ProductDTO> products = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }
    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }
    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }
    public String getCodeCertificate() {
        return codeCertificate;
    }

    public void setCodeCertificate(String codeCertificate) {
        this.codeCertificate = codeCertificate;
    }
    public OrgType getOrgType() {
        return orgType;
    }

    public void setOrgType(OrgType orgType) {
        this.orgType = orgType;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Set<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeDTO> employees) {
        this.employees = employees;
    }

    public Set<BizUserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<BizUserDTO> bizUsers) {
        this.users = bizUsers;
    }

    public Set<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDTO> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;

        if ( ! Objects.equals(id, companyDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
            "id=" + id +
            ", companyName='" + companyName + "'" +
            ", regAddress='" + regAddress + "'" +
            ", linkAddress='" + linkAddress + "'" +
            ", businessLicense='" + businessLicense + "'" +
            ", codeCertificate='" + codeCertificate + "'" +
            ", orgType='" + orgType + "'" +
            '}';
    }
}
