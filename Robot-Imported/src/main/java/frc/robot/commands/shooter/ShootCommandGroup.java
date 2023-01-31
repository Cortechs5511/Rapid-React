package frc.robot.commands.shooter;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.limelight.LightOn;
import frc.robot.subsystems.*;

public class ShootCommandGroup extends SequentialCommandGroup {
    public ShootCommandGroup(Drive drive, Feeder feeder, Intake intake, Limelight limelight, Shooter shooter) {
        super(
                new LightOn(limelight),
                new WaitCommand(ShooterConstants.DWELL_TIME),
                new Launch(drive, feeder, intake, limelight, shooter)
        );
    }
}