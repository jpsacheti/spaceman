package br.edu.fema.spaceman.configuracao;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import android.hardware.SensorManager;

public class Aparelho {
	// Classe de utilitários, atalhos para os parametros do aparelho, como por
	// exemplo, tamanho de tela
	private static SensorManager sensorManager;

	public static void setSensorManager(SensorManager sensor) {
		sensorManager = sensor;
	}

	public static CGPoint screenResolution(CGPoint gcPoint) {
		return gcPoint;
	}

	public static float screenWidth() {
		return winSize().width;
	}

	public static float screenHeight() {
		return winSize().height;
	}

	public static CGSize winSize() {
		return CCDirector.sharedDirector().winSize();
	}

	public static SensorManager getSensormanager() {
		return sensorManager;
	}
}
