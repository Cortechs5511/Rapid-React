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

    private double x;
    private double y;
    private double v;

    private double RPMAdjustment;

    public Limelight() {
        ledMode.setNumber(1); // sets lights off
        SmartDashboard.putNumber("Shooter/RPM Setpoint", 0);
        SmartDashboard.putNumber("Shooter/RPM Adjustment", 0);
        SmartDashboard.putNumber("Shooter/Flat RPM", 0);
        SmartDashboard.putBoolean("Limelight/Limelight Lights", false);
    }

    @Override
    public void periodic() {
        x = tx.getDouble(0.0);
        y = ty.getDouble(0.0);
        v = tv.getDouble(0.0);

        SmartDashboard.putNumber("Limelight/Limelight X", x);
        SmartDashboard.putBoolean("Limelight/Limelight Valid Target", v == 0);
    }

    public double calculateRPM() {
        double flatRPM = SmartDashboard.getNumber("Shooter/Flat RPM", 0);
        if (flatRPM != 0) {
            return flatRPM;
        }
        
        // Math for determining the flat rpm from estimated distance
        // currently setting rpm to 0, but that should be changed for actual testing
        double rpm = 0;
        SmartDashboard.putNumber("Shooter/RPM Setpoint", rpm);
        
        if (v != 0) {
            return rpm;
        } else {
            return 0;
        }
    }

    public double getX() {
        return x;
    }

    public double getLightStatus() {
        return ledMode.getDouble(1);
    }

    public void setLightStatus(double input) {
        ledMode.setNumber(input); // 3 = on, 1 = off
        SmartDashboard.putBoolean("Limelight/Limelight Lights", input != 1);
    }
}