package cn.starteasy.uaa.service.mapper;

import cn.starteasy.uaa.domain.*;
import cn.starteasy.uaa.service.dto.ClientDetailsDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ClientDetails and its DTO ClientDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientDetailsMapper {

    ClientDetailsDTO clientDetailsToClientDetailsDTO(ClientDetails clientDetails);

    List<ClientDetailsDTO> clientDetailsToClientDetailsDTOs(List<ClientDetails> clientDetails);

    @Mapping(target = "products", ignore = true)
    ClientDetails clientDetailsDTOToClientDetails(ClientDetailsDTO clientDetailsDTO);

    List<ClientDetails> clientDetailsDTOsToClientDetails(List<ClientDetailsDTO> clientDetailsDTOs);
}
