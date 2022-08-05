package com.cos.project1.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name="tbl_user")
@Data
public class User {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
	private int id;
	private String username;
	private String password;
	private String email;
	private String address;
	private String role; //ROLE_USER / ROLE_ADMIN
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date regdate;
	
	@OneToMany(mappedBy = "user"/*, cascade = CascadeType.ALL, orphanRemoval = true*/)
	private List<Board> boards = new ArrayList<>();
}
