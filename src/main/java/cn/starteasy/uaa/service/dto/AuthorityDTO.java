package cn.starteasy.uaa.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Authority entity.
 */
public class AuthorityDTO implements Serializable {

    private Long id;

    private String authName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthorityDTO authorityDTO = (AuthorityDTO) o;

        if ( ! Objects.equals(id, authorityDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AuthorityDTO{" +
            "id=" + id +
            ", authName='" + authName + "'" +
            '}';
    }
}
