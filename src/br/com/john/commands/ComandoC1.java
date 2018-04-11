package br.com.john.commands;

import java.io.PrintStream;

public class ComandoC1 implements Runnable {

	private PrintStream saidaCliente;

	public ComandoC1(PrintStream saidaCliente) {
		this.saidaCliente = saidaCliente;
	}

	@Override
	public void run() {
		System.out.println("Executando comando C1");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Comando C1 finalizado");
	}

}
