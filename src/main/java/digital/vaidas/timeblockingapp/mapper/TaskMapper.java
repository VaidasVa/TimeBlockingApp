package digital.vaidas.timeblockingapp.mapper;

import digital.vaidas.timeblockingapp.model.Task;
import digital.vaidas.timeblockingapp.repository.DAO.TaskDAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedSourcePolicy = IGNORE)
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "status", defaultValue = "TODO")
    TaskDAO toTaskDAO(Task task);

    Task toTask(TaskDAO taskDAO);
}
