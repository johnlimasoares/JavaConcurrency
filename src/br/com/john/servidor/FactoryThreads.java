package br.com.john.servidor;

import java.util.concurrent.ThreadFactory;

import br.com.john.exception.TratadorDeExcecao;

public class FactoryThreads implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r,"ThreadFactory");
		thread.setUncaughtExceptionHandler(new TratadorDeExcecao());
		return thread;
	}

}
