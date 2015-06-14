package br.edu.fema.spaceman.model;

import static br.edu.fema.spaceman.configuracao.Aparelho.screenWidth;

import org.cocos2d.actions.interval.CCFadeOut;
import org.cocos2d.actions.interval.CCScaleBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import br.edu.fema.spaceman.R;
import br.edu.fema.spaceman.configuracao.Acelerometro;
import br.edu.fema.spaceman.configuracao.Aparelho;
import br.edu.fema.spaceman.configuracao.Assets;
import br.edu.fema.spaceman.delegate.AcelerometroDelegate;
import br.edu.fema.spaceman.delegate.MotorProjetilDelegate;

//Nave que o jogador controla
public class Jogador extends CCSprite implements AcelerometroDelegate {
	//Essa variavel controla o "ruido" do acelerômetro, impedindo que a nave sofra turbulências.
	private static final Integer NOISE = 1;
	
	// Posição X e Y do jogador
	private float posX = Aparelho.screenWidth() / 2; // meio da tela
	private float posY = 70; //tamanho da nave + alguns pixels (50+20)
	private float acelX; // variação do acelerometro em X
	private MotorProjetilDelegate delegateTiros;
	private Acelerometro acelerometro;

	public Jogador() {
		super(Assets.SPACESHIP);
		setPosition(posX, posY);
		ativarAcelerometro();
		schedule("update");
	}

	public void setDelegate(MotorProjetilDelegate delegate) {
		this.delegateTiros = delegate;
	}

	public void atirar() {
		delegateTiros.criarProjetil(new Projetil(posX, posY));

	}

	public void moverEsquerda() {
		if (posX > 30) {
			posX -= 10;
		}
		setPosition(posX, posY);
	}

	public void moverDireita() {
		if (posX < screenWidth() - 30) {
			posX += 10;
		}
		setPosition(posX, posY);
	}

	// Pede ao cocos2D que limpe a referencia desse objeto junto as camadas.
	public void remover() {
		removeFromParentAndCleanup(true);
	}

	// Faz a animação de explodir a nave. Um sequencial de fade-out
	// [esmaecimento] seguido de escalamento [aumento da nave]
	public void kabum() {
		float dt = 0.2f;
		CCScaleBy a1 = CCScaleBy.action(dt, 2f);
		CCFadeOut a2 = CCFadeOut.action(dt);
		CCSpawn s1 = CCSpawn.actions(a1, a2);
		this.runAction(CCSequence.actions(s1));
		SoundEngine.sharedEngine().playEffect(
				CCDirector.sharedDirector().getActivity(), R.raw.over);
		SoundEngine.sharedEngine().pauseSound();

	}

	// Implementação do delegate. Quando ele receber a ação do acelerômetro,
	// deve executar esse método que atualiza
	// a nave na tela
	@Override
	public void acelerou(float x) {
		acelX = x;
	}
	
	/*
	 * Passa a referencia de acelerometro para esse objeto. Como ele não deve ser instanciado, ele recebe a instancia
	 * que é criada ao se chamar o método sharedAcelerometro()
	 */
	private void ativarAcelerometro(){
		Acelerometro.sharedAcelerometro().ligarAcelerometro();
		this.acelerometro = Acelerometro.sharedAcelerometro();
		this.acelerometro.setDelegate(this);
	}
	
	public void update(float dt) {
		
			if(this.acelX< -NOISE){
				this.posX++;
			}
			
			if(this.acelX> NOISE){
				this.posX--;
			}
			
			// Atualizar posição do jogador.
			this.setPosition(CGPoint.ccp(this.posX, this.posY));

		
	}
}
