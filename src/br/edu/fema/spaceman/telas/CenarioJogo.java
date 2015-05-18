package br.edu.fema.spaceman.telas;

import static br.edu.fema.spaceman.configuracao.Aparelho.screenHeight;
import static br.edu.fema.spaceman.configuracao.Aparelho.screenResolution;
import static br.edu.fema.spaceman.configuracao.Aparelho.screenWidth;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import br.edu.fema.spaceman.Util;
import br.edu.fema.spaceman.configuracao.Assets;
import br.edu.fema.spaceman.control.Controle;
import br.edu.fema.spaceman.delegate.MotorMeteorosDelegate;
import br.edu.fema.spaceman.delegate.MotorProjetilDelegate;
import br.edu.fema.spaceman.model.Jogador;
import br.edu.fema.spaceman.model.Meteoro;
import br.edu.fema.spaceman.model.Projetil;
import br.edu.fema.spaceman.motor.MotorMeteoros;

//Essa classe orquestra todo o funcionamento do jogo e chama as lógicas encapsuladas por outras classes
public class CenarioJogo extends CCLayer implements MotorMeteorosDelegate,
		MotorProjetilDelegate {

	private Fundo fundo;

	// Meteoros
	private MotorMeteoros motorMeteoros;
	private CCLayer camadaMeteoros;
	private List<Meteoro> arrayMeteoros;

	// Jogador
	private CCLayer camadaJogador;
	private Jogador jogador;
	private List<Jogador> arrayJogador;

	// Projeteis
	private CCLayer camadaProjeteis;
	private List<Projetil> arrayProjeteis;

	private CenarioJogo() {
		this.fundo = new Fundo(Assets.BACKGROUND);
		this.fundo.setPosition(screenResolution(CGPoint.ccp(
				screenWidth() / 2.0f, screenHeight() / 2.0f)));

		this.addChild(this.fundo);

		// Singleton - Adiciona os controles
		Controle camadaControle = Controle.getControles();
		addChild(camadaControle);
		camadaControle.setDelegate(this);

		this.camadaMeteoros = CCLayer.node();
		this.camadaJogador = CCLayer.node();
		this.camadaProjeteis = CCLayer.node();
		this.addChild(this.camadaMeteoros);

		addChild(camadaJogador);
		addChild(camadaProjeteis);
		this.construirTela();

		this.setIsTouchEnabled(true);

	}

	public static CCScene criarJogo() {
		CCScene cena = CCScene.node();
		CenarioJogo cenario = new CenarioJogo();
		cena.addChild(cenario);
		return cena;
	}

	private void construirTela() {
		arrayMeteoros = new ArrayList<Meteoro>();
		motorMeteoros = new MotorMeteoros();
		arrayJogador = new ArrayList<Jogador>();
		arrayProjeteis = new ArrayList<Projetil>();
		jogador = new Jogador();
		jogador.setDelegate(this);
		camadaJogador.addChild(jogador);
		arrayJogador.add(jogador);
	}

	@Override
	public void criarMeteoro(Meteoro meteoro) {
		camadaMeteoros.addChild(meteoro);
		meteoro.setDelegate(this);
		meteoro.iniciar();
		arrayMeteoros.add(meteoro);
	}

	// Dita o que vai acontecer quando o cenário estiver pronto, pós
	// renderização pelo construtor
	@Override
	public void onEnter() {
		super.onEnter();
		schedule("verificarColisao");
		schedule("removerElementos");
		ignicao();
	}

	private void ignicao() {
		addChild(motorMeteoros);
		motorMeteoros.setDelegate(this);
	}

	private boolean verificarColisaoBlocos(List<? extends CCSprite> array1,
			List<? extends CCSprite> array2, CenarioJogo cenario, String hit) {
		boolean result = false;
		for (int i = 0; i < array1.size(); i++) {
			CGRect rect1 = Util.getBordas(array1.get(i));
			for (int j = 0; j < array2.size(); j++) {
				// Pega objeto do segundo array
				CGRect rect2 = Util.getBordas(array2.get(j));
				// Verifica colisão
				if (CGRect.intersects(rect1, rect2)) {
					System.out.println("Aviso - Colisão: " + hit);
					result = true;
					//Invoca o método apropriado para lidar com a colisão
					Method method;
					try {
						method = CenarioJogo.class.getMethod(hit,
								CCSprite.class, CCSprite.class);
						method.invoke(cenario, array1.get(i), array2.get(j));
					} catch (SecurityException e1) {
						e1.printStackTrace();
					} catch (NoSuchMethodException e1) {
						e1.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}

	public void verificarColisao(float dt) {
		this.verificarColisaoBlocos(arrayMeteoros, arrayProjeteis, this,
				"meteoroHit");
		this.verificarColisaoBlocos(this.arrayMeteoros, arrayJogador, this,
				"jogadorHit");
	}

	@SuppressWarnings("unused")
	private void removerElementos(float dt) {
		// Desaloca elementos para evitar vazamento de memória
		if (!arrayMeteoros.isEmpty()) {
			List<Meteoro> remover = new ArrayList<Meteoro>();
			for (Meteoro meteoro : arrayMeteoros) {
				if (meteoro.getY() <= 0) {
					remover.add(meteoro);
					meteoro.removerEParar();
				}
			}
			arrayMeteoros.removeAll(remover);
		}
		if (!arrayProjeteis.isEmpty()) {
			List<Projetil> remover = new ArrayList<Projetil>();
			for (Projetil projetil : arrayProjeteis) {
				if (projetil.getY() >= screenHeight()) {
					// para a atualização e remove e o elemento da tela
					remover.add(projetil);
					projetil.removerEParar();
				}
			}
			arrayProjeteis.removeAll(remover);
		}
	}

	@Override
	public void criarProjetil(Projetil projetil) {
		camadaProjeteis.addChild(projetil);
		projetil.setDelegate(this);
		projetil.start();
		arrayProjeteis.add(projetil);

	}

	public boolean atirar() {
		jogador.atirar();
		return true;
	}

	public void moverDireita() {
		jogador.moverDireita();
	}

	public void moverEsquerda() {
		jogador.moverEsquerda();
	}
	
	public void meteoroHit(CCSprite meteoro, CCSprite projetil){
		((Meteoro) meteoro).kabum();
		((Projetil) projetil).kabum();
	}
	
	public void jogadorHit(CCSprite meteoro, CCSprite jogador){
		
	}

	@Override
	public void removerMeteoro(Meteoro meteoro) {
		arrayMeteoros.remove(meteoro);

	}

	@Override
	public void removerProjetil(Projetil projetil) {
		arrayProjeteis.remove(projetil);

	}
}
