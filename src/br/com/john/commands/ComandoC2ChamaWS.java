package br.com.john.commands;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2ChamaWS implements Callable<String>{

	private PrintStream saidaCliente;

	public ComandoC2ChamaWS(PrintStream saidaCliente) {
		this.saidaCliente = saidaCliente;
	}

	@Override
	public String call() throws InterruptedException {
		System.out.println("Servidor recebeu Comando C2 - WS");
		
		saidaCliente.println("Processando Comando C2 - WS");
		Thread.sleep(10000);
		
		int numero = new Random().nextInt(100) + 1;
		System.out.println("Server finalizou C2 - WS");
		return Integer.toString(numero);
	}

}
