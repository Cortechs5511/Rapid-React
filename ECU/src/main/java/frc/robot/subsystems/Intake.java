package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
    private final WPI_TalonSRX intakeMotor = new WPI_TalonSRX(IntakeConstants.INTAKE_ID);
    private final WPI_TalonSRX wristMotor = new WPI_TalonSRX(IntakeConstants.WRIST_ID);

    public void setWrist(double speed) {
        wristMotor.set(speed);
    }

    public void setIntake(double speed) {
        intakeMotor.set(speed);

    }
    public Intake() {
        intakeMotor.setInverted(IntakeConstants.INVERT_INTAKE);
        wristMotor.setInverted(IntakeConstants.INVERT_WRIST);
    }

    private WPI_TalonSRX createIntakeController(int id, boolean invert) {
        WPI_TalonSRX controller = new WPI_TalonSRX(id);

        controller.configFactoryDefault();

        controller.configContinuousCurrentLimit(IntakeConstants.CURRENT_LIMIT);
        controller.configPeakCurrentLimit(IntakeConstants.PEAK_CURRENT_LIMIT);

        controller.configOpenloopRamp(IntakeConstants.RAMP_RATE);
        controller.configClosedloopRamp(IntakeConstants.RAMP_RATE);

        controller.setInverted(invert);

        return controller;
    }

    @Override
    public void periodic() {
    }
}