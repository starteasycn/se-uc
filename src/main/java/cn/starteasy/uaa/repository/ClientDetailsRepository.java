package cn.starteasy.uaa.repository;

import cn.starteasy.uaa.domain.ClientDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ClientDetails entity.
 */
@SuppressWarnings("unused")
public interface ClientDetailsRepository extends JpaRepository<ClientDetails,Long> {

}
