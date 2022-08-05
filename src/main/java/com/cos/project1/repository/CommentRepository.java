package com.cos.project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.project1.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Modifying
	@Query(value = "insert board(content, regdate, bnum, user_id) values(?1,now(), ?2, ?3)", nativeQuery = true)
	public void commentInsert(String content, int bnum, int user_id);

    @Query("select sc from tbl_comment3 sc join fetch sc.board where bnum=?1")
    public List<Comment> findByBnum(Long bnum);

}
