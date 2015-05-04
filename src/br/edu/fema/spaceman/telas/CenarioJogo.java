package br.edu.fema.spaceman.telas;

import static br.edu.fema.spaceman.configuracao.Aparelho.screenHeight;
import static br.edu.fema.spaceman.configuracao.Aparelho.screenResolution;
import static br.edu.fema.spaceman.configuracao.Aparelho.screenWidth;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.types.CGPoint;

import br.edu.fema.spaceman.configuracao.Assets;
import br.edu.fema.spaceman.control.Controle;
import br.edu.fema.spaceman.delegate.MotorDisparosDelegate;
import br.edu.fema.spaceman.delegate.MotorMeteorosDelegate;
import br.edu.fema.spaceman.model.Jogador;
import br.edu.fema.spaceman.model.Meteoro;
import br.edu.fema.spaceman.model.Projetil;
import br.edu.fema.spaceman.motor.MotorMeteoros;

//Essa classe orquestra todo o funcionamento do jogo e chama as lógicas encapsuladas por outras classes
public class CenarioJogo extends CCLayer implements MotorMeteorosDelegate, MotorDisparosDelegate{
	
	private Fundo fundo;
	
	//Meteoros
	private MotorMeteoros motorMeteoros;
	private CCLayer camadaMeteoros;
	private List<Meteoro> arrayMeteoros;
	
	//Jogador
	private CCLayer camadaJogador;
	private Jogador jogador;
	
	//Projeteis
	private CCLayer camadaProjeteis;
	private List<Projetil> arrayProjeteis;
	
	private CenarioJogo(){
		this.fundo = new Fundo(Assets.BACKGROUND);
		this.fundo.setPosition(
		screenResolution(CGPoint.ccp(screenWidth() / 2.0f,
		screenHeight() / 2.0f)));
		
		
		this.addChild(this.fundo);
		
		//Singleton - Adiciona os controles
		Controle camadaControle = Controle.getControles();
		addChild(camadaControle);
		
		this.camadaMeteoros = CCLayer.node();
		this.camadaJogador = CCLayer.node();
		this.camadaProjeteis = CCLayer.node();
		this.addChild(this.camadaMeteoros);
		
		addChild(camadaJogador);
		addChild(camadaProjeteis);		
		this.construirTela();

		this.setIsTouchEnabled(true);
		
	}
	
	public static CCScene criarJogo(){
		CCScene cena = CCScene.node();
		CenarioJogo cenario = new CenarioJogo();
		cena.addChild(cenario);
		return cena;
	}
	
	private void construirTela(){
		arrayMeteoros = new ArrayList<Meteoro>();
		motorMeteoros = new MotorMeteoros();
		arrayProjeteis = new ArrayList<Projetil>();
		jogador = new Jogador();
		jogador.setDelegate(this);
		camadaJogador.addChild(jogador);
	}

	@Override
	public void criarMeteoro(Meteoro meteoro) {
		camadaMeteoros.addChild(meteoro);
		meteoro.iniciar();
		arrayMeteoros.add(meteoro);
	}

	//Dita o que vai acontecer quando o cenário estiver pronto, pós renderização pelo construtor
	@Override
	public void onEnter() {
		super.onEnter();
		ignicao();
	}
	
	private void ignicao(){
		addChild(motorMeteoros);
		motorMeteoros.setDelegate(this);
	}

	@Override
	public void criarDisparo(Projetil projetil) {
		// TODO Auto-generated method stub
		
	}
}
