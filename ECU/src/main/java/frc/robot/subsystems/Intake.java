package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
    private final WPI_TalonSRX intakeMotor = new WPI_TalonSRX(IntakeConstants.INTAKE_PORT);
    private final WPI_TalonSRX wristMotor = new WPI_TalonSRX(IntakeConstants.WRIST_PORT);

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

    @Override
    public void periodic() {
    }
}