package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    @Value("${avatar.dir.path}")
    private String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    private Logger avatarServiceLogger = LoggerFactory.getLogger(AvatarService.class);


    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
        avatarServiceLogger.info("Сервис работы с аватарками запущен");
    }

    public void postAvatar(long stuId, MultipartFile file) throws IOException {
        avatarServiceLogger.info("Загрузка картинки для студента по его Id");
        Student student = studentService.getStudent(stuId);
        Path path = Path.of(avatarsDir, stuId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(path, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(stuId);
        if (avatar!=null) {
            avatarServiceLogger.info("Картинка прикрепляется к указанному по Id студету");
        } else {
            avatarServiceLogger.info (" Методом findAvatar() cоздаётся новый аватар");
        }
        avatar.setStudent(student);
        avatar.setFilePath(path.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setPreview(file.getBytes());
        avatarRepository.save(avatar);

    }

    public Avatar findAvatar(Long id) {
        avatarServiceLogger.info("Поиск картинки по указанному Id, а в случае необнаружения, создание новой картинки");
        return avatarRepository.findByStudentId(id).orElse(new Avatar());
        }

   public List <Avatar> getAvatarsOnPages (int num, int size) {
       avatarServiceLogger.info("По-страничный вывод картинок по указанной странице и её размеру");
       return avatarRepository.findAll(PageRequest.of(num - 1, size)).getContent();
        }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
