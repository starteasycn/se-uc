package cn.starteasy.uaa.repository;

import cn.starteasy.uaa.domain.City;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the City entity.
 */
@SuppressWarnings("unused")
public interface CityRepository extends JpaRepository<City,Long> {

}
