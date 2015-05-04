package br.edu.fema.spaceman.telas;

import static br.edu.fema.spaceman.configuracao.Aparelho.screenHeight;
import static br.edu.fema.spaceman.configuracao.Aparelho.screenResolution;
import static br.edu.fema.spaceman.configuracao.Aparelho.screenWidth;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import br.edu.fema.spaceman.configuracao.Assets;
import br.edu.fema.spaceman.control.MenuBotoes;

public class TelaInicial extends CCLayer {
	public Fundo fundo;

	// Esse método estrutura a tela inicial junto com a imagem de fundo.
	public CCScene scene() {
		// Obtém uma cena do cocos2D
		CCScene scene = CCScene.node();
		// Adiciona essa classe como parte da cena
		scene.addChild(this);
		return scene;
	}

	public TelaInicial() {
		//cria a tela inicial com o fundo 
		this.fundo = new Fundo(Assets.BACKGROUND);
		//aloca o fundo para o centro da tela
		this.fundo.setPosition(screenResolution(CGPoint.ccp(
				screenWidth() / 2.0f, screenHeight() / 2.0f)));
		//adiciona o fundo na cena
		this.addChild(this.fundo);
		
		CCSprite titulo = CCSprite.sprite(Assets.LOGO);
		titulo.setPosition(screenResolution(CGPoint.ccp(screenWidth()/2, screenHeight()-130)));
		this.addChild(titulo);
		
		MenuBotoes menu = new MenuBotoes();
		this.addChild(menu);
	}
}
