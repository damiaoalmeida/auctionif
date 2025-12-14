package br.edu.ifpb.entity;

public enum StatusType {
	ATIVO("Ativo"),
	INATIVO("Inativo"),
	BLOQUEADO("Bloqueado"),
	PENDENTE("Pendente");

	private final String displayName;

	StatusType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}