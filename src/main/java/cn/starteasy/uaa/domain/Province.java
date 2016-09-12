package cn.starteasy.uaa.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 省                                                                           
 * 
 */
@ApiModel(description = ""
    + "省                                                                      "
    + "")
@Entity
@Table(name = "province")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Province implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "province_name")
    private String provinceName;

    @Column(name = "province_code")
    private String provinceCode;

    @OneToOne
    @JoinColumn(unique = true)
    private City city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public Province provinceName(String provinceName) {
        this.provinceName = provinceName;
        return this;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public Province provinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
        return this;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public City getCity() {
        return city;
    }

    public Province city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Province province = (Province) o;
        if(province.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, province.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Province{" +
            "id=" + id +
            ", provinceName='" + provinceName + "'" +
            ", provinceCode='" + provinceCode + "'" +
            '}';
    }
}
