// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TopShooter;

public class SetTopSpeed extends CommandBase {

    private TopShooter t_shooter;

    /** Creates a new SetPower. */
    public SetTopSpeed(TopShooter t_shooter) {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(t_shooter);
        this.t_shooter = t_shooter;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        t_shooter.setPower(0);

        SmartDashboard.putNumber("TopShooter/Target RPM", 0);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        // set shooter power to SmartDashboard value
        t_shooter.setPIDReference(SmartDashboard.getNumber("TopShooter/Target RPM", 0));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        t_shooter.setPower(0);
        
        SmartDashboard.putNumber("Shooter/Target RPM", 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}