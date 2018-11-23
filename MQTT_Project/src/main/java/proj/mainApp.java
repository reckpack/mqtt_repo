package proj;

import mqttStuff.Publisher;
import mqttStuff.Subscriber;

import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttException;

// tcp://192.168.56.102:8080 - ip for mosquitto server vm
// to launch server in server vm: mosquitto -p 8080			this specifies the port

public class mainApp {

	public static void main(String[] args) throws MqttException {
		Scanner sc = new Scanner(System.in);
		String name = "";
		String[] arrOfStr = {};
		Publisher pub;
		Subscriber sub;

		System.out.print("Enter Name: ");
		name = sc.nextLine();

		System.out.println("Message Subscription Options:\n" + "1) Cassual.\n" + "2) Work.\n" + "3) Emergencies\n"
				+ "Please enter the message queues you wish to subscribe to (separated by comma)");

		boolean input = true;
		while (input) {
			String str = sc.nextLine();
			if (str.length() > 0) {
				if (str.contains("[a-zA-Z]+") == false) {
					arrOfStr = sc.next().split(",");
					if (arrOfStr.length > 0) {
						sc.close();
						break;
					}
				} else {
					System.out.println("Invalid Input. Try Again:");
				}
			} else {
				System.out.println("Input Empty. Try Again:");
			}
		}
		
		if(arrOfStr.length > 0)
		{
			pub = new Publisher(name);
			Thread pubThread = new Thread(pub, "publisher");
			
			sub = new Subscriber(arrOfStr);
			Thread subThread = new Thread(sub, "subscriber");
			pubThread.start();
			subThread.start();
			
			if(!pubThread.isAlive() || !subThread.isAlive())
			{
				pub.stop();
				sub.stop();
			}
		}

		if (args.length < 1) {
			throw new IllegalArgumentException("Must have either 'publisher' or 'subscriber' as argument");
		}

	}

}
