package se.hkr;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
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
            Arrays.asList("indoor_light", "outdoor_light", "alarm", "fan", "fan_speed", "heating_in", "heating_loft",
                    "auto_mode", "am_temp_value", "leakage", "fire", "stove"));
    private static MqttClient client;
    private static final String prefix = "smart_house/cmd/";
    private static final String prefixGui = "smart_house/gui/";

    SmartHouse smartHouse = SmartHouse.getInstance();
    private House house = smartHouse.getHouse();


    public static void main(String[] args) {
        controlCenter = ControlCenter.getInstance();

        controlCenter.connectMqtt();
        controlCenter.smartHouse.houseMock();
        controlCenter.smartHouse.outputsMockData();
//        controlCenter.startAlarm();
//        controlCenter.togglePausePlayAlarm();
    }

    /**
     * startAlarm() should be used when a user wants to initialize the
     * alarm sound playing class
     */
    public void startAlarm() {
    }

    /**
     * togglePausePlayAlarm() toggles between play/pause functions in
     * sound player class
     */
    public void togglePausePlayAlarm() {
    }

    /**
     * subscribeToTopics() subscribes to all necessary MQTT topics
     */
    public void subscribeToTopics(MqttClient client) throws MqttException {
        for (String s: topics) {
            String topic = prefix + s;
            client.subscribe(topic, new MqttMessageListener());
            System.out.println("subscribed to: " + topic);
        }
    }

    /**
     * connectMqtt() connects to MQTT broker
     */
    public void connectMqtt() {

        new Thread(() -> {
            //Setting up the connection
            String port = "1883";
            String broker = "tcp://smart-mqtthive.duckdns.org:1883";
            MemoryPersistence persistence = new MemoryPersistence();

            try {
                client = new MqttClient(broker, "House_mock", persistence);

                //Creating the options
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);
                connOpts.setKeepAliveInterval(180);
                client.connect(connOpts);
                System.out.println("broker: " + broker + " Connected");
                subscribeToTopics(client);
                smartHouse.setClient(client);
                //client.publish("smart_house/indoor_temperature", new M);


            } catch (MqttException e) {
                e.printStackTrace();
            }

        }).start();
    }

    /**
     * getInstance() singleton method
     *
     * @return ControlCenter instance
     */
    public static ControlCenter getInstance() {
        if (controlCenter == null) {
            controlCenter = new ControlCenter();
        }
        return controlCenter;
    }

    /**
     * getHouse()
     * @return house
     */

    public House getHouse(){
        return house;
    }

    /**
     * clientCheck() checks if MQTT connection exists
     * before publishing to broker
     * this method is used for error handling
     */
    public void clientCheck() {
        System.out.println("Checking MQTT connection ...");
        if (!client.isConnected()){
            System.out.println("Reconnecting ...");
            connectMqtt();
        } else {
            System.out.println("Connection to MQTT is stable.");
        }
    }

}

class MqttMessageListener implements IMqttMessageListener {
    @Override
    public void messageArrived(String var1, MqttMessage var2) throws Exception {
        ControlCenter controlCenter = ControlCenter.getInstance();
        MqttClient client = controlCenter.smartHouse.client;
        switch (var1){
            case "smart_house/cmd/indoor_light":
                client.publish("smart_house/gui/indoor_light", var2);
                controlCenter.getHouse().setIndoorLight(Boolean.parseBoolean(var2.toString()));
                break;
            case "smart_house/cmd/outdoor_light":
                client.publish("smart_house/gui/outdoor_light", var2);
                controlCenter.getHouse().setOutdoorLight(Boolean.parseBoolean(var2.toString()));
                break;
            case "smart_house/cmd/alarm":
                client.publish("smart_house/gui/alarm", var2);
                controlCenter.getHouse().setAlarmArmed(Boolean.parseBoolean(var2.toString()));
                controlCenter.togglePausePlayAlarm();
                if(controlCenter.getHouse().isAlarmArmed() && !controlCenter.getHouse().isDoorClosed()){
                    controlCenter.smartHouse.setIntruderInHouse(true);
                } else {
                    controlCenter.smartHouse.setIntruderInHouse(false);
                }
                break;
            case "smart_house/cmd/fan":
                client.publish("smart_house/gui/fan", var2);
                controlCenter.getHouse().setFanOn(Boolean.parseBoolean(var2.toString()));
                break;
            case "smart_house/cmd/fan_speed":
                client.publish("smart_house/gui/fan_speed", var2);
                controlCenter.getHouse().setFanSpeed(Integer.parseInt(var2.toString()));
                break;
            case "smart_house/cmd/heating_in":
                client.publish("smart_house/gui/heating_in", var2);
                controlCenter.getHouse().setIndoorHeatOn(Boolean.parseBoolean(var2.toString()));
                break;
            case "smart_house/cmd/heating_loft":
                client.publish("smart_house/gui/heating_loft", var2);
                controlCenter.getHouse().setWindHeatOn(Boolean.parseBoolean(var2.toString()));
                break;
            case "smart_house/cmd/auto_mode":
                client.publish("smart_house/gui/auto_mode", var2);
                controlCenter.getHouse().setAutoMode(Boolean.parseBoolean(var2.toString()));
                break;
            case "smart_house/cmd/am_temp_value":
                client.publish("smart_house/gui/am_temp_value", var2);
                controlCenter.getHouse().setAmTemp(Integer.parseInt(var2.toString()));
                break;
            case "smart_house/cmd/door":
                client.publish("smart_house/gui/door", var2);
                controlCenter.getHouse().setDoorClosed(Boolean.parseBoolean(var2.toString()));
                controlCenter.togglePausePlayAlarm();
                if(controlCenter.getHouse().isAlarmArmed() && !controlCenter.getHouse().isDoorClosed()){
                    controlCenter.smartHouse.setIntruderInHouse(true);
                }
                break;
            case "smart_house/cmd/window":
                client.publish("smart_house/gui/window", var2);
                controlCenter.getHouse().setWindowOpen(Boolean.parseBoolean(var2.toString()));
                break;
            case "smart_house/cmd/leakage":
                client.publish("smart_house/gui/leakage", var2);
                controlCenter.getHouse().setWaterLeakageActive(Boolean.parseBoolean(var2.toString()));
                break;
            case "smart_house/cmd/fire":
                client.publish("smart_house/gui/fire", var2);
                controlCenter.getHouse().setFireActive(Boolean.parseBoolean(var2.toString()));
                break;
            case "smart_house/cmd/stove":
                client.publish("smart_house/gui/stove", var2);
                controlCenter.getHouse().setStoveOn(Boolean.parseBoolean(var2.toString()));
                break;
            case "smart_house/cmd/twilight":
                client.publish("smart_house/gui/twilight", var2);
                controlCenter.getHouse().setTwilightSensor(Integer.parseInt(var2.toString()));
                break;
            case "smart_house/cmd/power_cut":
                client.publish("smart_house/gui/power_cut", var2);
                controlCenter.getHouse().setPowerCut(Integer.parseInt(var2.toString()));
                break;
        }
        System.out.println("reply topic  : " + var1);
        System.out.println("reply payload: " + var2.toString());
    }
}
