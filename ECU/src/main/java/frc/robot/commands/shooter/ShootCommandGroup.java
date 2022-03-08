package frc.robot.commands.shooter;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.*;

public class ShootCommandGroup extends SequentialCommandGroup {
    public ShootCommandGroup(Drive drive, Feeder feeder, Intake intake, Limelight limelight, FalconShooter shooter) {
        super(
                new LightOn(limelight),
                new WaitCommand(ShooterConstants.DWELL_TIME),
                new Align(drive, limelight, shooter),
                new LightToggle(limelight),
                new Launch(drive, feeder, intake, limelight, shooter),
                new StopShooter(drive, feeder, intake, shooter)
        );
    }
}