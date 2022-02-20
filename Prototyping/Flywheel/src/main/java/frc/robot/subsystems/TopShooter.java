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

public class TopShooter extends SubsystemBase {
   
    private final CANSparkMax m_rightMotor = createSparkMax(ShooterConstants.rightId);
    private final RelativeEncoder r_encoder = m_rightMotor.getEncoder();

    private final SparkMaxPIDController m_pidController = m_rightMotor.getPIDController();

    public TopShooter() {
       
        m_rightMotor.setInverted(true);

        
        r_encoder.setPositionConversionFactor(42);

        m_pidController.setP(ShooterConstants.kP);
        m_pidController.setI(ShooterConstants.kI);
        m_pidController.setD(ShooterConstants.kD);
        m_pidController.setFF(ShooterConstants.kF);

        SmartDashboard.putNumber("TopShooter/P", ShooterConstants.kP);
        SmartDashboard.putNumber("TopShooter/I", ShooterConstants.kI);
        SmartDashboard.putNumber("TopShooter/D", ShooterConstants.kD);
        SmartDashboard.putNumber("TopShooter/F", ShooterConstants.kF);

        SmartDashboard.putNumber("TopShooter/Ramp Rate", 1.5);
    }

    @Override
    public void periodic() {
        m_pidController.setP(SmartDashboard.getNumber("TopShooter/P", ShooterConstants.kP));
        m_pidController.setI(SmartDashboard.getNumber("TopShooter/I", ShooterConstants.kI));
        m_pidController.setD(SmartDashboard.getNumber("TopShooter/D", ShooterConstants.kD));
        m_pidController.setFF(SmartDashboard.getNumber("TopShooter/F", ShooterConstants.kF));

        this.setRampRate(SmartDashboard.getNumber("TopShooter/Ramp Rate", 1.5));

        SmartDashboard.putNumber("TopShooter/Speed", r_encoder.getVelocity());

        SmartDashboard.putNumber("TopShooter/Setpoint", m_rightMotor.get());
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
        m_rightMotor.set(power);
    }

    public void setPIDReference(double reference) {
        m_pidController.setReference(reference, ControlType.kVelocity);
    }

    public void setRampRate(double rate) {
        m_rightMotor.setOpenLoopRampRate(rate);
       
        m_rightMotor.setClosedLoopRampRate(rate);
    }
}

