package br.com.john.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

public class ClienteTarefas {

	public static void main(String[] args) throws Exception, IOException {
		Socket socket = new Socket("localhost",12345);
		System.out.println("conexão estabelecida");
		
		
		Thread threadEnvio = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					PrintStream print = new PrintStream(socket.getOutputStream());
					print.println("c2");				
					Scanner envioServer = new Scanner(System.in);		
					while(envioServer.hasNextLine()){
						String nextLine = envioServer.nextLine();
						if(nextLine.trim().equals("")){
							break;
						}				
						print.print(nextLine);
					}						
					print.close();
					envioServer.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		Thread threadResposta = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					System.out.println("recebendo dados do servidor.");
					Scanner respostaServer = new Scanner(socket.getInputStream());
					while(respostaServer.hasNextLine()){
						String nextLine = respostaServer.nextLine();
						System.out.println(nextLine);
					}				
					respostaServer.close();
				} catch (IOException e) { 
					throw new RuntimeException(e);
				}
			}
		});
		
		threadEnvio.start();
		threadResposta.start();
		
		threadEnvio.join();/*thread main espera a threadEnvio terminar para continuar a execução*/
		socket.close();
	}
}
