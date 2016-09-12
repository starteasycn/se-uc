package cn.starteasy.uaa.service.mapper;

import cn.starteasy.uaa.domain.*;
import cn.starteasy.uaa.service.dto.ProductDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientDetailsMapper.class, BizUserMapper.class, })
public interface ProductMapper {

    ProductDTO productToProductDTO(Product product);

    List<ProductDTO> productsToProductDTOs(List<Product> products);

    @Mapping(target = "companies", ignore = true)
    Product productDTOToProduct(ProductDTO productDTO);

    List<Product> productDTOsToProducts(List<ProductDTO> productDTOs);

    default ClientDetails clientDetailsFromId(Long id) {
        if (id == null) {
            return null;
        }
        ClientDetails clientDetails = new ClientDetails();
        clientDetails.setId(id);
        return clientDetails;
    }

    default BizUser bizUserFromId(Long id) {
        if (id == null) {
            return null;
        }
        BizUser bizUser = new BizUser();
        bizUser.setId(id);
        return bizUser;
    }
}
