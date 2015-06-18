package br.edu.fema.spaceman.telas;

import static br.edu.fema.spaceman.configuracao.Aparelho.screenHeight;
import static br.edu.fema.spaceman.configuracao.Aparelho.screenResolution;
import static br.edu.fema.spaceman.configuracao.Aparelho.screenWidth;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import br.edu.fema.spaceman.R;
import br.edu.fema.spaceman.Util;
import br.edu.fema.spaceman.configuracao.Assets;
import br.edu.fema.spaceman.configuracao.Controlador;
import br.edu.fema.spaceman.control.Controle;
import br.edu.fema.spaceman.delegate.MotorMeteorosDelegate;
import br.edu.fema.spaceman.delegate.MotorProjetilDelegate;
import br.edu.fema.spaceman.delegate.PauseDelegate;
import br.edu.fema.spaceman.model.Jogador;
import br.edu.fema.spaceman.model.Meteoro;
import br.edu.fema.spaceman.model.Placar;
import br.edu.fema.spaceman.model.Projetil;
import br.edu.fema.spaceman.motor.MotorMeteoros;

//Essa classe orquestra todo o funcionamento do jogo e chama as lógicas encapsuladas por outras classes
public class CenarioJogo extends CCLayer implements MotorMeteorosDelegate,
		MotorProjetilDelegate, PauseDelegate {

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

	// Placar
	private CCLayer camadaPlacar;
	private Placar placar;

	// Tela de Pause
	private Pause pauseScreen;
	private CCLayer layerTop;

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
		this.camadaPlacar = CCLayer.node();
		this.addChild(this.camadaMeteoros);

		addChild(camadaJogador);
		addChild(camadaProjeteis);
		addChild(camadaPlacar);

		this.layerTop = CCLayer.node();
		this.addChild(this.layerTop);

		this.construirTela();

		preloadCache();
		// começa a musica no jogo, o parâmetro true serve para indicar que a
		// música deve repetir indefinidamente.
		SoundEngine.sharedEngine().playSound(
				CCDirector.sharedDirector().getActivity(), R.raw.musica, true);
		this.setIsTouchEnabled(true);

	}

	public static CCScene criarJogo() {
		CCScene cena = CCScene.node();
		CenarioJogo cenario = new CenarioJogo();
		cena.addChild(cenario);
		return cena;
	}

	// Cria o cache de musicas para evitar carregamentos desnecessários
	private void preloadCache() {
		SoundEngine.sharedEngine().preloadEffect(
				CCDirector.sharedDirector().getActivity(), R.raw.laser);
		SoundEngine.sharedEngine().preloadEffect(
				CCDirector.sharedDirector().getActivity(), R.raw.bang);
		SoundEngine.sharedEngine().preloadEffect(
				CCDirector.sharedDirector().getActivity(), R.raw.over);

	}

	// Auto-explicativo. Carrega e instancia todos os objetos dessa classe
	private void construirTela() {
		arrayMeteoros = new ArrayList<Meteoro>();
		motorMeteoros = new MotorMeteoros();
		arrayJogador = new ArrayList<Jogador>();
		arrayProjeteis = new ArrayList<Projetil>();
		jogador = new Jogador();
		jogador.setDelegate(this);
		placar = new Placar();
		camadaPlacar.addChild(placar);
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
		Controlador.setJogando(true);
		Controlador.setPausado(false);
		SoundEngine.sharedEngine().setEffectsVolume(1f);
		SoundEngine.sharedEngine().setSoundVolume(1f);
		ignicao();
	}

	// Ativa a rolagem dos meteoros
	private void ignicao() {
		addChild(motorMeteoros);
		motorMeteoros.setDelegate(this);
	}

	// Método que avalia a colisão dos objetos na tela
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
					// Invoca o método apropriado para lidar com a colisão
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

	// Método que roda a cada instante no jogo, avaliando colisão
	// projetil-meteoro e meteoro-jogador
	public void verificarColisao(float dt) {
		this.verificarColisaoBlocos(arrayMeteoros, arrayProjeteis, this,
				"meteoroHit");
		this.verificarColisaoBlocos(this.arrayMeteoros, arrayJogador, this,
				"jogadorHit");
	}

	@SuppressWarnings("unused")
	private void removerElementos(float dt) {
		// Desaloca elementos para evitar vazamento de memória. O objeto é
		// dereferenciado tanto no array quanto na Layer.

		// Percorre o array de meteoros. Caso encontre um que tenha atingido o
		// fim da tela, ele é removido.
		if (!arrayMeteoros.isEmpty()) {
			Iterator<Meteoro> iterator = arrayMeteoros.iterator();
			while (iterator.hasNext()) {
				Meteoro c = iterator.next();
				if (c.getY() <= 0) {
					iterator.remove();
					c.removerEParar();
				}
			}
		}

		// Percorre o array de tiros. Caso encontre um que tenha atingido o fim
		// da tela, ele é removido.
		if (!arrayProjeteis.isEmpty()) {
			Iterator<Projetil> iterator = arrayProjeteis.iterator();
			while (iterator.hasNext()) {
				Projetil c = iterator.next();
				if (c.getY() >= screenHeight()) {
					iterator.remove();
					c.removerEParar();
				}
			}
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

	public void meteoroHit(CCSprite meteoro, CCSprite projetil) {
		((Meteoro) meteoro).kabum();
		((Projetil) projetil).kabum();
		placar.aumentar();
	}

	public void jogadorHit(CCSprite meteoro, CCSprite jogador) {
		((Meteoro) meteoro).kabum();
		((Jogador) jogador).kabum();
		CCDirector.sharedDirector().replaceScene(new GameOver(placar).scene());
	}

	@Override
	public void removerMeteoro(Meteoro meteoro) {
		arrayMeteoros.remove(meteoro);

	}

	@Override
	public void removerProjetil(Projetil projetil) {
		arrayProjeteis.remove(projetil);

	}

	@Override
	public void suspender() {
		SoundEngine.sharedEngine().setEffectsVolume(0f);
		SoundEngine.sharedEngine().setSoundVolume(0f);

		CCDirector.sharedDirector().replaceScene(new TelaInicial().scene());
	}

	@Override
	public void continuar() {
		if (Controlador.isPausado() || !Controlador.isJogando()) {
			// Continua o jogo
			this.pauseScreen = null;
			Controlador.setPausado(false);
			Controlador.setJogando(true);
			this.setIsTouchEnabled(true);
		}

	}

	public void pausarJogo() {
		if (Controlador.isJogando()) {
			Controlador.setPausado(true);
		}

		if (pauseScreen == null) {
			this.pauseScreen = new Pause();
			this.layerTop.addChild(this.pauseScreen);
			this.pauseScreen.setDelegate(this);
		}

	}

	@Override
	public void pausarEMostrarTela() {
		if (Controlador.isJogando()) {
			pausarJogo();
		}
	}

}
