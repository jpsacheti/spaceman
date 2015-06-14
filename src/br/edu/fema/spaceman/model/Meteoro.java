package br.edu.fema.spaceman.model;

import java.util.Random;

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
import br.edu.fema.spaceman.configuracao.Aparelho;
import br.edu.fema.spaceman.delegate.MotorMeteorosDelegate;

public class Meteoro extends CCSprite{
	//Posição do meteoro na tela
	private float x,y;
	
	MotorMeteorosDelegate delegate;
	
	public Meteoro(String imagem){
		//posiciona o meteoro com aletóriamente no cenário
		super(imagem);
		x = new Random(System.currentTimeMillis()).nextInt(Math.round(Aparelho.screenWidth()));
		y = Aparelho.screenHeight();
	}
	
	//inicializa a Thread que atualiza a posição do meteoro
	
	public void iniciar(){
		schedule("atualizar");
	}
	
	//atualiza a posição do meteoro
	public void atualizar(float dt){
		y--;
		setPosition(Aparelho.screenResolution(CGPoint.ccp(x, y)));
	}
	
	public void setDelegate(MotorMeteorosDelegate delegate) {
		this.delegate = delegate;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	//Destroi o meteoro
	public void kabum(){
		delegate.removerMeteoro(this);
		unschedule("atualizar");
		float dt = 0.2f;
		CCScaleBy a1 = CCScaleBy.action(dt, 0.5f);
		CCFadeOut a2 = CCFadeOut.action(dt);
		CCSpawn s1 = CCSpawn.actions(a1, a2);
		CCCallFunc c1 = CCCallFunc.action(this, "remover");
		runAction(CCSequence.actions(s1, c1));
		SoundEngine.sharedEngine().playEffect(
				CCDirector.sharedDirector().getActivity(), R.raw.bang);
	}
	
	public void parar(){
		unschedule("atualizar");
	}
	
	public void remover(){
		this.removeFromParentAndCleanup(true);
	}

	public void removerEParar() {
		parar();
		remover();
	}

}
