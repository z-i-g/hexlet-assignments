package exercise.mapper;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.model.Task;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {

    // BEGIN
    public abstract Task map(TaskCreateDTO dto);

    @Mapping(target = "assigneeId", expression = "java(model.getAssignee().getId())")
    public abstract TaskDTO map(Task model);

//    @Mapping(target = "model.assignee.id", source = "dto.assigneeId")
    public abstract void update(TaskUpdateDTO dto, @MappingTarget Task model);
    // END

}
