package br.edu.fema.spaceman.control;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.types.CGPoint;

import br.edu.fema.spaceman.configuracao.Assets;
import br.edu.fema.spaceman.delegate.ButtonDelegate;
import br.edu.fema.spaceman.telas.CenarioJogo;
import static br.edu.fema.spaceman.configuracao.Aparelho.*;

public class Controle extends CCLayer implements ButtonDelegate {

	private Botao atirar;
	private CenarioJogo delegate;
	private Botao pause;

	public Controle() {
		setIsTouchEnabled(true);

		// Instanciamento dos botoes
		atirar = new Botao(Assets.SHOOT);

		pause = new Botao(Assets.BTNPAUSE);

		// Parametrização do delegate
		atirar.setDelegate(this);
		pause.setDelegate(this);

		// posicionar

		posicionarBotoes();

		// adiciona os botoes na tela
		addChild(atirar);
	}

	public static Controle getControles() {
		return new Controle();
	}

	private void posicionarBotoes() {
		atirar.setPosition(screenResolution(CGPoint.ccp(screenWidth() - 40, 40)));
		pause.setPosition(screenResolution(CGPoint.ccp(40, screenHeight() - 30)));
	}

	public void setDelegate(CenarioJogo cenario) {
		this.delegate = cenario;
	}

	@Override
	public void buttonClicked(Botao sender) {
		if (sender.equals(atirar)) {
			delegate.atirar();
		} else {

		}
	}

}
