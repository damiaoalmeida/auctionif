package br.edu.ifpb.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Entity
//@NoArgsConstructor
//@Table(name="TB_USER")
public class User2 {

//	@Id
//	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private String name;
}
