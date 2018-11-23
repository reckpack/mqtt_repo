package mqttStuff;

import java.util.ArrayList;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import proj.MESSAGE_QUEUE;

public class Subscriber implements Runnable {
	
	String[] m_topics;
	MqttClient m_client;
	boolean m_exit;
	
	public Subscriber(String[] _topics) throws MqttException
	{
		m_topics = _topics;
		m_client = new MqttClient("tcp://192.168.56.102:8883", MqttClient.generateClientId());
		m_client.setCallback( new SubscriberMQTTCallback() );
		m_client.connect();
	    m_exit = false;
	}
	 
  public void run() 
  {

	  while (!m_exit) {
			try {
				doTheListeningStuff();
			} catch (MqttException e) {
				e.printStackTrace();
			}
		}
	closeDown();
}
  
  public void stop(){
      m_exit = true;
  }

  private void doTheListeningStuff() throws MqttException
  {
	  System.out.println("== START SUBSCRIBER ==");

	    //MqttClient client=new MqttClient("tcp://192.168.56.102:8080", MqttClient.generateClientId());
	  	ArrayList<String> srcList = new ArrayList<>();
	    
	    for (int i = 0; i < m_topics.length; ++i)
	    {
	    	char sr = m_topics[i].charAt(0);
	    	String messageType = sourceCheck(sr);
			srcList.add(messageType);
	    	
	    }
	    String[] src = {};
	    srcList.toArray(src);
	    m_client.subscribe(src);
  }
  
  public void closeDown() 
{
	try {
		m_client.disconnect();
		m_client.close();
	} catch (MqttException e) {
		e.printStackTrace();
	}
}
  
  public static String sourceCheck(char _s) {

		switch (_s) {
			case '1': {
				return MESSAGE_QUEUE.CASSUAL.toString().toLowerCase();
			}
			case '2': {
				return MESSAGE_QUEUE.WORK.toString().toLowerCase();
			}
			case '3': {
				return MESSAGE_QUEUE.EMERGENCY.toString().toLowerCase();
			}
		}

		return null;
	}

}
