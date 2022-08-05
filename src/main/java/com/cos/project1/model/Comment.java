package com.cos.project1.model;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Entity(name="tbl_comment3")
public class Comment {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private Long cnum;
	private String content;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date regdate;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "bnum") // bnum
	private Board board;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Override
	public String toString() {
		return "Comment [cnum=" + cnum + ", content=" + content + ", regdate=" + regdate + ", ]";
	}
}
