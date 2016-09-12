package cn.starteasy.uaa.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    private String productName;

    private String productCode;

    private String webSite;

    private String productDesc;


    private Set<ClientDetailsDTO> clientDetails = new HashSet<>();

    private Set<BizUserDTO> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Set<ClientDetailsDTO> getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(Set<ClientDetailsDTO> clientDetails) {
        this.clientDetails = clientDetails;
    }

    public Set<BizUserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<BizUserDTO> bizUsers) {
        this.users = bizUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;

        if ( ! Objects.equals(id, productDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + id +
            ", productName='" + productName + "'" +
            ", productCode='" + productCode + "'" +
            ", webSite='" + webSite + "'" +
            ", productDesc='" + productDesc + "'" +
            '}';
    }
}
