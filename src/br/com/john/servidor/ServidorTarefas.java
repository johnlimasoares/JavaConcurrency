package br.com.john.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.john.client.DistribuirTarefas;

public class ServidorTarefas {

	private ServerSocket servidorSocket;
	private ExecutorService threadPool;
	private BlockingQueue<String> filaComandos;
	private volatile boolean isRun;

	public ServidorTarefas() throws IOException{
		System.out.println("servidor iniciado");
		this.servidorSocket = new ServerSocket(12345);
		//this.newCachedThreadPool = Executors.newCachedThreadPool();//Gerencia as threads conforme demanda
		this.threadPool = Executors.newCachedThreadPool( new FactoryThreads());
		this.filaComandos = new ArrayBlockingQueue<>(2);
		iniciarConsumidores();
	}
	
	private void iniciarConsumidores() {		
		for (int i = 0; i < 2; i++) {
			TarefaConsumir tarefa = new TarefaConsumir(filaComandos);
			this.threadPool.execute(tarefa);
		}
	}

	public void rodar() throws IOException {
		while(this.isRun){
			Socket socket = servidorSocket.accept();
			DistribuirTarefas distribuidorTarefas = new DistribuirTarefas(socket, filaComandos, this, threadPool);
			threadPool.execute(distribuidorTarefas);
		}		
	}

	public void parar() throws IOException {
		servidorSocket.close();
		threadPool.shutdown();
	}	
	
	
	public static void main(String[] args) throws Exception {
		ServidorTarefas servidor = new ServidorTarefas();
		servidor.rodar();
		servidor.parar();
	}
	
}
