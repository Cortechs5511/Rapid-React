package frc.robot.subsystems;

import java.util.Set;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
    private final WPI_TalonSRX intakeMotor = createIntakeTalon(IntakeConstants.INTAKE_ID, IntakeConstants.INVERT_INTAKE);
    private final CANSparkMax wristMotor = createIntakeSparkMAX(IntakeConstants.WRIST_ID, IntakeConstants.INVERT_WRIST);

    public Intake() {
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
    *Get intake wheel motor current
    *
    * @return output current as double 
    */
    public double getIntakeCurrent(){
        return intakeMotor.getStatorCurrent();   
    }
    public double getWristCurrent(){
        return wristMotor.getOutputCurrent();

    
    
    }
    /**
     * Create intake (Talon SRX) motor controller with preferred configuration
     *
     * @param id     CAN ID of motor controller
     * @param invert boolean whether to invert
     * @return WPI_TalonSRX
     */
    private WPI_TalonSRX createIntakeTalon(int id, boolean invert) {
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


    /**
     * Create intake (SPARK MAX) motor controller with preferred configuration
     *
     * @param id     CAN ID of motor controller
     * @param invert boolean whether to invert
     * @return CANSparkMax
     */
    private CANSparkMax createIntakeSparkMAX(int id, boolean invert) {
        CANSparkMax controller = new CANSparkMax(id, MotorType.kBrushless);
        controller.restoreFactoryDefaults();

        controller.enableVoltageCompensation(DriveConstants.VOLTAGE_COMPENSATION);
        controller.setIdleMode(DriveConstants.IDLE_MODE);
        controller.setOpenLoopRampRate(DriveConstants.RAMP_RATE);
        controller.setClosedLoopRampRate(DriveConstants.RAMP_RATE);

        controller.setSmartCurrentLimit(IntakeConstants.WRIST_CURRENT_LIMIT);
        controller.setSecondaryCurrentLimit(100);

        controller.setInverted(invert);
        return controller;
    }

    @Override
    public void periodic() {
        if(Constants.DIAGNOSTICS){
            SmartDashboard.putNumber("Intake/Wheel Current", getIntakeCurrent());
            SmartDashboard.putNumber("Intake/Wrist Power", wristMotor.get());
            SmartDashboard.putNumber("Intake/Wrist Current", getWristCurrent());
        }
    
    }
}