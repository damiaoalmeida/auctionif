package br.edu.ifpb.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_AUCTION")
public class Auction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
				    generator = "auction_seq_gen")
	@SequenceGenerator(name = "auction_seq_gen", 
	   				sequenceName = "tb_auction_seq", 
	   				allocationSize = 1)
	private Long id;

	@Column(name = "starting_price")
	private BigDecimal startingPrice;
	
	@Column(name = "date_start")
	@Temporal(TemporalType.DATE)
	private Date DateStart;

	@Column(name = "date_end")
	@Temporal(TemporalType.DATE)
	private Date DateEnd;

	@Enumerated(EnumType.STRING)
	private AuctionStatus status;

	@ManyToMany
	@JoinTable(name = "TB_AUCTION_BOOK",
		joinColumns = {@JoinColumn(name = "auction_fk")},
		inverseJoinColumns = @JoinColumn(name = "book_fk"))
	private List<Book> books;
}
