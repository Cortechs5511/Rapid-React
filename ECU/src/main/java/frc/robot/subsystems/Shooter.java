package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase{
    private final WPI_TalonFX bottomShooter = createShooterController(ShooterConstants.BOTTOM_SHOOTER_ID, ShooterConstants.INVERT_BOTTOM_SHOOTER);
    private final WPI_TalonFX topShooter = createShooterController(ShooterConstants.TOP_SHOOTER_ID, ShooterConstants.INVERT_TOP_SHOOTER);

    public Shooter() {
    }

    public double getBottomSpeed() {
        return bottomShooter.getSelectedSensorVelocity() * ShooterConstants.RPM_TO_UNITS;
    }

    public double getTopSpeed() {
        return topShooter.getSelectedSensorVelocity() * ShooterConstants.RPM_TO_UNITS;
    }

    /**
     * Sets bottom wheel to open loop output
     *
     * @param power double speed [0.0, 1.0]
     */
    public void setBottomPower(double power) {
        bottomShooter.set(ControlMode.PercentOutput, power);
    }


    /**
     * Sets top wheel to open loop output
     *
     * @param power double speed [0.0, 1.0]
     */
    public void setTopPower(double power) {
        topShooter.set(ControlMode.PercentOutput, power);
    }

    /**
     * Returns whether shooter is operating
     * 
     * @return boolean whether either motor setpoint is above zero
     */
    public boolean getShooting() {
        return (bottomShooter.get() > 0) || (topShooter.get() > 0);
    }

    @Override
    public void periodic() {
        if (Constants.DIAGNOSTICS) {
            SmartDashboard.putNumber("Shooter/Bottom Speed", getBottomSpeed());
            SmartDashboard.putNumber("Shooter/Top Speed", getTopSpeed());
            SmartDashboard.putNumber("Shooter/Bottom Current", bottomShooter.getSupplyCurrent());
            SmartDashboard.putNumber("Shooter/Top Current", topShooter.getSupplyCurrent());
            SmartDashboard.putNumber("Shooter/Bottom Temperature", bottomShooter.getTemperature());
            SmartDashboard.putNumber("Shooter/Top Temperature", topShooter.getTemperature());
        }
    }

    /**
     * Creates a new TalonFX controller for the shooter
     *
     * @param id     int CAN id of the Falcon 500
     * @param invert boolean flag for motor inversion
     * @return WPI_TalonFX controller
     */
    private WPI_TalonFX createShooterController(int id, boolean invert) {
        WPI_TalonFX shooterController = new WPI_TalonFX(id);
        shooterController.configFactoryDefault();

        shooterController.setInverted(invert);
        shooterController.setNeutralMode(ShooterConstants.NEUTRAL_MODE);
        shooterController.configSupplyCurrentLimit(ShooterConstants.CURRENT_LIMIT);

        shooterController.configOpenloopRamp(ShooterConstants.LONG_RAMP_RATE);
        shooterController.configClosedloopRamp(ShooterConstants.LONG_RAMP_RATE);
        shooterController.configVoltageCompSaturation(ShooterConstants.VOLTAGE_COMPENSATION);

        // shooterController.configPeakOutputReverse(0);

        return shooterController;
    }
}
