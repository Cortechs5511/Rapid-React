package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {
    private final NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private final NetworkTableEntry tx = table.getEntry("tx");
    private final NetworkTableEntry ty = table.getEntry("ty");
    private final NetworkTableEntry tv = table.getEntry("tv");
    private final NetworkTableEntry ledMode = table.getEntry("ledMode");

    private double v;


    public Limelight() {
        // Turn lights off
        ledMode.setNumber(1);
    }

    /**
     * Method to return desired shooter RPM
     *
     * @return double calculated RPM for shooter
     */
    public double calculateRPM() {
        // Math for determining the flat rpm from estimated distance
        // currently setting rpm to 0, but that should be changed for actual testing
        double rpm = 0;
        
        if (v != 0) {
            return rpm;
        } else {
            return 0;
        }
    }

    /**
     * Returns target x deviation
     * 
     * @return double x in degrees
     */
    public double getX() {
        return tx.getDouble(0.0);
    }

    /**
     * Returns target y deviation
     * 
     * @return double y in degrees
     */
    public double getY() {
        return ty.getDouble(0.0);
    }

    /**
     * Returns whether a valid target is selected or not.
     * This is based off of target validity value.
     * 
     * @return boolean whether target is valid or not
     */
    public boolean getValidTarget() {
        return tv.getDouble(0.0) != 0.0;
    }

    /**
     * Returns light status 
     * 
     * @return boolean light on
     */
    public boolean getLightStatus() {
        return ledMode.getDouble(1) == 3;
    }

    /**
     * Sets lights
     * 
     * @param on boolean desired light state
     */
    public void setLight(boolean on) {
        ledMode.setNumber(on ? 3 : 1); // 3 = on, 1 = off
        SmartDashboard.putBoolean("Limelight/Limelight Lights", on);
    }

    @Override
    public void periodic() {
    }
}