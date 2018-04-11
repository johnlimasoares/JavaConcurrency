package br.com.john.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.john.client.DistribuirTarefas;

public class ServidorTarefas {

	private ServerSocket servidorSocket;
	private ExecutorService newCachedThreadPool;
	private volatile boolean isRun;

	public ServidorTarefas() throws IOException{
		System.out.println("servidor iniciado");
		this.servidorSocket = new ServerSocket(12345);
		this.newCachedThreadPool = Executors.newCachedThreadPool();//Gerencia as threads conforme demanda
	}
	
	public void rodar() throws IOException {
		while(this.isRun){
			Socket socket = servidorSocket.accept();
			DistribuirTarefas distribuidorTarefas = new DistribuirTarefas(socket, this);
			newCachedThreadPool.execute(distribuidorTarefas);
		}		
	}

	public void parar() throws IOException {
		servidorSocket.close();
		newCachedThreadPool.shutdown();
	}	
	
	
	public static void main(String[] args) throws Exception {
		ServidorTarefas servidor = new ServidorTarefas();
		servidor.rodar();
		servidor.parar();
	}
	
}
