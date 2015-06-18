package br.edu.fema.spaceman.telas;

import static br.edu.fema.spaceman.configuracao.Aparelho.screenHeight;
import static br.edu.fema.spaceman.configuracao.Aparelho.screenWidth;
import static br.edu.fema.spaceman.configuracao.Aparelho.screenResolution;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

import br.edu.fema.spaceman.configuracao.Assets;
import br.edu.fema.spaceman.control.Botao;
import br.edu.fema.spaceman.delegate.ButtonDelegate;
import br.edu.fema.spaceman.delegate.PauseDelegate;

public class Pause extends CCLayer implements ButtonDelegate {
	private Botao continuar; // botao para jogar novamente
	private Botao sair; // botao para sair para o menu principal
	private CCColorLayer background; //Controla o fundo esmaecido da tela
	private PauseDelegate delegate; //delegate do pause

	public Pause() {
		//Habilita o touch na tela
		this.setIsTouchEnabled(true);
		this.background = CCColorLayer.node(ccColor4B.ccc4(0, 0, 0, 175),
				screenWidth(), screenHeight());
		this.addChild(this.background);
		
		//Logo
		CCSprite title = CCSprite.sprite(Assets.LOGO);
		title.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2,
				screenHeight() - 130)));
		this.addChild(title);
		
		//Botao para jogar e botão para sair
		this.continuar = new Botao(Assets.PLAY);
		this.sair = new Botao(Assets.EXIT);
		this.addChild(this.continuar);
		this.addChild(this.sair);
		
		continuar.setDelegate(this);
		sair.setDelegate(this);

		//Posiciona os botoes
		this.continuar.setPosition(screenResolution(CGPoint.ccp(
				screenWidth() / 2, screenHeight() - 250)));
		this.sair.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2,
				screenHeight() - 300)));
	}

	@Override
	public void buttonClicked(Botao sender) {
		// Checa se o botao tocado foi o continuar
		if (sender == this.continuar) {
			this.delegate.continuar();
			this.removeFromParentAndCleanup(true);
		}

		// Checa se o botao tocado foi o sair
		if (sender == this.sair) {
			this.delegate.suspender();

		}

	}

	public void setDelegate(PauseDelegate pause) {
		this.delegate = pause;

	}
}
