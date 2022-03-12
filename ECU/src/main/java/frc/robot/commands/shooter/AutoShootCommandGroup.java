package frc.robot.commands.shooter;


import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.collector.AutoFeed;
import frc.robot.subsystems.*;

public class AutoShootCommandGroup extends SequentialCommandGroup {
    public AutoShootCommandGroup(Drive drive, Feeder feeder, Shooter shooter) {
        super(
                //new LightOn(limelight),
                //new WaitCommand(ShooterConstants.DWELL_TIME),
                //new LightToggle(limelight),
                new RunCommand(() -> shooter.setBottomPower(0.45)),
                new WaitCommand(1.0),
                new AutoFeed(feeder),
                new RunCommand(() -> feeder.setTower(0.0)),
                new StopShooter(shooter)
        );
    }
}