package se.hkr;

/**
 * @author Nemanja
 * This class is a controll center of the smart house mock model
 */

public class ControlCenter {

    //singleton static variable
    private static ControlCenter controlCenter;

    public static void main(String[] args) {

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
