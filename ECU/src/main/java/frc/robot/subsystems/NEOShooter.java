package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;

public class NEOShooter extends SubsystemBase implements Shooter {
    private final CANSparkMax bottomShooter = createShooterController(ShooterConstants.BOTTOM_SHOOTER_ID, ShooterConstants.INVERT_BOTTOM_SHOOTER);
    private final CANSparkMax topShooter = createShooterController(ShooterConstants.TOP_SHOOTER_ID, ShooterConstants.INVERT_TOP_SHOOTER);

    private final SparkMaxPIDController bottomPID = createShooterPID(bottomShooter);
    private final SparkMaxPIDController topPID = createShooterPID(topShooter);

    private final RelativeEncoder bottomEncoder = bottomShooter.getEncoder();
    private final RelativeEncoder topEncoder = topShooter.getEncoder();

    @Override
    public double getBottomSpeed() {
        return bottomEncoder.getVelocity();
    }

    @Override
    public void setBottomSpeed(double speed) {
        bottomPID.setReference(speed, CANSparkMax.ControlType.kVelocity);
        topPID.setReference(speed, CANSparkMax.ControlType.kVelocity);
    }

    @Override
    public void setBottomPower(double power) {
        bottomShooter.set(power);
        topShooter.set(power);
    }

    @Override
    public double getBottomSetpoint() {
        return bottomShooter.get();
    }

    @Override
    public void setRampRate(double rate) {
        bottomShooter.setOpenLoopRampRate(rate);
        topShooter.setOpenLoopRampRate(rate);
        bottomShooter.setClosedLoopRampRate(rate);
        topShooter.setClosedLoopRampRate(rate);
    }

    @Override
    public void periodic() {
        if (Constants.DIAGNOSTICS) {
            SmartDashboard.putNumber("Shooter/Bottom Speed", getBottomSpeed());
            SmartDashboard.putNumber("Shooter/Top Speed", topEncoder.getVelocity());

            SmartDashboard.putNumber("Shooter/Bottom Current", bottomShooter.getOutputCurrent());
            SmartDashboard.putNumber("Shooter/Top Current", topShooter.getOutputCurrent());

            SmartDashboard.putNumber("Shooter/Bottom Temperature", bottomShooter.getMotorTemperature());
            SmartDashboard.putNumber("Shooter/Top Temperature", topShooter.getMotorTemperature());
        }
    }

    private CANSparkMax createShooterController(int id, boolean invert) {
        CANSparkMax controller = new CANSparkMax(id, CANSparkMax.MotorType.kBrushless);
        controller.restoreFactoryDefaults();

        controller.setInverted(invert);
        controller.setIdleMode(CANSparkMax.IdleMode.kBrake);
        controller.setSmartCurrentLimit(100);

        controller.setOpenLoopRampRate(ShooterConstants.LONG_RAMP_RATE);
        controller.setClosedLoopRampRate(ShooterConstants.LONG_RAMP_RATE);
        controller.enableVoltageCompensation(ShooterConstants.VOLTAGE_COMPENSATION);

        return controller;
    }

    /**
     * Creates a PID controller from CANSparkMax for shooter
     *
     * @param controller CANSparkMax controller
     * @return SparkMaxPIDController
     */
    private SparkMaxPIDController createShooterPID(CANSparkMax controller) {
        SparkMaxPIDController pid = controller.getPIDController();

        pid.setP(ShooterConstants.PID_P);
        pid.setI(ShooterConstants.PID_I);
        pid.setD(ShooterConstants.PID_D);
        pid.setFF(ShooterConstants.PID_FF);

        return pid;
    }

    public void setTopPower(double power) {
        // TODO Auto-generated method stub
    }
}
