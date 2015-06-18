package br.edu.fema.spaceman.motor;

import java.util.Random;

import org.cocos2d.layers.CCLayer;

import br.edu.fema.spaceman.configuracao.Assets;
import br.edu.fema.spaceman.configuracao.Controlador;
import br.edu.fema.spaceman.delegate.MotorMeteorosDelegate;
import br.edu.fema.spaceman.model.Meteoro;

public class MotorMeteoros extends CCLayer{
	MotorMeteorosDelegate delegate;
	
	public MotorMeteoros(){
		//agenda os meteoros para aparecerem a cada 0.1 milissegundos
		schedule("motorMeteoros", 1.0f/10f);
	}
	
	public void motorMeteoros(float dt){
		//Aleatório: o Meteoro tem 0.3% de chance de aparecer a cada ciclo
		if(Controlador.isPausado()) return;
		if(new Random(System.currentTimeMillis()).nextInt(30) == 0){
			getDelegate().criarMeteoro(new Meteoro(Assets.METEOR));
		}
	}
	
	public MotorMeteorosDelegate getDelegate() {
		return delegate;
	}
	
	public void setDelegate(MotorMeteorosDelegate delegate) {
		this.delegate = delegate;
	}
}
