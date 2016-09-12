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
 * A ClientDetails.
 */
@Entity
@Table(name = "client_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClientDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "client_id")
    private String clientId;

    @ManyToMany(mappedBy = "clientDetails")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> products = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public ClientDetails clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public ClientDetails products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public ClientDetails addProduct(Product product) {
        products.add(product);
        product.getClientDetails().add(this);
        return this;
    }

    public ClientDetails removeProduct(Product product) {
        products.remove(product);
        product.getClientDetails().remove(this);
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
        ClientDetails clientDetails = (ClientDetails) o;
        if(clientDetails.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, clientDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClientDetails{" +
            "id=" + id +
            ", clientId='" + clientId + "'" +
            '}';
    }
}
