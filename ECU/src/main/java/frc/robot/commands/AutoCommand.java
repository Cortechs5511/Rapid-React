package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class AutoCommand extends CommandBase {
    final Drive drive;

    public AutoCommand(Drive subsystem) {
        addRequirements(subsystem);

        drive = subsystem;
    }

    @Override
    public void initialize() {
        drive.setSpeed(0.0, 0.0);
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {
        drive.setSpeed(0.0, 0.0);
    }

    @Override   
    public boolean isFinished() {
        return false;
    }
}
