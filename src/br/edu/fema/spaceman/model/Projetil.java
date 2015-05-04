package br.edu.fema.spaceman.model;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import br.edu.fema.spaceman.configuracao.Assets;
import br.edu.fema.spaceman.delegate.MotorDisparosDelegate;
import static br.edu.fema.spaceman.configuracao.Aparelho.*;

public class Projetil extends CCSprite{
	private float x, y;
	private MotorDisparosDelegate delegate;
	
	public Projetil(float x, float y){
		super(Assets.PROJECTILE);
		this.x = x;
		this.y = y;
		setPosition(this.x, this.y);
		schedule("atualizar");
	}
	
	//atualiza o tiro na tela
	public void atualizar(float dt){
		y = y+2;
		setPosition(screenResolution(CGPoint.ccp(this.x, this.y)));
	}
	
	public void setDelegate(MotorDisparosDelegate delegate){
		this.delegate = delegate;
	}
	
	public void start(){
		System.out.println("Tiro se move");
	}
}
