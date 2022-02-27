package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class AutoCommand extends CommandBase {
    Drive m_drive;

    public AutoCommand(Drive subsystem) {
        addRequirements(subsystem);

        m_drive = subsystem;
    }

    @Override
    public void initialize() {
        m_drive.setSpeed(0.0, 0.0);;
    }

    @Override
    public void execute() {}

    @Override
    public void end(boolean interrupted) {
        m_drive.setSpeed(0.0, 0.0);
    }

    @Override   
    public boolean isFinished() {
        return false;
    }
}
