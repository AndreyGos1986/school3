package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    @Value("${avatar.dir.path}")
    private String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void postAvatar(long stuId, MultipartFile file) throws IOException {
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
        avatar.setStudent(student);
        avatar.setFilePath(path.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setPreview(file.getBytes());
        avatarRepository.save(avatar);
    }

    public Avatar findAvatar(Long id) {
        return avatarRepository.findByStudentId(id).orElse(new Avatar());
    }

    //    private byte[] genPreview (Path filePath) throws IOException {
//
//        try (InputStream inputStream = Files.newInputStream(filePath);
//             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream,1024);
//             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
//                 BufferedImage image = ImageIO.read(bufferedInputStream);
//
//                 int height = image.getHeight()/(image.getWidth()/100);
//                 BufferedImage preview = new BufferedImage(100,height,image.getType());
//                 Graphics2D graphics = preview.createGraphics();
//                 graphics.drawImage(image,0,0,100,height,null);
//                 graphics.dispose();
//                 ImageIO.write(preview,getExtension(filePath.getFileName().toString()),byteArrayOutputStream);
//                 return byteArrayOutputStream.toByteArray();
//
//        }
//    }
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
