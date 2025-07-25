package br.edu.ifpb.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_BOOK")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
				    generator = "book_seq_gen")
	@SequenceGenerator(name = "book_seq_gen", 
	   				sequenceName = "tb_book_seq", 
	   				allocationSize = 1)
	private Long id;
	
	private String title;
	
	private int pages;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_FK")
	private User owner;

	@ManyToMany(mappedBy = "books")
	private List<Auction> actions;
}


