package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController()
@RequestMapping("/avatars")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> postAvatar(@PathVariable long id, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() >= 1024 * 700) {
            return ResponseEntity.badRequest().body("Файл слишком большой");
        }
        avatarService.postAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "{id}/origin_avatar")
    public void getOriginAvatar(@PathVariable long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {

            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }

    }


    @GetMapping(value = "{id}/db_avatar")
    public ResponseEntity<byte[]> getDBAvatar(@PathVariable long id) {
        Avatar avatar = avatarService.findAvatar(id);
        if (avatar == null)
            return ResponseEntity.notFound().build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        httpHeaders.setContentLength(avatar.getFileSize());

        return ResponseEntity.status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(avatar.getPreview());
    }

    @GetMapping("/findAll")
    public List<Avatar> findAll(@RequestParam int number, @RequestParam int size) {
        return avatarService.getAvatarsOnPages(number, size);
    }
}
