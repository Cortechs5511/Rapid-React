package frc.robot.commands.shooter;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.*;

public class AutoShootCommandGroup extends SequentialCommandGroup {
    public AutoShootCommandGroup(Drive drive, Limelight limelight, Shooter shooter) {
        super(
                //new LightOn(limelight),
                //new WaitCommand(ShooterConstants.DWELL_TIME),
                //new LightToggle(limelight),
                new LaunchAuto(drive, limelight, shooter, 8),
                new StopShooter(shooter)
        );
    }
}