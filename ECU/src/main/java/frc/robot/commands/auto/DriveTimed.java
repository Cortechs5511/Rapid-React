package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class DriveTimed extends CommandBase {
    private final Timer timer = new Timer();
    private final Drive m_drive;
    private final double stop;
    private final double speed;

    public DriveTimed(Drive drive, double time, double power) {
        stop = time;
        speed = power;
        m_drive = drive;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();

        m_drive.setPower(speed, speed);
    }

    @Override
    public void execute() {
        m_drive.setPower(speed, speed);
    }

    @Override
    public void end(boolean interrupted) {
        m_drive.setPower(0, 0);
    }

    @Override
    public boolean isFinished() {
        return (timer.get() > stop);
    }
}
