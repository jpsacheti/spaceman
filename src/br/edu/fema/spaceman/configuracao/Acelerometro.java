package br.edu.fema.spaceman.configuracao;

import br.edu.fema.spaceman.delegate.AcelerometroDelegate;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Acelerometro implements SensorEventListener {

	private float valX; // valor do X recebido pelo acelerometro
	private static Acelerometro acelerometro; // vide descrição do metodo
												// sharedAcelerometro
	private SensorManager sensorManager; // Sensor do acelerometro
	private AcelerometroDelegate delegate;

	private float calibX;
	private int calibracoes;

	// O construtor privado impede que a classe seja instanciada normalmente.
	private Acelerometro() {
		this.ligarAcelerometro();
	}

	// Essa método registra essa classe como uma ouvinte de acelerometros [pois
	// implementa SensorEventListener] e faz com que
	// a taxa de atualização seja ideal com a dos jogos por meio da constante
	// SENSOR_DELAY_GAME
	public void ligarAcelerometro() {
		sensorManager = Aparelho.getSensormanager();
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		//Esse If serve para realizar a calibração do acelerômetro. Foram usadas 100 iterações para a calibração.
		//Os eixos x e y são testados 100 vezes e então é realizada uma média de calibrações.
		if (calibracoes < 100) {
			this.calibX += event.values[0];
			calibracoes++;
			if (calibracoes == 100) {
				this.calibX /= 100;
			}
			return;
		}
			
		//Aqui é descontada do valor vindo do acelerometro a calibração realizada.
		valX = event.values[0] - calibX;
		if (this.delegate != null) {
			this.delegate.acelerou(valX);
		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	// Devolve a única instância válida dessa classe. Nenhuma outra classe deve
	// instanciar essa, para evitar acelerometros concorrentes.
	public static Acelerometro sharedAcelerometro() {
		if (acelerometro == null) {
			acelerometro = new Acelerometro();
		}
		return acelerometro;
	}

	public void setDelegate(AcelerometroDelegate delegate) {
		this.delegate = delegate;
	}
}
