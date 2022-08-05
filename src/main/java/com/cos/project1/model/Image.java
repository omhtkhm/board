package com.cos.project1.model;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
public class Image {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private int imgnum; //이미지번호
	private String fileName; // 이미지업로드용 
	private String originalFileName;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date regdate;
	
	@ManyToOne/*(fetch = FetchType.LAZY, cascade = CascadeType.ALL)*/
	@JoinColumn(name = "board_num") //join이 주인이되고 mapped by 는 주인이 안된다. 
	private Board board; //이 board라는 객체에 값을 먼저 받고 
	
	//JPA로직은 자식에 부모를 넣는다. 
	
}
