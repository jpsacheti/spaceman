package br.edu.fema.spaceman.model;

import org.cocos2d.nodes.CCSprite;

import br.edu.fema.spaceman.configuracao.Aparelho;
import br.edu.fema.spaceman.configuracao.Assets;
import br.edu.fema.spaceman.delegate.MotorDisparosDelegate;

//Nave que o jogador controla
public class Jogador extends CCSprite{
	//Posição X e Y do jogador
	float posX = Aparelho.screenWidth()/2;
	float posY = 50;
	MotorDisparosDelegate delegate;
	
	public Jogador(){
		super(Assets.SPACESHIP);
		setPosition(posX, posY);
	}
	
	public void setDelegate(MotorDisparosDelegate delegate){
		this.delegate = delegate;
	}
}
