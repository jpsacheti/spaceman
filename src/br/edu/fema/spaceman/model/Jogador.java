package br.edu.fema.spaceman.model;

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
import br.edu.fema.spaceman.configuracao.Controlador;
import br.edu.fema.spaceman.delegate.AcelerometroDelegate;
import br.edu.fema.spaceman.delegate.MotorProjetilDelegate;
import static br.edu.fema.spaceman.configuracao.Aparelho.screenWidth;

//Nave que o jogador controla
public class Jogador extends CCSprite implements AcelerometroDelegate {
	//Essa variavel controla o "ruido" do acelerômetro, impedindo que a nave sofra turbulências ou acelere em qualquer simples movimento
	private static final Float NOISE = 0.5F;
	
	// Posição X e Y do jogador
	private float posX = Aparelho.screenWidth() / 2; // meio da tela
	private float posY = 70; //tamanho da nave + alguns pixels (50+20)
	private float acelX; // variação do acelerometro em X
	private MotorProjetilDelegate delegateTiros;
	private Acelerometro acelerometro; //Instancia que vai controlar o acelerometro

	//Cria uma nova instancia de jogador, posiciona na tela e coloca a função 'update' para rodar a toda atualização de frame
	public Jogador() {
		super(Assets.SPACESHIP);
		setPosition(posX, posY);
		ativarAcelerometro();
		schedule("update");
	}

	public void setDelegate(MotorProjetilDelegate delegate) {
		this.delegateTiros = delegate;
	}

	//Pede à classe que implementa os disparos que insira um novo na tela
	public void atirar() {
		if (Controlador.isPausado())
			return;
		delegateTiros.criarProjetil(new Projetil(posX, posY));

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
	// deve executar esse método que recebe a variação de movimento do acelerômetro
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
	
	//Função que atualiza o movimento da nave na tela
	public void update(float dt) {
		if (Controlador.isPausado())
			return;
		
			if(this.acelX< -NOISE){
				if (posX < screenWidth() - 30) {
					this.posX++;
				}
				
			}
			
			if(this.acelX> NOISE){
				if (posX > 30) {
					posX--;
				}
			}
			
			// Atualizar posição do jogador.
			this.setPosition(CGPoint.ccp(this.posX, this.posY));

		
	}
}
