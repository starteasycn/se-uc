package cn.starteasy.uaa.repository;

import cn.starteasy.uaa.domain.Company;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Company entity.
 */
@SuppressWarnings("unused")
public interface CompanyRepository extends JpaRepository<Company,Long> {

    @Query("select distinct company from Company company left join fetch company.employees left join fetch company.users left join fetch company.products")
    List<Company> findAllWithEagerRelationships();

    @Query("select company from Company company left join fetch company.employees left join fetch company.users left join fetch company.products where company.id =:id")
    Company findOneWithEagerRelationships(@Param("id") Long id);

}
