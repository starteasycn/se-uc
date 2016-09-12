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
 * A Authority.
 */
@Entity
@Table(name = "authority")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "auth_name")
    private String authName;

    @ManyToMany(mappedBy = "authorities")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BizUser> myUsers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthName() {
        return authName;
    }

    public Authority authName(String authName) {
        this.authName = authName;
        return this;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public Set<BizUser> getMyUsers() {
        return myUsers;
    }

    public Authority myUsers(Set<BizUser> bizUsers) {
        this.myUsers = bizUsers;
        return this;
    }

    public Authority addBizUser(BizUser bizUser) {
        myUsers.add(bizUser);
        bizUser.getAuthorities().add(this);
        return this;
    }

    public Authority removeBizUser(BizUser bizUser) {
        myUsers.remove(bizUser);
        bizUser.getAuthorities().remove(this);
        return this;
    }

    public void setMyUsers(Set<BizUser> bizUsers) {
        this.myUsers = bizUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Authority authority = (Authority) o;
        if(authority.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, authority.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Authority{" +
            "id=" + id +
            ", authName='" + authName + "'" +
            '}';
    }
}
