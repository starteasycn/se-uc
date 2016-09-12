package cn.starteasy.uaa.repository;

import cn.starteasy.uaa.domain.County;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the County entity.
 */
@SuppressWarnings("unused")
public interface CountyRepository extends JpaRepository<County,Long> {

}
