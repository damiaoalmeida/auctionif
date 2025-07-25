package br.edu.ifpb.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="TB_USER_CONTENT")
@SuppressWarnings("serial")
public class UserContent implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, 
    	generator = "userc_seq_gen")
	@SequenceGenerator(name = "userc_seq_gen", 
		sequenceName = "tb_userc_seq", 
		allocationSize = 1)
    private Long id;

	@Lob
	private byte[] photo;
	
//	@OneToOne(fetch = FetchType.EAGER, mappedBy = "content")
//	private User user;
//
//	@Override
//	public String toString() {
//		return "UserContent [id=" + id + ", photo=" + Arrays.toString(photo) + ", user=" + user.getId() + "]";
//	}
}