package br.edu.fema.spaceman.model;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import br.edu.fema.spaceman.R;
import br.edu.fema.spaceman.configuracao.Assets;
import br.edu.fema.spaceman.delegate.MotorProjetilDelegate;
import static br.edu.fema.spaceman.configuracao.Aparelho.*;

public class Projetil extends CCSprite{
	private float x, y;
	private MotorProjetilDelegate delegate;
	
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
	
	public void setDelegate(MotorProjetilDelegate delegate){
		this.delegate = delegate;
	}
	
	public void start(){
		SoundEngine.sharedEngine().playEffect(
				CCDirector.sharedDirector().getActivity(), R.raw.laser);
	}
	
	public float getY() {
		return y;
	}
	
	public float getX() {
		return x;
	}
	
	public void parar(){
		unschedule("atualizar");
	}
	
	public void remover(){
		removeFromParentAndCleanup(true);
	}
	
	//Isso vai ser um estouro
	public void kabum(){
		delegate.removerProjetil(this);
		parar();
		
		float dt = 0.2f;
		CCScaleBy a1 = CCScaleBy.action(dt, 2f);
		CCFadeOut a2 = CCFadeOut.action(dt);
		CCSpawn s1 = CCSpawn.actions(a1, a2);
		
		CCCallFunc c1 = CCCallFunc.action(this, "remover");
		
		runAction(CCSequence.actions(s1, c1));
	}
	
	public void removerEParar(){
		parar();
		remover();
	}
}
