package br.com.john.client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import br.com.john.servidor.ServidorTarefas;

public class DistribuirTarefas implements Runnable {

	private Socket socket;
	private ServidorTarefas servidorTarefas;

	public DistribuirTarefas(Socket socket, ServidorTarefas servidorTarefas) {
		this.socket = socket;
		this.servidorTarefas = servidorTarefas;
			
	}

	@Override
	public void run() {
		System.out.println("Distribuindo tarefa para :" +  socket.getPort());
		try {
			Scanner entradaCliente = new Scanner(socket.getInputStream());
			PrintStream	 saidaCliente = new PrintStream(socket.getOutputStream());
			while(entradaCliente.hasNextLine()){
				
				switch(entradaCliente.nextLine()){
				case "c1" :{ saidaCliente.println("escreveu c1"); }break;
				case "c2" :{ saidaCliente.println("escreveu c2"); }break;
				case "fim" :{ saidaCliente.println("desligando..."); servidorTarefas.parar(); }break;
				default :{  saidaCliente.println("invalido"); }
				}
			}
			saidaCliente.close();
			entradaCliente.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
