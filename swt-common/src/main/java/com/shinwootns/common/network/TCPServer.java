package com.shinwootns.common.network;

//작업 중......

/*
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TCPServer implements Runnable {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	protected int _serverPort = 0;
	protected int _threadCount = 2;
	protected int _queueMax = -1;

	protected BlockingQueue<Runnable> _queue = null;
	protected ThreadPoolExecutor _executor = null;
	protected ServerSocketChannel _serverSocketChannel = null;
	
	protected static Map<Integer, TCPClient> _CLIENT_CONNECTIONS = Collections.synchronizedMap(new HashMap<Integer, TCPClient>());

	public boolean Bind(int serverPort) {
		
		this._serverPort = serverPort;

		try {
			int availCPU = Runtime.getRuntime().availableProcessors();
			
			int maxThreadCount = (availCPU * _threadCount) + 2;

			// System.out.println("Using ThreadPoolExecutor, cpu#" + availCPU +
			// ", threadCount#" + _threadCount + " queue#" + _queueMax);

			if (_queueMax == 0) {
				_queue = new SynchronousQueue<Runnable>(true);// enforce
																// fairness
			} else {
				_queue = new LinkedBlockingQueue<Runnable>(_queueMax);
			}

			_executor = new ThreadPoolExecutor(_threadCount, maxThreadCount, 10, TimeUnit.SECONDS, _queue, new ThreadFactory() 
			{
					long count = 0;
					public Thread newThread(Runnable r) {

						System.out.println("Create EsperServer thread " + (count + 1));

						return new Thread(r, "EsperServer-" + count++);
					}
					
				}, new ThreadPoolExecutor.CallerRunsPolicy() {
					public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
						super.rejectedExecution(r, e);
					}
				});
			
			_executor.prestartAllCoreThreads();

			// Open Channel
			_serverSocketChannel = ServerSocketChannel.open();

			// Bind Socket
			_serverSocketChannel.socket().bind(new InetSocketAddress(this._serverPort));

			System.out.println("Accepting connections on port : " + this._serverPort);
			
			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;
	}
	
	public void Close()
	{
		
	}

	public void run() {

		try {

			while (true) {
				// Accept
				SocketChannel socketChannel = _serverSocketChannel.accept();

				System.out.println("Client connected to server.");

				// new ClientConnection
				TCPClient client = new TCPClient(socketChannel, _executor);
				client.start();
			}

		} catch (IOException e) {
			// throw new RuntimeException(e);
			e.printStackTrace();
		}
	}
}
*/