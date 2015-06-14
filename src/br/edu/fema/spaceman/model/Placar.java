package br.edu.fema.spaceman.model;

import static br.edu.fema.spaceman.configuracao.Aparelho.screenHeight;
import static br.edu.fema.spaceman.configuracao.Aparelho.screenWidth;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCLabel;

public class Placar extends CCLayer {
	private Integer placar;
	private CCLabel label;
	
	public Placar() {
		placar = 0;
		label = CCLabel.makeLabel("Pontos: "+placar.toString(), "ARCADE.ttf", 14);
		setPosition(screenWidth()-50, screenHeight()-50);
		addChild(label);
	}
	
	public void aumentar(){
		placar++;
		label.setString("Pontos: "+placar.toString());
	}
}
