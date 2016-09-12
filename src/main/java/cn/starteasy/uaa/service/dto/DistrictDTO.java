package cn.starteasy.uaa.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the District entity.
 */
public class DistrictDTO implements Serializable {

    private Long id;

    private String districtName;

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

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
    public String getDistrictCode() {
        return districtCode;
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

        DistrictDTO districtDTO = (DistrictDTO) o;

        if ( ! Objects.equals(id, districtDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DistrictDTO{" +
            "id=" + id +
            ", districtName='" + districtName + "'" +
            ", districtCode='" + districtCode + "'" +
            '}';
    }
}
