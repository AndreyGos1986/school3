package ru.hogwarts.school.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.hogwarts.school.model.Avatar;

import java.util.List;
import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long>, PagingAndSortingRepository <Avatar,Long> {
    Optional<Avatar> findByStudentId(Long studentId);

}
