package br.edu.fema.spaceman.configuracao;

public class Controlador {
	private static boolean isPausado;
	private static boolean isJogando;

	public static boolean isPausado() {
		return isPausado;
	}

	public static void setPausado(boolean isPausado) {
		Controlador.isPausado = isPausado;
	}

	public static boolean isJogando() {
		return isJogando;
	}

	public static void setJogando(boolean isJogando) {
		Controlador.isJogando = isJogando;
	}
}
