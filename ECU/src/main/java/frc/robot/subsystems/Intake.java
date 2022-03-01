package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
    private final WPI_TalonSRX intakeMotor = new WPI_TalonSRX(IntakeConstants.INTAKE_ID);
    private final WPI_TalonSRX wristMotor = new WPI_TalonSRX(IntakeConstants.WRIST_ID);

    public Intake() {
        intakeMotor.setInverted(IntakeConstants.INVERT_INTAKE);
        wristMotor.setInverted(IntakeConstants.INVERT_WRIST);
    }

    /**
     * Set wrist motor to open loop power
     *
     * @param speed double speed
     */
    public void setWrist(double speed) {
        wristMotor.set(speed);
    }

    /**
     * Set intake motor to open loop power
     *
     * @param speed double speed
     */
    public void setIntake(double speed) {
        intakeMotor.set(speed);
    }

    /**
     * Create intake (Talon SRX) motor controller with preferred configuration
     *
     * @param id     CAN ID of motor controller
     * @param invert boolean whether to invert
     * @return WPI_TalonSRX
     */
    private WPI_TalonSRX createIntakeController(int id, boolean invert) {
        WPI_TalonSRX controller = new WPI_TalonSRX(id);

        controller.configFactoryDefault();

        controller.configContinuousCurrentLimit(IntakeConstants.CURRENT_LIMIT);
        controller.configPeakCurrentLimit(IntakeConstants.PEAK_CURRENT_LIMIT);

        controller.configOpenloopRamp(IntakeConstants.RAMP_RATE);
        controller.configClosedloopRamp(IntakeConstants.RAMP_RATE);

        controller.setInverted(invert);
        controller.setNeutralMode(IntakeConstants.NEUTRAL_MODE);

        return controller;
    }

    @Override
    public void periodic() {
    }
}