package cn.starteasy.uaa.service.mapper;

import cn.starteasy.uaa.domain.*;
import cn.starteasy.uaa.service.dto.AuthorityDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Authority and its DTO AuthorityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AuthorityMapper {

    AuthorityDTO authorityToAuthorityDTO(Authority authority);

    List<AuthorityDTO> authoritiesToAuthorityDTOs(List<Authority> authorities);

    @Mapping(target = "myUsers", ignore = true)
    Authority authorityDTOToAuthority(AuthorityDTO authorityDTO);

    List<Authority> authorityDTOsToAuthorities(List<AuthorityDTO> authorityDTOs);
}
