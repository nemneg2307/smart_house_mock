package se.hkr.models;

/**
 * @author Nemanja
 * This class is a model class for the mock representation of the
 * real physical house model
 */
public class House {
    //Constant outputs
    private int electricityConsumption;
    private float indoorTemp;
    private float outdoorTemp;
    //The house
    private boolean indoorLight = false;
    private boolean outdoorLight = false;
    private boolean doorClosed = true;
    private boolean windowOpen = false;
    private boolean alarmArmed = false;
    private boolean fireActive = false;
    private boolean waterLeakageActive = false;
    private boolean stoveOn = false;
    private int powerCut = 0;
    private boolean indoorHeatOn = false;
    private boolean windHeatOn = false;
    public boolean fanOn = false;
    public int fanSpeed = 0;


    public House() {
    }

    public boolean isIndoorLight() {
        return indoorLight;
    }

    public void setIndoorLight(boolean indoorLight) {
        this.indoorLight = indoorLight;
    }

    public boolean isOutdoorLight() {
        return outdoorLight;
    }

    public void setOutdoorLight(boolean outdoorLight) {
        this.outdoorLight = outdoorLight;
    }

    public boolean isDoorClosed() {
        return doorClosed;
    }

    public void setDoorClosed(boolean doorClosed) {
        this.doorClosed = doorClosed;
    }

    public boolean isWindowOpen() {
        return windowOpen;
    }

    public void setWindowOpen(boolean windowOpen) {
        this.windowOpen = windowOpen;
    }

    public int getElectricityConsumption() {
        return electricityConsumption;
    }

    public void setElectricityConsumption(int electricityConsumption) {
        this.electricityConsumption = electricityConsumption;
    }

    public float getIndoorTemp() {
        return indoorTemp;
    }

    public void setIndoorTemp(float indoorTemp) {
        this.indoorTemp = indoorTemp;
    }

    public float getOutdoorTemp() {
        return outdoorTemp;
    }

    public void setOutdoorTemp(float outdoorTemp) {
        this.outdoorTemp = outdoorTemp;
    }

    public boolean isAlarmArmed() {
        return alarmArmed;
    }

    public void setAlarmArmed(boolean alarmArmed) {
        this.alarmArmed = alarmArmed;
    }

    public boolean isFireActive() {
        return fireActive;
    }

    public void setFireActive(boolean fireActive) {
        this.fireActive = fireActive;
    }

    public boolean isWaterLeakageActive() {
        return waterLeakageActive;
    }

    public void setWaterLeakageActive(boolean waterLeakageActive) {
        this.waterLeakageActive = waterLeakageActive;
    }

    public boolean isStoveOn() {
        return stoveOn;
    }

    public void setStoveOn(boolean stoveOn) {
        this.stoveOn = stoveOn;
    }

    public int getPowerCut() {
        return powerCut;
    }

    public void setPowerCut(int powerCut) {
        this.powerCut = powerCut;
    }

    public boolean isIndoorHeatOn() {
        return indoorHeatOn;
    }

    public void setIndoorHeatOn(boolean indoorHeatOn) {
        this.indoorHeatOn = indoorHeatOn;
    }

    public boolean isWindHeatOn() {
        return windHeatOn;
    }

    public void setWindHeatOn(boolean windHeatOn) {
        this.windHeatOn = windHeatOn;
    }

    public boolean isFanOn() {
        return fanOn;
    }

    public void setFanOn(boolean fanOn) {
        this.fanOn = fanOn;
    }

    public int getFanSpeed() {
        return fanSpeed;
    }

    public void setFanSpeed(int fanSpeed) {
        this.fanSpeed = fanSpeed;
    }
}
