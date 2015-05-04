package br.edu.fema.spaceman.control;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.view.MotionEvent;
import br.edu.fema.spaceman.delegate.ButtonDelegate;

public class Botao extends CCLayer {
	private CCSprite buttonImage;
	private ButtonDelegate delegate;

	public Botao(String img) {
		this.setIsTouchEnabled(true);
		this.buttonImage = CCSprite.sprite(img);
		addChild(this.buttonImage);
	}

	// adiciona o delegate do bot�o, que vai definir seu comportamento
	public void setDelegate(ButtonDelegate sender) {
		this.delegate = sender;
	}

	// registra o bot�o como "toc�vel"
	@Override
	protected void registerWithTouchDispatcher() {
		CCTouchDispatcher.sharedDispatcher()
				.addTargetedDelegate(this, 0, false);
	}
	
	//Analisa o toque do usu�rio e verifica se est� dentro dos limites do bot�o
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint touchLocation = CGPoint.make(event.getX(), event.getY());
		touchLocation = CCDirector.sharedDirector().convertToGL(touchLocation);
		touchLocation = this.convertToNodeSpace(touchLocation);
		// Verifica toque no bot�o
		if (CGRect.containsPoint(this.buttonImage.getBoundingBox(),
				touchLocation)) {
			delegate.buttonClicked(this);
		}
		return true;
	}

}
