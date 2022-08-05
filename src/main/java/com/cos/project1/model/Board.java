package com.cos.project1.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
@Entity(name="fit_board")
public class Board {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	@Column(name="idx")
	private int num; // 게시물 번호
	private String title; // 게시물 제목
	@Column(name="nickname")
	private String writer; // 작성자
	@Column(name="contents")
	private String content; // 내용
//	private String price; // 가격
//	private String fileName; // 이미지업로드용 
//	private String originalFileName;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="regidate")
	private Date regdate;
	@Column(name="hits")
	private Long hitcount; // 조회수
	@Column(name="likes")
	private Long replycount; // 좋아요
//	private Long commentcount;
	private String b_flag; // ?

	
	@ManyToOne(optional = true)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	private User user;// 유저정보

	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("board")
	private List<Comment> comments;

	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("board")
	private List<Image> imgs = new ArrayList<>();
//	public void addImg(Image img) {
//		img.setBoard(this); // 키포인트, 자식으로 부모의 보드번호를 불러와야한다 
//		imgs.add(img); // 
//	}
	
	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Mylike> likes = new ArrayList<Mylike>();
	

	@PrePersist
	public void prePersist() {
		this.hitcount = this.hitcount == null ? 0 : this.hitcount;
		this.replycount = this.replycount == null ? 0 : this.replycount;
	}


	@Override
	public String toString() {
		return "Board [num=" + num + ", title=" + title + ", writer=" + writer + ", content=" + content + ", price="
				+ "]";
	}
}