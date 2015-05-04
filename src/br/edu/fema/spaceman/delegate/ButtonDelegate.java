package br.edu.fema.spaceman.delegate;

import br.edu.fema.spaceman.control.Botao;

//interface responsável pelo gerenciamento do clique do botão
//identifica o input do usuário e execute alguma lógica do jogo
public interface ButtonDelegate {
	public void buttonClicked(Botao sender);
}
