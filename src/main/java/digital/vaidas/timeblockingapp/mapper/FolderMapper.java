package digital.vaidas.timeblockingapp.mapper;

import digital.vaidas.timeblockingapp.model.Folder;
import digital.vaidas.timeblockingapp.repository.DAO.FolderDAO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FolderMapper {

    FolderMapper INSTANCE = Mappers.getMapper(FolderMapper.class);

    Folder toFolder(FolderDAO folderDAO);

    FolderDAO toFolderDAO(Folder folder);
}
