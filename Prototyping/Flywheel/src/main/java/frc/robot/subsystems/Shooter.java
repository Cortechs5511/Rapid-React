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
    private final CANSparkMax m_leftMotor = createSparkMax(ShooterConstants.leftId);
    
    //private final RelativeEncoder r_encoder = m_rightMotor.getEncoder();
    private final RelativeEncoder l_encoder = m_leftMotor.getEncoder();
    private final SparkMaxPIDController m_pidController = m_leftMotor.getPIDController();

    public Shooter() {
        m_leftMotor.setInverted(false);
        
        
        l_encoder.setPositionConversionFactor(42);

        m_pidController.setP(ShooterConstants.kP);
        m_pidController.setI(ShooterConstants.kI);
        m_pidController.setD(ShooterConstants.kD);
        m_pidController.setFF(ShooterConstants.kF);

        SmartDashboard.putNumber("BottomShooter/P", ShooterConstants.kP);
        SmartDashboard.putNumber("BottomShooter/I", ShooterConstants.kI);
        SmartDashboard.putNumber("BottomShooter/D", ShooterConstants.kD);
        SmartDashboard.putNumber("BottomShooter/F", ShooterConstants.kF);

        SmartDashboard.putNumber("BottomShooter/Ramp Rate", 1.5);
    }

    @Override
    public void periodic() {
        m_pidController.setP(SmartDashboard.getNumber("BottomShooter/P", ShooterConstants.kP));
        m_pidController.setI(SmartDashboard.getNumber("BottomShooter/I", ShooterConstants.kI));
        m_pidController.setD(SmartDashboard.getNumber("BottomShooter/D", ShooterConstants.kD));
        m_pidController.setFF(SmartDashboard.getNumber("BottomShooter/F", ShooterConstants.kF));

        this.setRampRate(SmartDashboard.getNumber("BottomShooter/Ramp Rate", 1.5));

        SmartDashboard.putNumber("BottomShooter/Speed", l_encoder.getVelocity());

        SmartDashboard.putNumber("BottomShooter/Setpoint", m_leftMotor.get());
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
        

        m_leftMotor.setClosedLoopRampRate(rate);
      
    }
}
