package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Limelight extends SubsystemBase {
    private final NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private final NetworkTableEntry tx = table.getEntry("tx");
    private final NetworkTableEntry ty = table.getEntry("ty");
    private final NetworkTableEntry tv = table.getEntry("tv");
    private final NetworkTableEntry ledMode = table.getEntry("ledMode");

    // Abstracted distance, in inches
    private double distance;

    public Limelight() {
        // Turn lights off
        ledMode.setNumber(1);
    }

    /**
     * Method to return desired top shooter power
     *
     * @return double calculated power for top shooter
     */
    public double getTopPower() {
        if (distance < 0) {
            return ShooterConstants.TOP_SHOOTER_POWER;
        }

        // TODO: Fit top shooter power vs. distance and update
        double power = 0;
        return power;
    }
    
    /**
     * Method to return desired bottom shooter power
     *
     * @return double calculated power for bottom shooter
     */
    public double getBottomPower() {
        updateDistance();

        if (distance < 0) {
            return ShooterConstants.BOTTOM_SHOOTER_POWER;
        }

        // TODO: Fit bottom shooter power vs. distance and update
        double power = 0;
        return power;
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
     * Runs distance calculation from limelight y and pushes to field "distance"
     */
    private void updateDistance() {
        if (!getValidTarget()) {
            distance = -1.0;
            return;
        }

        double y = getY();
        distance = -0.001996 * Math.pow(y, 3) + 0.09212 * Math.pow(y, 2) - 3.634 * y + 115.1;
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