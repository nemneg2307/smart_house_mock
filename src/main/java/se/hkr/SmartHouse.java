package se.hkr;

import org.eclipse.paho.client.mqttv3.MqttClient;
import se.hkr.models.House;

/**
 * Class SmartHouse is the main simulator class
 * it holds the main methods for house behaviour simulation
 *
 * @author Nemanja
 */
public class SmartHouse {
    private static SmartHouse single_instance;

    private int additionalConsumption; // adds an abstract value to the el. consumption simulator to simulate
                                       // elevated consumption when a new device has been turned on
    private boolean intruderInHouse = false;
    private House house = new House();

    MqttClient client = null;

    /**
     * houseMock() starts a new thread
     * and runs the readInput() method
     */
    public void houseMock(){}

    /**
     * readInput() reads the input and handles
     * the switch statement regulated house behaviour
     */
    public void readInput(){}

    /**
     * outputsMockData() mocks the data with a Random.class
     * creating an illusion of real temperature and el. consumption data
     */
    public void outputsMockData(){}

    /**
     * printMenu() prints a menu with possible choices for commands
     */
    public void printMenu(){}

    /**
     * printOutputsAndState() prints constant outputs and state of the house
     */
    public void printOutputsAndState(){}

    /**
     * getHouse()
     * @return house
     */
    public House getHouse() {
        return house;
    }

    /**
     * getAdditionalConsumption()
     * @return additionalConsumption
     */
    public int getAdditionalConsumption() {
        return additionalConsumption;
    }

    /**
     * setAdditionalConsumption() sets the additionalConsumption to @param
     * @param additionalConsumption
     */
    public void setAdditionalConsumption(int additionalConsumption) {
        this.additionalConsumption = additionalConsumption;
    }

    /**
     * isIntruderInHouse()
     * @return intruderInHouse
     */
    public boolean isIntruderInHouse() {
        return intruderInHouse;
    }

    /**
     * setIntruderInHouse() sets the intruderInHouse to @param
     * @param intruderInHouse
     */
    public void setIntruderInHouse(boolean intruderInHouse) {
        this.intruderInHouse = intruderInHouse;
    }

    /**
     * setClient() sets the MQTT client to @param
     * @param client
     */
    public void setClient(MqttClient client) {
        this.client = client;
    }

    /**
     * getInstance() singleton method
     * @return SmartHouse instance
     */
    public static SmartHouse getInstance(){
        if(single_instance == null){
            single_instance = new SmartHouse();
        }
        return single_instance;
    }
}
