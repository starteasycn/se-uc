package cn.starteasy.uaa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "web_site")
    private String webSite;

    @Column(name = "product_desc")
    private String productDesc;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "product_client_details",
               joinColumns = @JoinColumn(name="products_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="client_details_id", referencedColumnName="ID"))
    private Set<ClientDetails> clientDetails = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "product_user",
               joinColumns = @JoinColumn(name="products_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="users_id", referencedColumnName="ID"))
    private Set<BizUser> users = new HashSet<>();

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Company> companies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public Product productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public Product productCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getWebSite() {
        return webSite;
    }

    public Product webSite(String webSite) {
        this.webSite = webSite;
        return this;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public Product productDesc(String productDesc) {
        this.productDesc = productDesc;
        return this;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Set<ClientDetails> getClientDetails() {
        return clientDetails;
    }

    public Product clientDetails(Set<ClientDetails> clientDetails) {
        this.clientDetails = clientDetails;
        return this;
    }

    public Product addClientDetails(ClientDetails clientDetails) {
        clientDetails.add(clientDetails);
        clientDetails.getProducts().add(this);
        return this;
    }

    public Product removeClientDetails(ClientDetails clientDetails) {
        clientDetails.remove(clientDetails);
        clientDetails.getProducts().remove(this);
        return this;
    }

    public void setClientDetails(Set<ClientDetails> clientDetails) {
        this.clientDetails = clientDetails;
    }

    public Set<BizUser> getUsers() {
        return users;
    }

    public Product users(Set<BizUser> bizUsers) {
        this.users = bizUsers;
        return this;
    }

    public Product addBizUser(BizUser bizUser) {
        users.add(bizUser);
        bizUser.getProducts().add(this);
        return this;
    }

    public Product removeBizUser(BizUser bizUser) {
        users.remove(bizUser);
        bizUser.getProducts().remove(this);
        return this;
    }

    public void setUsers(Set<BizUser> bizUsers) {
        this.users = bizUsers;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public Product companies(Set<Company> companies) {
        this.companies = companies;
        return this;
    }

    public Product addCompany(Company company) {
        companies.add(company);
        company.getProducts().add(this);
        return this;
    }

    public Product removeCompany(Company company) {
        companies.remove(company);
        company.getProducts().remove(this);
        return this;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if(product.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", productName='" + productName + "'" +
            ", productCode='" + productCode + "'" +
            ", webSite='" + webSite + "'" +
            ", productDesc='" + productDesc + "'" +
            '}';
    }
}
