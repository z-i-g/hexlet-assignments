package exercise.mapper;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.BooDto;
import exercise.dto.AuthorUpdateDTO;
import exercise.model.Author;
import org.mapstruct.*;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class AuthorMapper {

    // BEGIN
    public abstract Author map(AuthorCreateDTO dto);

    public abstract BooDto map(Author model);
    // END

    public abstract void update(AuthorUpdateDTO dto, @MappingTarget Author model);
}
