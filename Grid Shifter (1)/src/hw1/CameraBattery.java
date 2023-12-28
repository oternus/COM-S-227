package hw1;


/* FORMULAS: 
 * increase in charge for camera charging = minutes x CHARGE_RATE
 * increase in charge for external charging = minutes x setting x CHARGE_RATE
 * decrease in charge for camera power drain = minutes x camera power consumption
 */

public class CameraBattery {
	
	//CONSTRAINTS:
	
	/**
	 * Number of external charger settings. 
	 * Settings are numbered between 0 inclusive and 4 exclusive.
	 * @author
	 * @param
	 */
	public static final int NUM_CHARGER_SETTINGS = 4;
	
	/**
	 * A constant used in calculating the charge rate of the external charger.
	 * @author
	 */
	public static final double CHARGE_RATE = 2.0; 
	
	/**
	 * Default power consumption of the camera at the start of simulation.
	 * @author
	 */
	public static final double DEFAULT_CAMERA_POWER_CONSUMPTION = 1.0;
	
	/**
	 * sets the max capacity of the battery
	 * @author
	 */
	private double BatteryCapacity;
	
	/**
	 * current charge of the battery
	 * @author
	 */
	private double batteryCharge;
	
	/**
	 * sets/updates the total amount drained from battery
	 * @author
	 */
	private double drainedTotal;
	
	/**
	 * sets/updates the total amount drained from camera
	 */
	private double cameraCharge;
	
	/**
	 * sets/updates the camera consumption
	 * @author
	 */
	private double cameraPowerConsumption;
	
	/**
	 * Keeps track of the setting of the charger, between 0 and NUM_CHARGER_SETTING.
	 * @param
	 * @author
	 */
	private int chargerSetting;
	
	/**
	 * Keeps track of button press, max of 4.
	 * @author
	 * @param
	 */
	private int buttonPress;
	
	/**
	 * Either 0 or 1, 1 indicates battery is connected, 0 false.
	 * @author
	 */
	private int connected;
	
	/**
	 * Either 0 or 1, 1 indicates external charger is used, 0 false.
	 * @author
	 */
	private double externalCharge;
	
	//CONSTRUCTOR
	/**
	   * Constructs new battery simulation
	   * external charger starts at 0
	   * @author
	   */
	public CameraBattery(double batteryStartingCharge, double batteryCapacity) {
		
		batteryCharge = batteryStartingCharge;
		
		BatteryCapacity = batteryCapacity;
		
		batteryCharge = Math.min(batteryCharge, BatteryCapacity);
		
		connected = 0;
		
	}
	
	/**
	 * Indicates the user has pressed the setting 
	 * button one time on the external charger.
	 * @author
	 */
	public void buttonPress() {
		
		buttonPress = (buttonPress + 1) % NUM_CHARGER_SETTINGS;
		chargerSetting = buttonPress;
		
	}

	/**
	   * Charges the camera battery for x minutes
	   * @return amount charged
	   * @author
	   */
	public double cameraCharge(double minutes) {
		
		double thisCharge = minutes * CHARGE_RATE;
		cameraCharge = Math.min(thisCharge, BatteryCapacity - batteryCharge);
		cameraCharge = cameraCharge * connected;
		batteryCharge = batteryCharge + cameraCharge;
		return batteryCharge * connected;
		
	}
	
	/**
	   * Drains the camera battery for x minutes
	   * updates battery charge
	   * @return actual drain amount
	   * @author
	   */
	public double drain(double minutes) {
		
		double thisDrain = minutes * DEFAULT_CAMERA_POWER_CONSUMPTION;
		drainedTotal = Math.min(thisDrain, BatteryCapacity);
		batteryCharge -= minutes * DEFAULT_CAMERA_POWER_CONSUMPTION;
		batteryCharge = Math.max(batteryCharge, 0);
	
		return drainedTotal;
	}

	/**
	   * Charges the external charger
	   * updates batteryCharge and passes original charge
	   * @return amount charged
	   * @author
	   */
	public double externalCharge(double minutes) {
		
		double charged = chargerSetting * minutes * CHARGE_RATE;
		double originalCharge = batteryCharge;
		batteryCharge = Math.min(BatteryCapacity, batteryCharge + charged);
		batteryCharge += batteryCharge - originalCharge;
		return batteryCharge - originalCharge;
	}
	
	/**
	   * Reset the battery monitoring system to 0; (drain = 0)
	   * @author
	   */
	public void resetBatteryMonitor() {
		drainedTotal = 0;
	}

	/**
	   * Gets battery capacity
	   * updates with each call
	   * @author
	   * @return batteryCapacity
	   */
	public double getBatteryCapacity() {
		
		return BatteryCapacity;
	}
	
	/**
	   * Gets battery current charge
	   * updates with each call
	   * @author
	   * @return batteryCharge
	   */
	public double getBatteryCharge() {
		
		return batteryCharge;
	}
	
	/**
	   * Gets camera current camera charge
	   * updates with each call
	   * @author
	   * @return cameraCharge
	   */
	public double getCameraCharge() {
		
		return cameraCharge;
	}
	
	/**
	   * Gets camera power consumption
	   * updated with each call
	   * @author
	   * @return updated power consumption
	   */
	public double getCameraPowerConsumption() {
		
		return cameraPowerConsumption + 1.0;
	}

	/**
	   * Gets external charger setting
	   * updates with each call
	   * @author
	   * @return the charger setting
	   */
	public int getChargerSetting() {
		
		return chargerSetting;
	}
	
	/**
	   * Gets total drained since last time it was changed/reset
	   * updates with each call
	   * @author
	   * @return total drained from battery
	   */
	public double getTotalDrain() {
		
		return drainedTotal;
	}
	
	/**
	   * Moves battery to external charger
	   * updates variables from move
	   * @author
	   */
	public void moveBatteryExternal() {
		cameraCharge = 0;
	}
	
	/**
	   * Moves battery to camera
	   * updates variables from move
	   * @author
	   */
	public void moveBatteryCamera() {
		cameraCharge = batteryCharge;
		externalCharge = 0;
		
	}
	
	/**
	   * Moves battery from camera/external charger
	   * updates variables as needed
	   * @author
	   */
	public void removeBattery() {
		cameraCharge = 0;
		externalCharge = 0;
	}

	/**
	   * sets power consumption of camera
	   * updates variables as needed
	   * @author
	   */
	public void setCameraPowerConsumption(double cameraPowerConsumption) {
		cameraPowerConsumption = (cameraPowerConsumption * DEFAULT_CAMERA_POWER_CONSUMPTION) - drainedTotal;
	}
}
