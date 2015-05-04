package br.edu.fema.spaceman;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import br.edu.fema.spaceman.configuracao.Aparelho;
import br.edu.fema.spaceman.telas.TelaInicial;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//parametros de configuração da tela
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		
		// configura a tela - instancia um novo renderizador openGL do cocos2D
		CCGLSurfaceView glSurfaceView = new CCGLSurfaceView(this);
		glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		
		setContentView(glSurfaceView);
		CCDirector.sharedDirector().attachInView(glSurfaceView);
		configSensormanager();
		CCDirector.sharedDirector().setScreenSize(320, 480);
		
		// abre tela principal

		CCScene scene = new TelaInicial().scene();
		CCDirector.sharedDirector().runWithScene(scene);
	}

	private void configSensormanager() {
		SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Aparelho.setSensorManager(sensorManager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
