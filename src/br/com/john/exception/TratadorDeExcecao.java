package br.com.john.exception;

import java.lang.Thread.UncaughtExceptionHandler;

public class TratadorDeExcecao implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("Thread: " + t.getName() + "  Falha: " + e.getMessage());
	}

}
