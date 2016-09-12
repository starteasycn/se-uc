package cn.starteasy.uaa.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 区                                                                           
 * 
 */
@ApiModel(description = ""
    + "区                                                                      "
    + "")
@Entity
@Table(name = "district")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class District implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "district_code")
    private String districtCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public District districtName(String districtName) {
        this.districtName = districtName;
        return this;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public District districtCode(String districtCode) {
        this.districtCode = districtCode;
        return this;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        District district = (District) o;
        if(district.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, district.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "District{" +
            "id=" + id +
            ", districtName='" + districtName + "'" +
            ", districtCode='" + districtCode + "'" +
            '}';
    }
}
