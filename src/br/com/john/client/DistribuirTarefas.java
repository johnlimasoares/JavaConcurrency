package br.com.john.client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import br.com.john.commands.ComandoC1;
import br.com.john.commands.ComandoC2AcessaBanco;
import br.com.john.commands.ComandoC2ChamaWS;
import br.com.john.servidor.ServidorTarefas;

public class DistribuirTarefas implements Runnable {

	private Socket socket;
	private ServidorTarefas servidorTarefas;
	private ExecutorService threadPoll;
	private BlockingQueue<String> filaComandos;

	public DistribuirTarefas(Socket socket, BlockingQueue<String> filaComandos, ServidorTarefas servidorTarefas, ExecutorService threadPoll) {
		this.socket = socket;
		this.filaComandos = filaComandos;
		this.servidorTarefas = servidorTarefas;
		this.threadPoll = threadPoll;

	}

	@Override
	public void run() {
		System.out.println("Distribuindo tarefa para :" + socket.getPort());
		try {
			Scanner entradaCliente = new Scanner(socket.getInputStream());
			PrintStream saidaCliente = new PrintStream(socket.getOutputStream());
			while (entradaCliente.hasNextLine()) {
				String comando = entradaCliente.nextLine();
				switch (comando) {
					case "c1": {
						saidaCliente.println("escreveu c1");
						threadPoll.execute(new ComandoC1(saidaCliente));
						break;
					}					
					case "c2": {
						saidaCliente.println("escreveu c2");
						ComandoC2ChamaWS comandoC2ChamaWS = new ComandoC2ChamaWS(saidaCliente);
						ComandoC2AcessaBanco comandoC2AcessaBanco = new ComandoC2AcessaBanco(saidaCliente);
						
						Future<String> futureWS = threadPoll.submit(comandoC2ChamaWS);
						Future<String> futureBanco = threadPoll.submit(comandoC2AcessaBanco);
						this.threadPoll.submit(new JuntaResultadoFutureWSFutureBanco(futureWS,futureBanco,saidaCliente));
						break;
					}										
					case "c3": {
						this.filaComandos.put(comando);
						saidaCliente.println("comando c3 adicionado na fila");
						break;
					}
					case "fim": {
						saidaCliente.println("desligando...");
						servidorTarefas.parar();
						break;
					}
					default: {
						saidaCliente.println("invalido");
					}
				}
			}
			saidaCliente.close();
			entradaCliente.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
