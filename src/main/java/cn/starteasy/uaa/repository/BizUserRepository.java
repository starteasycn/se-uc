package cn.starteasy.uaa.repository;

import cn.starteasy.uaa.domain.BizUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the BizUser entity.
 */
@SuppressWarnings("unused")
public interface BizUserRepository extends JpaRepository<BizUser,Long> {

    @Query("select distinct bizUser from BizUser bizUser left join fetch bizUser.authorities")
    List<BizUser> findAllWithEagerRelationships();

    @Query("select bizUser from BizUser bizUser left join fetch bizUser.authorities where bizUser.id =:id")
    BizUser findOneWithEagerRelationships(@Param("id") Long id);

}
