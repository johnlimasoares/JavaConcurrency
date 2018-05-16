package br.com.john.commands;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2AcessaBanco implements Callable<String>{

	private PrintStream saidaCliente;

	public ComandoC2AcessaBanco(PrintStream saidaCliente) {
		this.saidaCliente = saidaCliente;
	}

	@Override
	public String call() throws InterruptedException {
		System.out.println("Servidor recebeu Comando C2 - Banco de Dados");
		
		saidaCliente.println("Processando Comando C2 - Banco de Dados");
		Thread.sleep(10000);
		
		int numero = new Random().nextInt(100) + 1;
		System.out.println("Server finalizou C2 - Banco de Dados");
		return Integer.toString(numero);
	}


}
