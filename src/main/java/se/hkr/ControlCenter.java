package se.hkr;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import se.hkr.models.House;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Nemanja
 * This class is a controll center of the smart house mock model
 */

public class ControlCenter {

    //static variables
    private static ControlCenter controlCenter;
    private static ArrayList<String> topics = new ArrayList<String>( //Example topics
            Arrays.asList("indoor_light", "outdoor_light", "alarm",
                    "fan", "heating_indoor", "heating_wind", "door"));
    private static MqttClient client;
    private static String prefix = "smart_house/";


    private House house;


    public static void main(String[] args) {
        controlCenter = ControlCenter.getInstance();

        controlCenter.connectMqtt();
        controlCenter.startAlarm();
        controlCenter.togglePausePlayAlarm();
    }

    /**
     * startAlarm() should be used when a user wants to initialize the
     * alarm sound playing class
     */
    public void startAlarm(){}

    /**
     * togglePausePlayAlarm() toggles between play/pause functions in
     * sound player class
     */
    public void togglePausePlayAlarm(){}

    /**
     * subscribeToTopics() subscribes to all necessary MQTT topics
     */
    public void subscribeToTopics(){}

    /**
     * connectMqtt() connects to MQTT broker
     */
    public void connectMqtt(){}

    /**
     * getInstance() singleton method
     *
     * @return ControlCenter instance
     */
    public static ControlCenter getInstance() {
        if(controlCenter == null){
            controlCenter = new ControlCenter();
        }
        return controlCenter;
    }

    /**
     * clientCheck() checks if MQTT connection exists
     * before publishing to broker
     * this method is used for error handling
     */
    public void clientCheck(){}

}
class MqttMessageListener implements IMqttMessageListener {
    @Override
    public void messageArrived(String var1, MqttMessage var2) throws Exception {

    }
}
