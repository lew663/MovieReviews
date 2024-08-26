package com.board.movie.scrap.repository;

import com.board.movie.post.entity.PostEntity;
import com.board.movie.scrap.entity.ScrapEntity;
import com.board.movie.scrap.entity.ScrapId;
import com.board.movie.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScrapRepository extends JpaRepository<ScrapEntity, ScrapId> {

  List<ScrapEntity> findByUser(UserEntity user);

  Optional<ScrapEntity> findByUserAndPost(UserEntity user, PostEntity post);
}
