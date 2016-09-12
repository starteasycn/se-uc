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
 * A BizUser.
 */
@Entity
@Table(name = "biz_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BizUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "biz_user_authority",
               joinColumns = @JoinColumn(name="biz_users_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="authorities_id", referencedColumnName="ID"))
    private Set<Authority> authorities = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Company> companies = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> products = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public BizUser userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public BizUser authorities(Set<Authority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public BizUser addAuthority(Authority authority) {
        authorities.add(authority);
        authority.getMyUsers().add(this);
        return this;
    }

    public BizUser removeAuthority(Authority authority) {
        authorities.remove(authority);
        authority.getMyUsers().remove(this);
        return this;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public BizUser companies(Set<Company> companies) {
        this.companies = companies;
        return this;
    }

    public BizUser addCompany(Company company) {
        companies.add(company);
        company.getUsers().add(this);
        return this;
    }

    public BizUser removeCompany(Company company) {
        companies.remove(company);
        company.getUsers().remove(this);
        return this;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public BizUser products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public BizUser addProduct(Product product) {
        products.add(product);
        product.getUsers().add(this);
        return this;
    }

    public BizUser removeProduct(Product product) {
        products.remove(product);
        product.getUsers().remove(this);
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
        BizUser bizUser = (BizUser) o;
        if(bizUser.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bizUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BizUser{" +
            "id=" + id +
            ", userName='" + userName + "'" +
            '}';
    }
}
