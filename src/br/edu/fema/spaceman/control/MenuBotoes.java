package br.edu.fema.spaceman.control;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import static br.edu.fema.spaceman.configuracao.Aparelho.screenHeight;
import static br.edu.fema.spaceman.configuracao.Aparelho.screenWidth;
import static br.edu.fema.spaceman.configuracao.Aparelho.screenResolution;
import br.edu.fema.spaceman.configuracao.Assets;
import br.edu.fema.spaceman.delegate.ButtonDelegate;
import br.edu.fema.spaceman.telas.CenarioJogo;

public class MenuBotoes extends CCLayer implements ButtonDelegate {
	private Botao jogar;
	//private Botao recordes;
	//private Botao ajuda;
	//private Botao som;

	public MenuBotoes() {
		this.setIsTouchEnabled(true);
		this.jogar = new Botao(Assets.PLAY);
		//this.recordes = new Botao(Assets.HIGHSCORE);
		//this.ajuda = new Botao(Assets.HELP);
		//this.som = new Botao(Assets.SOUND);

		setPosicaoBotoes();

		addChild(jogar);
		//addChild(recordes);
		//addChild(ajuda);
		//addChild(som);
		jogar.setDelegate(this);
		//recordes.setDelegate(this);
	//	ajuda.setDelegate(this);
	//	som.setDelegate(this);

	}

	private void setPosicaoBotoes() {
		jogar.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2,
				screenHeight() - 250)));
	//	recordes.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2,
	//			screenHeight() - 300)));
	//	ajuda.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2,
	//			screenHeight() - 350)));
	//	som.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2 - 100,
	//			screenHeight() - 420)));
	}

	@Override
	public void buttonClicked(Botao sender) {
		if (sender.equals(this.jogar)) {
			System.out.println("Botão Jogar pressionado");
			CCDirector.sharedDirector().replaceScene(
					CCFadeTransition.transition(1.0f,
					CenarioJogo.criarJogo()));
		}

	}
	
}
