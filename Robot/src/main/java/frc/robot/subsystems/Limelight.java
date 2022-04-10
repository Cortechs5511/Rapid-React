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
        setLight(false);
    }

    /**
     * Method to return desired top shooter power
     *
     * @return double calculated power for top shooter
     */
    public double getTopPower() {
        updateDistance();

        if (distance < 0) {
            return ShooterConstants.TOP_SHOOTER_POWER;
        }

        //Added 1.5% to the constant term
        double power = (-7.5505436444866E-7 * Math.pow(distance, 3)) + (3.0733525002985E-4 * Math.pow(distance, 2))
                - (0.035273885457723 * distance) + 1.6119725962643 + 0.015;
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

        //Added 2% to constant term
        double power = (7.4410621008951E-8 * Math.pow(distance, 3)) - (2.685403143862E-5 * Math.pow(distance, 2))
                + (0.0036871646248961 * distance) + 0.33565674462514 + 0.02;
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
        distance = 12 * ((-2.2350145831184E-4 * Math.pow(y, 3)) + (0.0070239840777885 * Math.pow(y, 2))
                - (0.29164518774187 * y) + 9.4505826482213);
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
        ledMode.setNumber(on ? 1 : 3);
        SmartDashboard.putBoolean("Limelight/Limelight Lights", on);
    }

    @Override
    public void periodic() {
    }
}