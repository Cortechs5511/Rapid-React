package frc.robot.commands.shooter;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.*;

public class ShootCommandGroup extends SequentialCommandGroup {
    public ShootCommandGroup(Feeder feeder, Intake intake, Shooter shooter) {
        super(
                //new LightOn(limelight),
                //new WaitCommand(ShooterConstants.DWELL_TIME),
                //new LightToggle(limelight),
                new Launch(shooter),
                new StopShooter(shooter)
        );
    }
}