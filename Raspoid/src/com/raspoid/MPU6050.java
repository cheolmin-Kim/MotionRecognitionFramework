/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raspoid;


import com.pi4j.io.i2c.I2CFactory;
import com.raspoid.I2CComponent;
import com.raspoid.Tools;
import com.raspoid.MPU6050Example;
import com.raspoid.exceptions.RaspoidException;

//import com.pi4j.io.i2c.I2CBus;
//import com.pi4j.io.i2c.I2CDevice;
//import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

public class MPU6050 extends I2CComponent{

	/**
	 * Default address of the MPU6050 device.
	 */
	public static final int DEFAULT_MPU6050_ADDRESS = 0x68;

	/**
	 * Default value for the digital low pass filter (DLPF) setting for both gyroscope and accelerometer.
	 */
	public static final int DEFAULT_DLPF_CFG = 0x06;

	/**
	 * Default value for the sample rate divider.
	 */
	public static final int DEFAULT_SMPLRT_DIV = 0x00;
	public static final double RADIAN_TO_DEGREE = 180. / Math.PI;
	private static final double ACCEL_Z_ANGLE = 0;

	/* -----------------------------------------------------------------------
     *                          REGISTERS ADDRESSES 
     * -----------------------------------------------------------------------*/
	/**
	 * <b>[datasheet 2 - p.11]</b> Sample Rate Divider
	 * <p>
	 * This register specifies the divider from the gyroscope output rate used to generate the Sample Rate for the MPU-60X0.</p>
	 */
	public static final int MPU6050_REG_ADDR_SMPRT_DIV = 0x19; // 25

	/**
	 * <b>[datasheet 2 - p.13]</b> Configuration
	 * <p>
	 * This register configures the external Frame Synchronization (FSYNC) pin sampling and the Digital Low Pass Filter (DLPF) setting for both the gyroscopes and accelerometers.</p>
	 */
	public static final int MPU6050_REG_ADDR_CONFIG = 0x1A; // 26

	/**
	 * <b>[datasheet 2 - p.14]</b> Gyroscope Configuration
	 * <p>
	 * This register is used to trigger gyroscope self-test and configure the gyroscopes’ full scale range</p>
	 */
	public static final int MPU6050_REG_ADDR_GYRO_CONFIG = 0x1B; // 27

	/**
	 * <b>[datasheet 2 - p.15]</b> Accelerometer Configuration
	 * <p>
	 * This register is used to trigger accelerometer self test and configure the accelerometer full scale range. This register also configures the Digital High Pass Filter (DHPF).</p>
	 */
	public static final int MPU6050_REG_ADDR_ACCEL_CONFIG = 0x1C; // 28

	/**
	 * <b>[datasheet 2 - p.27]</b> Interrupt Enable
	 * <p>
	 * This register enables interrupt generation by interrupt sources.</p>
	 */
	public static final int MPU6050_REG_ADDR_INT_ENABLE = 0x1A; // 56

	/**
	 * <b>[datasheet 2 - p.40]</b> Power Management 1
	 * <p>
	 * This register allows the user to configure the power mode and clock source. It also provides a bit for resetting the entire device, and a bit for disabling the temperature sensor.</p>
	 */
	public static final int MPU6050_REG_ADDR_PWR_MGMT_1 = 0x6B; // 107

	/**
	 * <b>[datasheet 2 - p.42]</b> Power Management 2
	 * <p>
	 * This register allows the user to configure the frequency of wake-ups in Accelerometer Only Low Power Mode. This register also allows the user to put individual axes of the accelerometer and gyroscope into standby mode.</p>
	 */
	public static final int MPU6050_REG_ADDR_PWR_MGMT_2 = 0x6C; // 108

	/**
	 * <b>[datasheet 2 - p.29]</b> Accelerometer Measurements
	 * <p>
	 * These registers store the most recent accelerometer measurements.</p>
	 *
	 * @see #MPU6050_REG_ADDR_ACCEL_XOUT_L
	 * @see #MPU6050_REG_ADDR_ACCEL_YOUT_H
	 * @see #MPU6050_REG_ADDR_ACCEL_YOUT_L
	 * @see #MPU6050_REG_ADDR_ACCEL_ZOUT_H
	 * @see #MPU6050_REG_ADDR_ACCEL_ZOUT_L
	 */
	public static final int MPU6050_REG_ADDR_ACCEL_XOUT_H = 0x3B; // 59

	/**
	 * <b>[datasheet 2 - p.29]</b> Accelerometer Measurements
	 * <p>
	 * These registers store the most recent accelerometer measurements.</p>
	 *
	 * @see #MPU6050_REG_ADDR_ACCEL_XOUT_H
	 * @see #MPU6050_REG_ADDR_ACCEL_YOUT_H
	 * @see #MPU6050_REG_ADDR_ACCEL_YOUT_L
	 * @see #MPU6050_REG_ADDR_ACCEL_ZOUT_H
	 * @see #MPU6050_REG_ADDR_ACCEL_ZOUT_L
	 */
	public static final int MPU6050_REG_ADDR_ACCEL_XOUT_L = 0x3C; // 60

	/**
	 * <b>[datasheet 2 - p.29]</b> Accelerometer Measurements
	 * <p>
	 * These registers store the most recent accelerometer measurements.</p>
	 *
	 * @see #MPU6050_REG_ADDR_ACCEL_XOUT_H
	 * @see #MPU6050_REG_ADDR_ACCEL_XOUT_L
	 * @see #MPU6050_REG_ADDR_ACCEL_YOUT_L
	 * @see #MPU6050_REG_ADDR_ACCEL_ZOUT_H
	 * @see #MPU6050_REG_ADDR_ACCEL_ZOUT_L
	 */
	public static final int MPU6050_REG_ADDR_ACCEL_YOUT_H = 0x3D; // 61

	/**
	 * <b>[datasheet 2 - p.29]</b> Accelerometer Measurements
	 * <p>
	 * These registers store the most recent accelerometer measurements.</p>
	 *
	 * @see #MPU6050_REG_ADDR_ACCEL_XOUT_H
	 * @see #MPU6050_REG_ADDR_ACCEL_XOUT_L
	 * @see #MPU6050_REG_ADDR_ACCEL_YOUT_H
	 * @see #MPU6050_REG_ADDR_ACCEL_ZOUT_H
	 * @see #MPU6050_REG_ADDR_ACCEL_ZOUT_L
	 */
	public static final int MPU6050_REG_ADDR_ACCEL_YOUT_L = 0x3E; // 62

	/**
	 * <b>[datasheet 2 - p.29]</b> Accelerometer Measurements
	 * <p>
	 * These registers store the most recent accelerometer measurements.</p>
	 *
	 * @see #MPU6050_REG_ADDR_ACCEL_XOUT_H
	 * @see #MPU6050_REG_ADDR_ACCEL_XOUT_L
	 * @see #MPU6050_REG_ADDR_ACCEL_YOUT_H
	 * @see #MPU6050_REG_ADDR_ACCEL_YOUT_L
	 * @see #MPU6050_REG_ADDR_ACCEL_ZOUT_L
	 */
	public static final int MPU6050_REG_ADDR_ACCEL_ZOUT_H = 0x3F; // 63

	/**
	 * <b>[datasheet 2 - p.29]</b> Accelerometer Measurements
	 * <p>
	 * These registers store the most recent accelerometer measurements.</p>
	 *
	 * @see #MPU6050_REG_ADDR_ACCEL_XOUT_H
	 * @see #MPU6050_REG_ADDR_ACCEL_XOUT_L
	 * @see #MPU6050_REG_ADDR_ACCEL_YOUT_H
	 * @see #MPU6050_REG_ADDR_ACCEL_YOUT_L
	 * @see #MPU6050_REG_ADDR_ACCEL_ZOUT_H
	 */
	public static final int MPU6050_REG_ADDR_ACCEL_ZOUT_L = 0x40; // 64

	/**
	 * <b>[datasheet 2 - p.30]</b> Temperature Measurement
	 * <p>
	 * These registers store the most recent temperature sensor measurement.</p>
	 *
	 * @see #MPU6050_REG_ADDR_TEMP_OUT_L
	 */
	public static final int MPU6050_REG_ADDR_TEMP_OUT_H = 0x41; // 65

	/**
	 * <b>[datasheet 2 - p.30]</b> Temperature Measurement
	 * <p>
	 * These registers store the most recent temperature sensor measurement.</p>
	 *
	 * @see #MPU6050_REG_ADDR_TEMP_OUT_H
	 */
	public static final int MPU6050_REG_ADDR_TEMP_OUT_L = 0x42; // 66

	/**
	 * <b>[datasheet 2 - p.31]</b> Gyroscope Measurements
	 * <p>
	 * These registers store the most recent gyroscope measurements.</p>
	 *
	 * @see #MPU6050_REG_ADDR_GYRO_XOUT_L
	 * @see #MPU6050_REG_ADDR_GYRO_YOUT_H
	 * @see #MPU6050_REG_ADDR_GYRO_YOUT_L
	 * @see #MPU6050_REG_ADDR_GYRO_ZOUT_H
	 * @see #MPU6050_REG_ADDR_GYRO_ZOUT_L
	 */
	public static final int MPU6050_REG_ADDR_GYRO_XOUT_H = 0x43; // 67

	/**
	 * <b>[datasheet 2 - p.31]</b> Gyroscope Measurements
	 * <p>
	 * These registers store the most recent gyroscope measurements.</p>
	 *
	 * @see #MPU6050_REG_ADDR_GYRO_XOUT_H
	 * @see #MPU6050_REG_ADDR_GYRO_YOUT_H
	 * @see #MPU6050_REG_ADDR_GYRO_YOUT_L
	 * @see #MPU6050_REG_ADDR_GYRO_ZOUT_H
	 * @see #MPU6050_REG_ADDR_GYRO_ZOUT_L
	 */
	public static final int MPU6050_REG_ADDR_GYRO_XOUT_L = 0x44; // 68

	/**
	 * <b>[datasheet 2 - p.31]</b> Gyroscope Measurements
	 * <p>
	 * These registers store the most recent gyroscope measurements.</p>
	 *
	 * @see #MPU6050_REG_ADDR_GYRO_XOUT_H
	 * @see #MPU6050_REG_ADDR_GYRO_XOUT_L
	 * @see #MPU6050_REG_ADDR_GYRO_YOUT_L
	 * @see #MPU6050_REG_ADDR_GYRO_ZOUT_H
	 * @see #MPU6050_REG_ADDR_GYRO_ZOUT_L
	 */
	public static final int MPU6050_REG_ADDR_GYRO_YOUT_H = 0x45; // 69

	/**
	 * <b>[datasheet 2 - p.31]</b> Gyroscope Measurements
	 * <p>
	 * These registers store the most recent gyroscope measurements.</p>
	 *
	 * @see #MPU6050_REG_ADDR_GYRO_XOUT_H
	 * @see #MPU6050_REG_ADDR_GYRO_XOUT_L
	 * @see #MPU6050_REG_ADDR_GYRO_YOUT_H
	 * @see #MPU6050_REG_ADDR_GYRO_ZOUT_H
	 * @see #MPU6050_REG_ADDR_GYRO_ZOUT_L
	 */
	public static final int MPU6050_REG_ADDR_GYRO_YOUT_L = 0x46; // 70

	/**
	 * <b>[datasheet 2 - p.31]</b> Gyroscope Measurements
	 * <p>
	 * These registers store the most recent gyroscope measurements.</p>
	 *
	 * @see #MPU6050_REG_ADDR_GYRO_XOUT_H
	 * @see #MPU6050_REG_ADDR_GYRO_XOUT_L
	 * @see #MPU6050_REG_ADDR_GYRO_YOUT_H
	 * @see #MPU6050_REG_ADDR_GYRO_YOUT_L
	 * @see #MPU6050_REG_ADDR_GYRO_ZOUT_L
	 */
	public static final int MPU6050_REG_ADDR_GYRO_ZOUT_H = 0x47; // 71

	/**
	 * <b>[datasheet 2 - p.31]</b> Gyroscope Measurements
	 * <p>
	 * These registers store the most recent gyroscope measurements.</p>
	 *
	 * @see #MPU6050_REG_ADDR_GYRO_XOUT_H
	 * @see #MPU6050_REG_ADDR_GYRO_XOUT_L
	 * @see #MPU6050_REG_ADDR_GYRO_YOUT_H
	 * @see #MPU6050_REG_ADDR_GYRO_YOUT_L
	 * @see #MPU6050_REG_ADDR_GYRO_ZOUT_H
	 */
	public static final int MPU6050_REG_ADDR_GYRO_ZOUT_L = 0x48; // 72

	/* -----------------------------------------------------------------------
     *                             VARIABLES
     * -----------------------------------------------------------------------*/
    
    /**
     * Value used for the DLPF config.
     */
    private int dlpfCfg;
	private int smplrtDiv;
	private double accelLSBSensitivity;
	private double gyroLSBSensitivity;

	private Thread updatingThread = null;
	private boolean updatingThreadStopped = true;
	private long lastUpdateTime = 0;

	// ACCELEROMETER
//	 Last acceleration value, in g, retrieved from the accelerometer, for the x axis.
	private double accelAccelerationX = 0.;
	private double accelAccelerationY = 0.;
	private double accelAccelerationZ = 0.;

//	 Last angle value, in °, retrieved from the accelerometer
	private double accelAngleX = 0.;
	private double accelAngleY = 0.;
	private double accelAngleZ = 0.;

	// GYROSCOPE
//	 Last angular speed value, in °/sec, retrieved from the gyroscope
	private double gyroAngularSpeedX = 0.;
	private double gyroAngularSpeedY = 0.;
	private double gyroAngularSpeedZ = 0.;
//	Last angle value, in °, calculated from the gyroscope,
	private double gyroAngleX = 0.;
	private double gyroAngleY = 0.;
	private double gyroAngleZ = 0.;

//	 Calculated offset for the angular speed from the gyroscope, for the x axis.
	private double gyroAngularSpeedOffsetX = 0.;
	private double gyroAngularSpeedOffsetY = 0.;
	private double gyroAngularSpeedOffsetZ = 0.;

	// FILTERED
//	Last angle value, in °, calculated from the accelerometer and the gyroscope,
	private double filteredAngleX = 0.;
	private double filteredAngleY = 0.;
	private double filteredAngleZ = 0.;

	/* -----------------------------------------------------------------------
     *                             CONSTRUCTORS
     * -----------------------------------------------------------------------*/
	public MPU6050() throws I2CFactory.UnsupportedBusNumberException {
		this(DEFAULT_MPU6050_ADDRESS, DEFAULT_DLPF_CFG, DEFAULT_SMPLRT_DIV);
	}

	public MPU6050(int i2cAddress, int dlpfCfg, int smplrtDiv) throws I2CFactory.UnsupportedBusNumberException {
		super(i2cAddress);
		this.dlpfCfg = dlpfCfg;
		this.smplrtDiv = smplrtDiv;

		// 1. waking up the MPU6050 (0x00 = 0000 0000) as it starts in sleep mode.
		updateRegisterValue(MPU6050_REG_ADDR_PWR_MGMT_1, 0x00);

		// 2. sample rate divider
		// The sensor register output, FIFO output, and DMP sampling are all based on the Sample Rate.
		// The Sample Rate is generated by dividing the gyroscope output rate by SMPLRT_DIV:
		//      Sample Rate = Gyroscope Output Rate / (1 + SMPLRT_DIV)
		// where Gyroscope Output Rate = 8kHz when the DLPF is disabled (DLPF_CFG = 0 or 7),
		// and 1kHz when the DLPF is enabled (see register 26).
		// SMPLRT_DIV set the rate to the default value : Sample Rate = Gyroscope Rate.
		updateRegisterValue(MPU6050_REG_ADDR_SMPRT_DIV, smplrtDiv);

		// 3. This register configures the external Frame Synchronization (FSYNC) 
		// pin sampling and the Digital Low Pass Filter (DLPF) setting for both 
		// the gyroscopes and accelerometers.
		setDLPFConfig(dlpfCfg);

		// 4. Gyroscope configuration
		// FS_SEL selects the full scale range of the gyroscope outputs.
		byte fsSel = 0 << 3; // FS_SEL +- 250 °/s
		gyroLSBSensitivity = 131.; // cfr [datasheet 2 - p.31]
		updateRegisterValue(MPU6050_REG_ADDR_GYRO_CONFIG, fsSel);

		// 5. Accelerometer configuration [datasheet 2 - p.29]
		byte afsSel = 0; // AFS_SEL full scale range: ± 2g. LSB sensitivity : 16384 LSB/g
		accelLSBSensitivity = 16384.; // LSB Sensitivity corresponding to AFS_SEL 0
		updateRegisterValue(MPU6050_REG_ADDR_ACCEL_CONFIG, afsSel);

		// 6. Disable interrupts
		updateRegisterValue(MPU6050_REG_ADDR_INT_ENABLE, 0x00);

		// 7. Disable standby mode
		updateRegisterValue(MPU6050_REG_ADDR_PWR_MGMT_2, 0x00);

		calibrateSensors();
	}
    /* -----------------------------------------------------------------------
     *                             METHODS
     * -----------------------------------------------------------------------*/
    
    /**
     * Returns the Sample Rate of the MPU6050.
     * 
     * [datasheet 2 - p.12] The sensor output, FIFO output, and DMP sampling are 
     * all based on the Sample Rate ('Fs' in the datasheet).
     * 
     * The Sample Rate is generated by dividing the gyroscope output rate 
     * by SMPLRT_DIV:
     *              Sample Rate = Gyroscope Output Rate / (1 + SMPLRT_DIV)
     * where Gyroscope Output Rate = 8kHz when the DLPF is disabled (DLPF_CFG = 0 or 7),
     * and 1kHz when the DLPF is enabled (see Register 26)
     * 
     * Note: The accelerometer output rate is 1kHz (accelerometer and not gyroscope !).
     * This means that for a Sample Rate greater than 1kHz, the same accelerometer 
     * sample may be output to the FIFO, DMP, and sensor registers more than once.
     * 
     * @return the sample rate, in Hz.
     */
    public int getSampleRate() {
        int gyroscopeOutputRate = dlpfCfg == 0 || dlpfCfg == 7 ? 8000 : 1000; // 8kHz if DLPG disabled, and 1kHz if enabled.
        return gyroscopeOutputRate / (1 + smplrtDiv);
    }
    
    /**
     * Sets the value of the DLPF config, according to the datasheet informations.
     * 
     * The accelerometer and gyroscope are filtered according to the value of 
     * DLPF_CFG as shown in the table [datasheet 2 - p.13].
     * 
     * @param dlpfConfig the new DLPF_CFG value. Must be in the [0; 7] range,
     * where 0 and 7 are used to disable the DLPF.
     */
    public void setDLPFConfig(int dlpfConfig) {
        if(dlpfConfig > 7 || dlpfConfig < 0)
            throw new IllegalArgumentException("The DLPF config must be in the 0..7 range.");
        dlpfCfg  = dlpfConfig;
        updateRegisterValue(MPU6050_REG_ADDR_CONFIG, dlpfCfg);
    }
    
    /**
     * Reads the most recent accelerometer values on MPU6050 for X, Y and Z axis,
     * and calculates the corresponding accelerations in g, according to the
     * selected AFS_SEL mode.
     * @return [ACCEL_X, ACCEL_Y, ACCEL_Z], the accelerations in g for the x, y and z axis.
     */
    public double[] readScaledAccelerometerValues() {
        double accelX = readWord2C(MPU6050_REG_ADDR_ACCEL_XOUT_H);
        accelX /= accelLSBSensitivity;
        double accelY = readWord2C(MPU6050_REG_ADDR_ACCEL_YOUT_H);
        accelY /= accelLSBSensitivity;
        double accelZ = readWord2C(MPU6050_REG_ADDR_ACCEL_ZOUT_H);
        accelZ /= accelLSBSensitivity;
        
        return new double[]{accelX, accelY, -accelZ};
    }
    
    /**
     * Reads the most recent gyroscope values on the MPU6050 for X, Y and Z axis,
     * and calculates the corresponding angular speeds in degrees/sec,
     * according to the selected FS_SEL mode.
     * @return [GYRO_X, GYRO_Y, GYRO_Z], the angular velocities in degrees/sec for the x, y and z axis.
     */
    public double[] readScaledGyroscopeValues() {
        double gyroX = readWord2C(MPU6050_REG_ADDR_GYRO_XOUT_H);
        gyroX /= gyroLSBSensitivity;
        double gyroY = readWord2C(MPU6050_REG_ADDR_GYRO_YOUT_H);
        gyroY /= gyroLSBSensitivity;
        double gyroZ = readWord2C(MPU6050_REG_ADDR_GYRO_ZOUT_H);
        gyroZ /= gyroLSBSensitivity;
        
        return new double[]{gyroX, gyroY, gyroZ};
    }
    
    /**
     * Callibrate the accelerometer and gyroscope sensors.
     */
    private void calibrateSensors() {
        Tools.log("Calibration starting in 5 seconds (don't move the sensor).", Tools.Color.ANSI_RED);
        //Tools.sleepMilliseconds(5000);
        Tools.log("Calibration started (~5s) (don't move the sensor)", Tools.Color.ANSI_RED);
        
        int nbReadings = 50;
        
        // Gyroscope offsets
        gyroAngularSpeedOffsetX = 0.;
        gyroAngularSpeedOffsetY = 0.;
        gyroAngularSpeedOffsetZ = 0.;
        for(int i = 0; i < nbReadings; i++) {
            double[] angularSpeeds = readScaledGyroscopeValues();
            gyroAngularSpeedOffsetX += angularSpeeds[0];
            gyroAngularSpeedOffsetY += angularSpeeds[1];
            gyroAngularSpeedOffsetZ += angularSpeeds[2];
            Tools.sleepMilliseconds(100);
        }
        gyroAngularSpeedOffsetX /= nbReadings;
        gyroAngularSpeedOffsetY /= nbReadings;
        gyroAngularSpeedOffsetZ /= nbReadings;
        
        Tools.log("Calibration ended", Tools.Color.ANSI_RED);
    }
    
    /**
     * Starts the thread responsible to update MPU6050 values in background.
     */
    public void startUpdatingThread() {
        if(updatingThread == null || !updatingThread.isAlive()) {
            updatingThreadStopped = false;
            lastUpdateTime = System.currentTimeMillis();
            updatingThread = new Thread(() -> {
                while(!updatingThreadStopped) {
                    updateValues();
                }
            });
            updatingThread.start();
        } else {
            Tools.debug("Updating thread of the MPU6050 is already started.", Tools.Color.ANSI_RED);
        }
    }
		
    
    /**
     * Stops the thread responsible to update MPU6050 values in background. 
     * @throws InterruptedException if any thread has interrupted the current thread.
     * The interrupted status of the current thread is cleared when this exception is thrown.
     */
    public void stopUpdatingThread() throws InterruptedException {
        updatingThreadStopped = true;
        try {
            updatingThread.join();
        } catch (InterruptedException e) {
            Tools.log("Exception when joining the updating thread.");
            throw e;
        }
        updatingThread = null;
    }
    
    /**
     * Update values for the accelerometer angles, gyroscope angles and filtered angles values.
     * <p><i>This method is used with the updating thread.</i></p>
     */
    private void updateValues() {
        // Accelerometer
        double[] accelerations = readScaledAccelerometerValues();
        accelAccelerationX = accelerations[0];
        accelAccelerationY = accelerations[1];
        accelAccelerationZ = accelerations[2];
        accelAngleX = getAccelXAngle(accelAccelerationX, accelAccelerationY, accelAccelerationZ);
        accelAngleY = getAccelYAngle(accelAccelerationX, accelAccelerationY, accelAccelerationZ);
        accelAngleZ = getAccelZAngle();
        
        // Gyroscope
        double[] angularSpeeds = readScaledGyroscopeValues();
        gyroAngularSpeedX = angularSpeeds[0] - gyroAngularSpeedOffsetX;
        gyroAngularSpeedY = angularSpeeds[1] - gyroAngularSpeedOffsetY;
        gyroAngularSpeedZ = angularSpeeds[2] - gyroAngularSpeedOffsetZ;
        // angular speed * time = angle
        double dt = Math.abs(System.currentTimeMillis() - lastUpdateTime) / 1000.; // s
        double deltaGyroAngleX = gyroAngularSpeedX * dt;
        double deltaGyroAngleY = gyroAngularSpeedY * dt;
        double deltaGyroAngleZ = gyroAngularSpeedZ * dt;
        lastUpdateTime = System.currentTimeMillis();
        
        gyroAngleX += deltaGyroAngleX;
        gyroAngleY += deltaGyroAngleY;
        gyroAngleZ += deltaGyroAngleZ;
        
        // Complementary Filter
        double alpha = 0.96;
        filteredAngleX = alpha * (filteredAngleX + deltaGyroAngleX) + (1. - alpha) * accelAngleX;
        filteredAngleY = alpha * (filteredAngleY + deltaGyroAngleY) + (1. - alpha) * accelAngleY;
        filteredAngleZ = filteredAngleZ + Math.round(deltaGyroAngleZ*100)/100.;
    }
    
    /**
     * Get the last acceleration values, in g, retrieved from the accelerometer,
     * for the x, y and z axis.
     * <p><i>(using the updating thread)</i></p>
     * @return the accelerations for the x, y and z axis. [-1, -1, -1] if the updating thread isn't running.
     */
    public double[] getAccelAccelerations() {
        if(updatingThreadStopped)
            return new double[] {-1., -1., -1.};
        return new double[] {accelAccelerationX, accelAccelerationY, accelAccelerationZ};
    }
    
    /**
     * Get the last angle values, in °, retrieved from the accelerometer,
     * for the x, y and z axis.
     * <p><i>(using the updating thread)</i></p>
     * @return the angle values for the x, y and z axis. [-1, -1, -1] if the updating thread isn't running.
     */
    public double[] getAccelAngles() {
        if(updatingThreadStopped)
            return new double[] {-1., -1., -1.};
        return new double[] {accelAngleX, accelAngleY, accelAngleZ};
    }
    
    /**
     * Get the last angular speed values, in °/sec, retrieved from the gyroscope,
     * for the x, y and z axis.
     * <p><i>(using the updating thread)</i></p>
     * @return the angular speed values for the x, y and z axis. [-1, -1, -1] if the updating thread isn't running.
     */
    public double[] getGyroAngularSpeeds() {
        if(updatingThreadStopped)
            return new double[] {-1., -1., -1.};
        return new double[] {gyroAngularSpeedX, gyroAngularSpeedY, gyroAngularSpeedZ};
    }
    
    /**
     * Get the last angles values, in °, retrieved from the gyroscope,
     * for the x, y and z axis.
     * <p><i>(using the updating thread)</i></p>
     * @return the angles values from the gyroscope for the x, y and z axis. [-1, -1, -1] if the updating thread isn't running.
     */
    public double[] getGyroAngles() {
        if(updatingThreadStopped)
            return new double[] {-1., -1., -1.};
        return new double[] {gyroAngleX, gyroAngleY, gyroAngleZ};
    }
    
    /**
     * Get the calculated offsets for the angular speeds from the gyroscope,
     * for the x, y and z axis.
     * <p><i>(calculated with the calibration function)</i></p>
     * @return the offsets for the angular speeds from the gyroscope.
     */
    public double[] getGyroAngularSpeedsOffsets() {
        return new double[] {gyroAngularSpeedOffsetX, gyroAngularSpeedOffsetY, gyroAngularSpeedOffsetZ};
    }
    
    /**
     * Last angle value, in °, calculated from the accelerometer and the gyroscope,
     * for the x, y and z axis.
     * <p><i>(using the updating thread)</i></p>
     * @return the angles values, in °, filtered with values from the accelerometer and the gyroscope.
     */
    public double[] getFilteredAngles() {
        if(updatingThreadStopped)
            return new double[] {-1., -1., -1.};
        return new double[] {filteredAngleX, filteredAngleY, filteredAngleZ};
    }
	  /* -----------------------------------------------------------------------
     *                              UTILS
     * -----------------------------------------------------------------------*/
        public void updateRegisterValue(int address, int value) {
        writeUnsignedValueToRegister(address, value);
        
        // we check that the value of the register has been updated
        int readRegisterValue = readUnsignedRegisterValue(address);
        if(readRegisterValue != value)
            throw new RaspoidException("Error when updating the MPU6050 register value (register: " + 
                    address + ", value: " + value + ")");
    }
    /**
	    public void updateRegisterValue(int address, int value) {
        writeUnsignedValueToRegister(address, value);
        
        // we check that the value of the register has been updated
        int readRegisterValue = readUnsignedRegisterValue(address);
        if(readRegisterValue != value)
            throw new RaspoidException("Error when updating the MPU6050 register value (register: " + 
                    address + ", value: " + value + ")");
    }
    
    /**
     * Reads the content of a specific register of the MPU6050.
     * @param registerAddress the address of the register to read.
     * @return the int representation of the content of the register.
     */
    private int readRegisterValue(int registerAddress) {
        return readUnsignedRegisterValue(registerAddress);
    }
    
    /**
     * Reads the content of two consecutive registers, starting at registerAddress,
     * and returns the int representation of the combination of those registers,
     * with a two's complement representation.
     * @param registerAddress the address of the first register to read.
     * @return the int representation of the combination of the two consecutive
     * registers, with a two's complement representation.
     */
    private int readWord2C(int registerAddress) {
        int value = readRegisterValue(registerAddress);
        value = value << 8;
        value += readRegisterValue(registerAddress + 1);
        
        if (value >= 0x8000)
            value = -(65536 - value);
        return value;
    }
    
	private double distance(double a, double b) {
		return Math.sqrt(a * a + b * b);
	}

	private double getAccelXAngle(double x, double y, double z) {
		// v1 - 360
		double radians = Math.atan2(y, distance(x, z));
		double delta = 0.;
		if (y >= 0) {
			if (z >= 0) {
				// pass
			} else {
				radians *= -1;
				delta = 180.;
			}
		} else {
			if (z <= 0) {
				radians *= -1;
				delta = 180.;
			} else {
				delta = 360.;
			}
		}
		return radians * RADIAN_TO_DEGREE + delta;
	}

	private double getAccelYAngle(double x, double y, double z) {
		// v2
		double tan = -1 * x / distance(y, z);
		double delta = 0.;
		if (x <= 0) {
			if (z >= 0) {
				// q1
				// nothing to do
			} else {
				// q2
				tan *= -1;
				delta = 180.;
			}
		} else {
			if (z <= 0) {
				// q3
				tan *= -1;
				delta = 180.;
			} else {
				// q4
				delta = 360.;
			}
		}

		return Math.atan(tan) * RADIAN_TO_DEGREE + delta;
	}

	private double getAccelZAngle() {
		return ACCEL_Z_ANGLE;
	}

	public static String angleToString(double angle) {
		return String.format("%.4f", angle) + "°";
	}

	public static String accelToString(double accel) {
		return String.format("%.6f", accel) + "g";
	}

	public static String angularSpeedToString(double angularSpeed) {
		return String.format("%.4f", angularSpeed) + "°/s";
	}

	public static String xyzValuesToString(String x, String y, String z) {
		return "x: " + x + "\ty: " + y + "\tz: " + z;
	}
}

