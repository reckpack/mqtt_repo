package mqttStuff;

import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Publisher implements Runnable {
	
	String m_name;
	MqttClient m_client;
	boolean m_exit;
	
	
	public Publisher(String _name) throws MqttException
	{
		m_name = _name;
		m_client = new MqttClient("tcp://192.168.56.102:8080", MqttClient.generateClientId());
		m_client.connect();
		m_exit = false;
	}

	public void run() {

		while (!m_exit) {
			try {
				doTheMessageStuff();
			} catch (MqttException e) {
				e.printStackTrace();
			}
		}
		closeDown();
	}
	
	public void stop(){
        m_exit = true;
    }
	
	public void doTheMessageStuff() throws MqttException {
		Scanner sc = new Scanner(System.in);
		String input = sc.next();

		String dest = destinationCheck(input.charAt(0));
		
		MqttMessage message = new MqttMessage();
		message.setPayload(input.getBytes());

		if(!input.isEmpty() && !dest.isEmpty())
		{
			m_client.publish(dest, message);
			System.out.printf("%s to %s:" + input,m_name,dest.toString());	
		}
		
		sc.close();
	}
	
	public void closeDown()
	{
		try {
			m_client.disconnect();
			m_client.close();
		}
		catch (MqttException e) {
			e.printStackTrace();
		}
		
	}
	
	

	public static String destinationCheck(char _s) {

		switch (_s) {
			case '1': {
				return proj.MESSAGE_QUEUE.CASSUAL.toString().toLowerCase();
			}
			case '2': {
				return proj.MESSAGE_QUEUE.WORK.toString().toLowerCase();
			}
			case '3': {
				return proj.MESSAGE_QUEUE.EMERGENCY.toString().toLowerCase();
			}
		}

		return "";
	}
}








