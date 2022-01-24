// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    // create two CANSparkMaxes
    private final CANSparkMax m_leftMotor = createSparkMax(ShooterConstants.leftId);
    private final CANSparkMax m_rightMotor = createSparkMax(ShooterConstants.rightId);

    private final RelativeEncoder m_encoder = m_leftMotor.getEncoder();
    private final SparkMaxPIDController m_pidController = m_leftMotor.getPIDController();

    /** Creates a new ExampleSubsystem. */
    public Shooter() {
        m_rightMotor.follow(m_leftMotor);
        // TODO: test motor inversion

        m_encoder.setPositionConversionFactor(42);

        m_pidController.setP(ShooterConstants.kP);
        m_pidController.setI(ShooterConstants.kI);
        m_pidController.setD(ShooterConstants.kD);
        m_pidController.setFF(ShooterConstants.kF);

        SmartDashboard.putNumber("Shooter/P", ShooterConstants.kP);
        SmartDashboard.putNumber("Shooter/I", ShooterConstants.kI);
        SmartDashboard.putNumber("Shooter/D", ShooterConstants.kD);
        SmartDashboard.putNumber("Shooter/F", ShooterConstants.kF);

        SmartDashboard.putNumber("Shooter/Ramp Rate", 1.5);
    }

    @Override
    public void periodic() {
        m_pidController.setP(SmartDashboard.getNumber("Shooter/P", ShooterConstants.kP));
        m_pidController.setI(SmartDashboard.getNumber("Shooter/I", ShooterConstants.kI));
        m_pidController.setD(SmartDashboard.getNumber("Shooter/D", ShooterConstants.kD));
        m_pidController.setFF(SmartDashboard.getNumber("Shooter/F", ShooterConstants.kF));

        this.setRampRate(SmartDashboard.getNumber("Shooter/Ramp Rate", 1.5));

        SmartDashboard.putNumber("Shooter/Speed", m_encoder.getVelocity());
        SmartDashboard.putNumber("Shooter/Setpoint", m_leftMotor.get());
    }

    private CANSparkMax createSparkMax(int id) {
        CANSparkMax sparkMax = new CANSparkMax(id, MotorType.kBrushless);
        sparkMax.restoreFactoryDefaults();

        sparkMax.setIdleMode(IdleMode.kCoast);
        sparkMax.setSmartCurrentLimit(60);
        sparkMax.setOpenLoopRampRate(1.5);
        sparkMax.setClosedLoopRampRate(1.5);
        sparkMax.enableVoltageCompensation(11);

        return sparkMax;
    }

    public void setPower(double power) {
        m_leftMotor.set(power);
    }

    public void setPIDReference(double reference) {
        m_pidController.setReference(reference, ControlType.kVelocity);
    }

    public void setRampRate(double rate) {
        m_leftMotor.setOpenLoopRampRate(rate);
        m_rightMotor.setOpenLoopRampRate(rate);

        m_leftMotor.setClosedLoopRampRate(rate);
        m_rightMotor.setClosedLoopRampRate(rate);
    }
}
