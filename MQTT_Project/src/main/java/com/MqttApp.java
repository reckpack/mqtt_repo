package main.java.com;

import org.eclipse.paho.client.mqttv3.MqttException;

import mqtt.classes.Publisher;
import mqtt.classes.Subscriber;

/**
 * Basic launcher for Publisher and Subscriber
 */
public class MqttApp {

  public static void main(String[] args) throws MqttException {

    if (args.length < 1) {
      throw new IllegalArgumentException("Must have either 'publisher' or 'subscriber' as argument");
    }
    
    switch (args[0]) {
      case "publisher":
        Publisher.main(args);
        break;
      case "subscriber":
        Subscriber.main(args);
        break;
      default:
        throw new IllegalArgumentException("Don't know how to do " + args[0]);
    }
  }
}

