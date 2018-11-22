package mqttStuff;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Subscriber {

  public static void main(String[] args) throws MqttException {

    System.out.println("== START SUBSCRIBER ==");

    MqttClient client=new MqttClient("tcp://192.168.56.102:8080", MqttClient.generateClientId());
    client.setCallback( new SubscriberMQTTCallback() );
    client.connect();

    client.subscribe("iot_data");

  }

}
