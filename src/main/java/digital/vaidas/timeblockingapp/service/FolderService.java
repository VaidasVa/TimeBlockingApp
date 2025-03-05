package digital.vaidas.timeblockingapp.service;

import digital.vaidas.timeblockingapp.model.Folder;
import digital.vaidas.timeblockingapp.repository.FolderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {

    private final FolderRepository folderRepository;

    public FolderService(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public Folder createFolder(String userId, String name) {
        Folder folder = new Folder();
        folder.setUserId(userId);
        folder.setName(name);
        return folderRepository.save(folder);
    }

    public List<Folder> getFoldersByUser(String userId) {
        return folderRepository.findByUserId(userId);
    }
}