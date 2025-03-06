package digital.vaidas.timeblockingapp.service;

import digital.vaidas.timeblockingapp.mapper.FolderMapper;
import digital.vaidas.timeblockingapp.model.Folder;
import digital.vaidas.timeblockingapp.repository.DAO.FolderDAO;
import digital.vaidas.timeblockingapp.repository.FolderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FolderService {

    private final FolderRepository folderRepository;

    public FolderService(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public Folder createFolder(String userId, String name) {
        return FolderMapper.INSTANCE.toFolder(folderRepository.save(new FolderDAO(userId, name)));
    }
    public List<Folder> getFoldersByUser(String userId) {
        return folderRepository.findByUserId(userId).stream()
                .map(FolderMapper.INSTANCE::toFolder)
                .collect(Collectors.toList());
    }
}