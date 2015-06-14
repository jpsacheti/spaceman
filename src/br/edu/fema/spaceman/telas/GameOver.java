package br.edu.fema.spaceman.telas;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import static br.edu.fema.spaceman.configuracao.Aparelho.*;
import br.edu.fema.spaceman.configuracao.Assets;
import br.edu.fema.spaceman.control.Botao;
import br.edu.fema.spaceman.delegate.ButtonDelegate;
import br.edu.fema.spaceman.model.Placar;

public class GameOver extends CCLayer implements ButtonDelegate {
	private Fundo fundo;
	private Botao iniciar;

	public CCScene scene() {
		CCScene scene = CCScene.node();
		scene.addChild(this);
		return scene;
	}

	public GameOver(Placar placar) {
		// background
		this.fundo = new Fundo(Assets.BACKGROUND);
		this.fundo.setPosition(screenResolution(CGPoint.ccp(
				screenWidth() / 2.0f, screenHeight() / 2.0f)));
		this.addChild(this.fundo);
		// image
		CCSprite title = CCSprite.sprite(Assets.GAMEOVER);
		title.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2,
				screenHeight() - 130)));
		addChild(title);

		placar.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2,
				screenHeight() + 200)));
		addChild(placar);

		this.setIsTouchEnabled(true);
		this.iniciar = new Botao(Assets.PLAY);
		this.iniciar.setPosition(screenResolution(CGPoint.ccp(
				screenWidth() / 2, screenHeight() - 300)));
		this.iniciar.setDelegate(this);
		addChild(this.iniciar);
	}

	@Override
	public void buttonClicked(Botao sender) {
		if (sender.equals(this.iniciar)) {
			SoundEngine.sharedEngine().pauseSound();
			CCDirector.sharedDirector().replaceScene(new TelaInicial().scene());
		}
	}

}
