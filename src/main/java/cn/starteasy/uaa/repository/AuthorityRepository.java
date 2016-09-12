package cn.starteasy.uaa.repository;

import cn.starteasy.uaa.domain.Authority;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Authority entity.
 */
@SuppressWarnings("unused")
public interface AuthorityRepository extends JpaRepository<Authority,Long> {

}
