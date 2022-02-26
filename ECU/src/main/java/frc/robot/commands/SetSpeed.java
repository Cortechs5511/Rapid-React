package frc.robot.commands;

import frc.robot.OI;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetSpeed extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Drive m_drive;
    private final OI m_oi = OI.getInstance();

    public SetSpeed(Drive subsystem) {
        m_drive = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_drive.setSpeed(m_oi.getLeftYDeadband(), m_oi.getRightYDeadband());
    }

    @Override
    public void end(boolean interrupted) {
        m_drive.setSpeed(0.0, 0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
