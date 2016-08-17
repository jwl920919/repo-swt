package com.shinwootns.common.network;

//작업 중......

/*
import java.io.ObjectInputStream;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class TCPClient extends Thread {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private SocketChannel _socketChannel;
	private ThreadPoolExecutor _executor;

	private ObjectInputStream _ois;

	private int _myID;

	public TCPClient(SocketChannel socketChannel, ThreadPoolExecutor executor) {
		this._socketChannel = socketChannel;
		this._executor = executor;
		
		_myID = (int)this.getId();

		TCPServer._CLIENT_CONNECTIONS.put(_myID, this);
	}

	public void run() {

		try {

			String sData = "";

			if (_socketChannel != null)
				_ois = new ObjectInputStream(_socketChannel.socket().getInputStream());

			do {
				Object obj = _ois.readObject();

				if (obj instanceof String) {
					sData = (String) obj;

					// System.out.println(sData);

					try {
						
						
//						// JSON --> Event
//						JSONObject json = (JSONObject) new JSONParser().parse(sData);
//
//						EventData event = new EventData();
//						event.setHost((String) json.get("host"));
//						event.setType((String) json.get("type"));
//						event.setSeverity(Integer.parseInt(json.get("severity").toString()));
//						event.setFacility(Integer.parseInt(json.get("facility").toString()));
//						event.setRecvTime((long) json.get("recvTime"));
//						event.setData((String) json.get("data"));
//
//						// Send to Esper
//						epService.getEPRuntime().sendEvent(event);
						
						
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

			} while (true);

		} catch (Throwable t) {

			t.printStackTrace();

			System.err.println("Error receiving data from client.");

		} finally {
			TCPServer._CLIENT_CONNECTIONS.remove(_myID);
		}
	}
}

*/