package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private final WPI_TalonFX leftShooter = createShooterController(ShooterConstants.LEFT_SHOOTER_ID, ShooterConstants.INVERT_LEFT_SHOOTER);
    private final WPI_TalonFX rightShooter = createShooterController(ShooterConstants.RIGHT_SHOOTER_ID, ShooterConstants.INVERT_RIGHT_SHOOTER);

    public Shooter() {
        rightShooter.set(ControlMode.Follower, ShooterConstants.LEFT_SHOOTER_ID);
    }

    public double getSpeed() {
        return leftShooter.getSelectedSensorVelocity() / ShooterConstants.RPM_TO_UNITS;
    }

    /**
     * Sets the shooter to a certain speed
     *
     * @param speed target velocity, RPM
     *                                                                               TODO: find units
     */
    public void setSpeed(double speed) {
        leftShooter.set(ControlMode.Velocity, speed * ShooterConstants.RPM_TO_UNITS);
    }

    /**
     * Sets shooter to open loop output
     *
     * @param power double speed [0.0, 1.0]
     */
    public void setPower(double power) {
        leftShooter.set(ControlMode.PercentOutput, power);
    }

    /**
     * Returns the most recently set PID reference value
     *
     * @return double reference value, RPM
     * TODO: confirm units in RPM
     */
    public double getSetpoint() {
        return leftShooter.getClosedLoopTarget() / ShooterConstants.RPM_TO_UNITS;
    }

    /**
     * Sets shooter controllers to adopt given ramp rate
     *
     * @param rate double ramp rate in seconds from neutral to full
     */
    public void setRampRate(double rate) {
        leftShooter.configOpenloopRamp(rate);
        rightShooter.configOpenloopRamp(rate);
        leftShooter.configClosedloopRamp(rate);
        rightShooter.configClosedloopRamp(rate);
    }

    @Override
    public void periodic() {
        if (Constants.DIAGNOSTICS) {
            SmartDashboard.putNumber("Shooter/Speed", getSpeed());
            SmartDashboard.putNumber("Shooter/Current", leftShooter.getSupplyCurrent());
            SmartDashboard.putNumber("Shooter/Temperature", leftShooter.getTemperature());
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

        shooterController.configPeakOutputReverse(0);

        return shooterController;
    }


}
