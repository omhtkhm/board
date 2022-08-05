package com.cos.project1.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
public class Mylike {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private int likenum;
	private int userid;
	
	@ManyToOne
	@JoinColumn(name = "board_num")
	private Board board;
	
}
