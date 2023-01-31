package frc.robot.commands.drive;

import frc.robot.OI;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetSpeed extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Drive drive;
    private final OI oi = OI.getInstance();

    public SetSpeed(Drive subsystem) {
        drive = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double moveSpeed = oi.getLeftYDeadband();
        double rotateSpeed = oi.getRightXDeadband();
        drive.arcadeDrive(moveSpeed, rotateSpeed);
       //drive.setPower(oi.getLeftYDeadband(), oi.getRightYDeadband());
    }

    @Override
    public void end(boolean interrupted) {
        drive.setPower(0.0, 0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
