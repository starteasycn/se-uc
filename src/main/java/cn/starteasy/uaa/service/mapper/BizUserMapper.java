package cn.starteasy.uaa.service.mapper;

import cn.starteasy.uaa.domain.*;
import cn.starteasy.uaa.service.dto.BizUserDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity BizUser and its DTO BizUserDTO.
 */
@Mapper(componentModel = "spring", uses = {AuthorityMapper.class, })
public interface BizUserMapper {

    BizUserDTO bizUserToBizUserDTO(BizUser bizUser);

    List<BizUserDTO> bizUsersToBizUserDTOs(List<BizUser> bizUsers);

    @Mapping(target = "companies", ignore = true)
    @Mapping(target = "products", ignore = true)
    BizUser bizUserDTOToBizUser(BizUserDTO bizUserDTO);

    List<BizUser> bizUserDTOsToBizUsers(List<BizUserDTO> bizUserDTOs);

    default Authority authorityFromId(Long id) {
        if (id == null) {
            return null;
        }
        Authority authority = new Authority();
        authority.setId(id);
        return authority;
    }
}
