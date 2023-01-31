package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
    private final WPI_TalonSRX intakeMotor = createIntakeTalon();
    private final CANSparkMax wristMotor = createIntakeSparkMAX();

    public Intake() {
    }

    /**
     * Set wrist motor to power
     *
     * @param speed double speed out of 1
     */
    public void setWrist(double speed) {
        wristMotor.set(speed);
    }

    /**
     * Set intake motor to power
     *
     * @param speed double speed out of 1
     */
    public void setIntake(double speed) {
        intakeMotor.set(speed);
    }

    /**
     * Get intake wheel motor current
     *
     * @return double output current in amperes
     */
    public double getIntakeCurrent() {
        return intakeMotor.getStatorCurrent();
    }

    /**
     * Get wrist motor current
     * 
     * @return double output current in amperes
     */
    public double getWristCurrent() {
        return wristMotor.getOutputCurrent();

    }

    /**
     * Create intake (Talon SRX) motor controller with preferred configuration
     *
     * @return WPI_TalonSRX
     */
    private WPI_TalonSRX createIntakeTalon() {
        WPI_TalonSRX controller = new WPI_TalonSRX(IntakeConstants.INTAKE_ID);

        controller.configFactoryDefault();

        controller.configContinuousCurrentLimit(IntakeConstants.CURRENT_LIMIT);
        controller.configPeakCurrentLimit(IntakeConstants.PEAK_CURRENT_LIMIT);

        controller.configOpenloopRamp(IntakeConstants.RAMP_RATE);
        controller.configClosedloopRamp(IntakeConstants.RAMP_RATE);

        controller.setInverted(IntakeConstants.INVERT_INTAKE);
        controller.setNeutralMode(IntakeConstants.NEUTRAL_MODE);

        return controller;
    }

    /**
     * Create intake (SPARK MAX) motor controller with preferred configuration
     *
     * @return CANSparkMax
     */
    private CANSparkMax createIntakeSparkMAX() {
        CANSparkMax controller = new CANSparkMax(IntakeConstants.WRIST_ID, MotorType.kBrushless);
        controller.restoreFactoryDefaults();

        controller.enableVoltageCompensation(DriveConstants.VOLTAGE_COMPENSATION);
        controller.setIdleMode(DriveConstants.IDLE_MODE);
        controller.setOpenLoopRampRate(DriveConstants.RAMP_RATE);
        controller.setClosedLoopRampRate(DriveConstants.RAMP_RATE);

        controller.setSmartCurrentLimit(IntakeConstants.WRIST_CURRENT_LIMIT);
        controller.setSecondaryCurrentLimit(100);

        controller.setInverted(IntakeConstants.INVERT_WRIST);
        return controller;
    }

    @Override
    public void periodic() {
        if (Constants.DIAGNOSTICS) {
            SmartDashboard.putNumber("Intake/Wheel Current", getIntakeCurrent());
            SmartDashboard.putNumber("Intake/Wrist Power", wristMotor.get());
            SmartDashboard.putNumber("Intake/Wrist Current", getWristCurrent());
        }

    }
}