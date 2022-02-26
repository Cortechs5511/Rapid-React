package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private final CANSparkMax shooterMotor1 = new CANSparkMax(ShooterConstants.SHOOTER1_PORT, CANSparkMax.MotorType.kBrushless);
    private final CANSparkMax shooterMotor2 = new CANSparkMax(ShooterConstants.SHOOTER2_PORT, CANSparkMax.MotorType.kBrushless);

    private final SparkMaxPIDController shooterPIDController = shooterMotor1.getPIDController();
    private final RelativeEncoder shooterEncoder = shooterMotor1.getEncoder();

    public void set(double speed1, double speed2) {
        shooterMotor1.set(speed1);
    }

    public void setRampRate(double rampRate1, double rampRate2) {
        shooterMotor1.setOpenLoopRampRate(rampRate1);
        shooterMotor2.setOpenLoopRampRate(rampRate2);
    }

    private CANSparkMax createController(int port, boolean isInverted) {
        CANSparkMax controller = new CANSparkMax(port, CANSparkMaxLowLevel.MotorType.kBrushless);
        controller.restoreFactoryDefaults();

        controller.enableVoltageCompensation(ShooterConstants.VOLTAGE_COMPENSATION);
        controller.setIdleMode(ShooterConstants.IDLE_MODE);
        controller.setOpenLoopRampRate(ShooterConstants.RAMP_RATE);
        controller.setClosedLoopRampRate(ShooterConstants.RAMP_RATE);

        controller.setSmartCurrentLimit(ShooterConstants.CURRENT_LIMIT);
        controller.setSecondaryCurrentLimit(100);

        controller.setInverted(isInverted);
        return controller;
    }
}
