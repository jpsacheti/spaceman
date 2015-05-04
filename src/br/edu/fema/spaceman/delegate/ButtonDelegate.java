package br.edu.fema.spaceman.delegate;

import br.edu.fema.spaceman.control.Botao;

//interface respons�vel pelo gerenciamento do clique do bot�o
//identifica o input do usu�rio e execute alguma l�gica do jogo
public interface ButtonDelegate {
	public void buttonClicked(Botao sender);
}
