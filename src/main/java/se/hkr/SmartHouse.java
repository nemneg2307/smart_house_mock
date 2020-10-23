package se.hkr;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import se.hkr.models.House;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

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

    private SmartHouse(){}

    /**
     * houseMock() starts a new thread
     * and runs the readInput() method
     */
    public void houseMock() {
        new Thread(() -> {
            while (true){
                try {
                    readInput();
                } catch (MqttException e) {
                    System.out.println("Connection to MQTT is lost !!!");
                }
            }
        }).start();
    }

    /**
     * readInput() reads the input and handles
     * the switch statement regulated house behaviour
     */
    public synchronized void readInput() throws MqttException {
        Scanner in = new Scanner(System.in);
        int choice = -1;
        try{
            choice = Integer.parseInt(in.nextLine());
        }catch (NumberFormatException e){
            System.out.println("Not a number");
        }
        switch (choice){
            case 1:
                //house.setIndoorLight(!house.isIndoorLight());
                additionalConsumption = (house.isIndoorLight() ? (additionalConsumption-=10) : (additionalConsumption=10));
                String inLght = String.valueOf(!house.isIndoorLight());
                client.publish("smart_house/indoor_light", new MqttMessage(inLght.getBytes()));
                break;
            case 2:
                //house.setOutdoorLight(!house.isOutdoorLight());
                additionalConsumption = (house.isOutdoorLight() ? (additionalConsumption-=10) : (additionalConsumption+=10));
                String outLght = String.valueOf(!house.isOutdoorLight());
                client.publish("smart_house/outdoor_light", new MqttMessage(outLght.getBytes()));
                break;
            case 3:
                house.setWindowOpen(!house.isWindowOpen());
                break;
            case 4:
                String door = String.valueOf(!house.isDoorClosed());
                ControlCenter.getInstance().clientCheck();
                client.publish("smart_house/door", new MqttMessage(door.getBytes()));
                house.setDoorClosed(!house.isDoorClosed());
                if(house.isAlarmArmed() && !house.isDoorClosed()){
                    intruderInHouse = true;
                }
                ControlCenter.getInstance().togglePausePlayAlarm();

                break;
            case 5:
                //house.setAlarmArmed(!house.isAlarmArmed());
                String alarm = String.valueOf(!house.isAlarmArmed());
                ControlCenter.getInstance().clientCheck();
                client.publish("smart_house/alarm", new MqttMessage(alarm.getBytes()));
                house.isAlarmArmed();
                if(house.isDoorClosed() && !house.isAlarmArmed())
                    setIntruderInHouse(false);
                ControlCenter.getInstance().togglePausePlayAlarm();
                break;
            case 0:
                System.out.println("Quitting the app");
                System.exit(0);
                break;
            default:
                System.out.println("Not a valid input!");
                break;
        }
    }

    /**
     * outputsMockData() mocks the data with a Random.class
     * creating an illusion of real temperature and el. consumption data
     */
    public void outputsMockData() {
        Random r = new Random();
        AtomicInteger consumption = new AtomicInteger();
        AtomicInteger indoorTemp = new AtomicInteger();
        AtomicInteger outdoorTemp = new AtomicInteger();
        new Thread(() -> {
            while (true) {
                consumption.set(r.nextInt(6) + 505 + additionalConsumption);
                float idoorFloat = (r.nextFloat() + r.nextInt(1) + 20.01f) * 1000.0f;
                indoorTemp.set((int) idoorFloat);
                float outdoorFloat = (r.nextFloat() + r.nextInt(2) + 17.01f) * 1000.0f;
                outdoorTemp.set((int) outdoorFloat);

                printOutputsAndState(consumption, indoorTemp, outdoorTemp);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printMenu();
                System.out.println("Enter your command now: ");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * printMenu() prints a menu with possible choices for commands
     */
    public void printMenu() {

        System.out.println("\u001B[5m\u001B[4m\u001B[35m" +
                "########---S-M-A-R-T---H-O-U-S-E---########");
        System.out.println("1. Toggle INDOOR LIGHT");
        System.out.println("2. Toggle OUTDOOR LIGHT");
        System.out.println("3. Toggle WINDOW");
        System.out.println("4. Toggle DOOR");
        System.out.println("5. Toggle ALARM");
        System.out.println();
        System.out.println("0. Quit");
        System.out.println("########---------------------------########\u001B[0m");
        if (intruderInHouse) {
            System.out.println("\u001B[91m###--I-N-T-R-U-D-E-R---A-L-E-R-T-!-!-!--###\u001B[0m");
        }
    }

    /**
     * printOutputsAndState() prints constant outputs and state of the house
     */
    public void printOutputsAndState(AtomicInteger consumption, AtomicInteger indoorTemp, AtomicInteger outdoorTemp) {

        String consumpt = String.valueOf(consumption);
        String indoorTmp = String.format("%.02f", (double) (indoorTemp.get() / 1000.0f));
        String outdoorTmp = String.format("%.02f", (double) (outdoorTemp.get() / 1000.0f));

        System.out.println("\u001B[5m\u001B[4m\u001B[32m########-------O-U-T-P-U-T-S-------########\u001B[0m" + " | " +
                "\u001B[5m\u001B[4m\u001B[32m####-S-M-A-R-T---H-O-U-S-E---S-T-A-T-E-####\u001B[0m");
        System.out.println("Indoor temperature:  " + indoorTmp + "                  | " +
                "Door:          " + (house.isDoorClosed() ? "CLOSED" : "OPEN"));
        System.out.println("Outdoor temperature: " + outdoorTmp + "                  | " +
                "Indoor light:  " + (house.isIndoorLight() ? "ON" : "OFF"));
        System.out.println("Power consumption:   " + consumpt + "                    | " +
                "Outdoor light: " + (house.isOutdoorLight() ? "ON" : "OFF"));
        System.out.println("                                            | " +
                "Window:        " + (house.isWindowOpen() ? "OPEN" : "CLOSED"));
        System.out.println("                                            | " +
                "Alarm:         " + (house.isAlarmArmed() ? "ARMED" : "NOT ARMED"));
        System.out.println("\u001B[5m\u001B[4m\u001B[32m########---------------------------########\u001B[0m" + " | " +
                "\u001B[5m\u001B[4m\u001B[32m########---------------------------########\u001B[0m");

        if (intruderInHouse) {
            System.out.println("\u001B[91m###--I-N-T-R-U-D-E-R---A-L-E-R-T-!-!-!--###\u001B[0m");
        }
        if (client != null && client.isConnected()) {
            try {
                client.publish("smart_house/indoor_temperature", new MqttMessage(indoorTmp.getBytes()));
                client.publish("smart_house/outdoor_temperature", new MqttMessage(outdoorTmp.getBytes()));
                client.publish("smart_house/electricity_consumption", new MqttMessage(consumpt.getBytes()));
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * getHouse()
     *
     * @return house
     */
    public House getHouse() {
        return house;
    }

    /**
     * getAdditionalConsumption()
     *
     * @return additionalConsumption
     */
    public int getAdditionalConsumption() {
        return additionalConsumption;
    }

    /**
     * setAdditionalConsumption() sets the additionalConsumption to @param
     *
     * @param additionalConsumption
     */
    public void setAdditionalConsumption(int additionalConsumption) {
        this.additionalConsumption = additionalConsumption;
    }

    /**
     * isIntruderInHouse()
     *
     * @return intruderInHouse
     */
    public boolean isIntruderInHouse() {
        return intruderInHouse;
    }

    /**
     * setIntruderInHouse() sets the intruderInHouse to @param
     *
     * @param intruderInHouse
     */
    public void setIntruderInHouse(boolean intruderInHouse) {
        this.intruderInHouse = intruderInHouse;
    }

    /**
     * setClient() sets the MQTT client to @param
     *
     * @param client
     */
    public void setClient(MqttClient client) {
        this.client = client;
    }

    /**
     * getInstance() singleton method
     *
     * @return SmartHouse instance
     */
    public static SmartHouse getInstance() {
        if (single_instance == null) {
            single_instance = new SmartHouse();
        }
        return single_instance;
    }
}
