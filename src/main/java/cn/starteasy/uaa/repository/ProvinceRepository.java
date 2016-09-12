package cn.starteasy.uaa.repository;

import cn.starteasy.uaa.domain.Province;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Province entity.
 */
@SuppressWarnings("unused")
public interface ProvinceRepository extends JpaRepository<Province,Long> {

}
