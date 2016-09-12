package cn.starteasy.uaa.repository;

import cn.starteasy.uaa.domain.District;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the District entity.
 */
@SuppressWarnings("unused")
public interface DistrictRepository extends JpaRepository<District,Long> {

}
