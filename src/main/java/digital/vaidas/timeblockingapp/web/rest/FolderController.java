package digital.vaidas.timeblockingapp.web.rest;

import digital.vaidas.timeblockingapp.model.Folder;
import digital.vaidas.timeblockingapp.service.FolderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folders")
public class FolderController {

    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping
    public Folder createFolder(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal,
                               @RequestParam String name) {
        String userId = principal.getAttribute("sub");
        return folderService.createFolder(userId, name);
    }

    @GetMapping
    public List<Folder> getFolders(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        String userId = principal.getAttribute("sub");
        return folderService.getFoldersByUser(userId);
    }
}