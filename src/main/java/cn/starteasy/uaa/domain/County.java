package cn.starteasy.uaa.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 县                                                                           
 * 
 */
@ApiModel(description = ""
    + "县                                                                      "
    + "")
@Entity
@Table(name = "county")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class County implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "county_name")
    private String countyName;

    @Column(name = "county_code")
    private String countyCode;

    @OneToOne
    @JoinColumn(unique = true)
    private District district;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public County countyName(String countyName) {
        this.countyName = countyName;
        return this;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public County countyCode(String countyCode) {
        this.countyCode = countyCode;
        return this;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public District getDistrict() {
        return district;
    }

    public County district(District district) {
        this.district = district;
        return this;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        County county = (County) o;
        if(county.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, county.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "County{" +
            "id=" + id +
            ", countyName='" + countyName + "'" +
            ", countyCode='" + countyCode + "'" +
            '}';
    }
}
