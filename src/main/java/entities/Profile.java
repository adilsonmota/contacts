package entities;

public class Profile {
	
	private String name;
	private boolean view;			//	PODE VER DADOS CONFIDENCIAIS? VISITANTE PODE VISUALIZAR APENAS DADOS BÁSICOS - OCULTAR CPF, SALÁRIO E ENDEREÇO
	private boolean edit;			//	PODE EDITAR?
	private boolean delete;			//	PODE DELETAR?
	
	private User user;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
