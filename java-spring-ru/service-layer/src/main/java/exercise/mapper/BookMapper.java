package exercise.mapper;

import exercise.dto.*;
import exercise.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class BookMapper {
    // BEGIN
    public abstract Book map(BookCreateDTO dto);

    @Mapping(target = "authorId", expression = "java(model.getAuthor().getId())")
    @Mapping(target = "authorFirstName", expression = "java(model.getAuthor().getFirstName())")
    @Mapping(target = "authorLastName", expression = "java(model.getAuthor().getLastName())")
    public abstract BookDTO map(Book model);
    // END

    @Mapping(target = "author", source = "authorId")
    public abstract void update(BookUpdateDTO dto, @MappingTarget Book model);
}
