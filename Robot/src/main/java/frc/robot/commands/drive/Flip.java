package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drive;


public class Flip extends InstantCommand {
    private final Drive drive;

    public Flip(Drive drive) {
        this.drive = drive;

        addRequirements(this.drive);
    }

    @Override
    public void initialize() {
        drive.flip();
    }

    @Override
    public void end(boolean interrupted) {

    }
}
