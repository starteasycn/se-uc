package cn.starteasy.uaa.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import cn.starteasy.uaa.domain.enumeration.OrgType;

/**
 * 公司                                                                          
 * 
 */
@ApiModel(description = ""
    + "公司                                                                     "
    + "")
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "reg_address")
    private String regAddress;

    @Column(name = "link_address")
    private String linkAddress;

    @Column(name = "business_license")
    private String businessLicense;

    @Column(name = "code_certificate")
    private String codeCertificate;

    @Enumerated(EnumType.STRING)
    @Column(name = "org_type")
    private OrgType orgType;

    @OneToOne
    @JoinColumn(unique = true)
    private Province province;

    @OneToOne
    @JoinColumn(unique = true)
    private City city;

    @OneToOne
    @JoinColumn(unique = true)
    private County county;

    @OneToOne
    @JoinColumn(unique = true)
    private District district;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "company_employee",
               joinColumns = @JoinColumn(name="companies_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="employees_id", referencedColumnName="ID"))
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "company_user",
               joinColumns = @JoinColumn(name="companies_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="users_id", referencedColumnName="ID"))
    private Set<BizUser> users = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "company_product",
               joinColumns = @JoinColumn(name="companies_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="products_id", referencedColumnName="ID"))
    private Set<Product> products = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Company companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public Company regAddress(String regAddress) {
        this.regAddress = regAddress;
        return this;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public Company linkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
        return this;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public Company businessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
        return this;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getCodeCertificate() {
        return codeCertificate;
    }

    public Company codeCertificate(String codeCertificate) {
        this.codeCertificate = codeCertificate;
        return this;
    }

    public void setCodeCertificate(String codeCertificate) {
        this.codeCertificate = codeCertificate;
    }

    public OrgType getOrgType() {
        return orgType;
    }

    public Company orgType(OrgType orgType) {
        this.orgType = orgType;
        return this;
    }

    public void setOrgType(OrgType orgType) {
        this.orgType = orgType;
    }

    public Province getProvince() {
        return province;
    }

    public Company province(Province province) {
        this.province = province;
        return this;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public City getCity() {
        return city;
    }

    public Company city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public County getCounty() {
        return county;
    }

    public Company county(County county) {
        this.county = county;
        return this;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public District getDistrict() {
        return district;
    }

    public Company district(District district) {
        this.district = district;
        return this;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Company employees(Set<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public Company addEmployee(Employee employee) {
        employees.add(employee);
        employee.getCompanies().add(this);
        return this;
    }

    public Company removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.getCompanies().remove(this);
        return this;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<BizUser> getUsers() {
        return users;
    }

    public Company users(Set<BizUser> bizUsers) {
        this.users = bizUsers;
        return this;
    }

    public Company addBizUser(BizUser bizUser) {
        users.add(bizUser);
        bizUser.getCompanies().add(this);
        return this;
    }

    public Company removeBizUser(BizUser bizUser) {
        users.remove(bizUser);
        bizUser.getCompanies().remove(this);
        return this;
    }

    public void setUsers(Set<BizUser> bizUsers) {
        this.users = bizUsers;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Company products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Company addProduct(Product product) {
        products.add(product);
        product.getCompanies().add(this);
        return this;
    }

    public Company removeProduct(Product product) {
        products.remove(product);
        product.getCompanies().remove(this);
        return this;
    }

    public void setProducts(Set<Product> products) {
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
        Company company = (Company) o;
        if(company.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Company{" +
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
