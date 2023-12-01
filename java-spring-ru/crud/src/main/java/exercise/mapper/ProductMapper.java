package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

// BEGIN
@Mapper(
        uses = { JsonNullableMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ProductMapper {
    public abstract Product map(ProductCreateDTO dto);

    @Mapping(target = "categoryId", expression = "java(model.getCategory().getId())")
    @Mapping(target = "categoryName", expression = "java(model.getCategory().getName())")
    public abstract ProductDTO map(Product model);

    public abstract void update(ProductUpdateDTO dto, @MappingTarget Product model);
}
// END
