package br.edu.fema.spaceman.model;

import static br.edu.fema.spaceman.configuracao.Aparelho.screenWidth;

import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCSprite;

import br.edu.fema.spaceman.configuracao.Aparelho;
import br.edu.fema.spaceman.configuracao.Assets;
import br.edu.fema.spaceman.delegate.MotorProjetilDelegate;
//Nave que o jogador controla
public class Jogador extends CCSprite{
	float posX = (Aparelho.screenWidth()+20)/2;
	float posY = 50;
	MotorProjetilDelegate delegate;
	
	public Jogador(){
		super(Assets.SPACESHIP);
		setPosition(posX, posY);
	}
	
	public void setDelegate(MotorProjetilDelegate delegate){
		this.delegate = delegate;
	}

	public void atirar() {
		delegate.criarProjetil(new Projetil(posX, posY));
		
	}
	
	public void moverEsquerda(){
		if(posX > 30){
			posX -=10;
		}
		setPosition(posX, posY);
	}
	
	public void moverDireita(){
		if(posX <screenWidth() - 30){
			posX += 10;
			}
			setPosition(posX, posY);
	}
	
	public void remover(){
		removeFromParentAndCleanup(true);
	}
	
	public void kabum(){
		float dt = 0.2f;
		CCScaleBy a1 = CCScaleBy.action(dt, 2f);
		CCFadeOut a2 = CCFadeOut.action(dt);
		CCSpawn s1 = CCSpawn.actions(a1, a2);
		
		this.runAction(CCSequence.actions(s1));
	}
}
