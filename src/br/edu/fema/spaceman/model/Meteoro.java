package br.edu.fema.spaceman.model;

import java.util.Random;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import br.edu.fema.spaceman.configuracao.Aparelho;

public class Meteoro extends CCSprite{
	//Posição do meteoro na tela
	private float x,y;
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

}
