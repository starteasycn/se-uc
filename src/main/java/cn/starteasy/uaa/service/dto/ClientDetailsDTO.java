package cn.starteasy.uaa.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the ClientDetails entity.
 */
public class ClientDetailsDTO implements Serializable {

    private Long id;

    private String clientId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientDetailsDTO clientDetailsDTO = (ClientDetailsDTO) o;

        if ( ! Objects.equals(id, clientDetailsDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClientDetailsDTO{" +
            "id=" + id +
            ", clientId='" + clientId + "'" +
            '}';
    }
}
