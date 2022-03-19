package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;

public class TaxiShoot extends CommandBase {
    private final Feeder feeder;
    private final Shooter shooter;
    private final Drive drive;

    final Timer shootTimer = new Timer();

    public TaxiShoot(Feeder feeder, Shooter shooter, Drive drive) {
        this.feeder = feeder;
        this.shooter = shooter;
        this.drive = drive;

        addRequirements(feeder, shooter, drive);
    }

    @Override
    public void initialize() {
        shootTimer.reset();
        shootTimer.start();
    }

    @Override
    public void execute() {
        double time = shootTimer.get();

        if (time < 1.0) {
            // Speed up shooter for 1 second
            shooter.setBottomPower(ShooterConstants.BOTTOM_SHOOTER_POWER);
            shooter.setTopPower(ShooterConstants.TOP_SHOOTER_POWER);
        } else if (time < 7.0) {
            // Feed for 6 seconds
            feeder.setTower(-0.6);
        } else {
            // Stop shooter, feeder, and drive back for 3 seconds.
            feeder.setTower(0);
            shooter.setBottomPower(0.0);
            shooter.setTopPower(0.0);
            drive.setPower(DriveConstants.AUTO_POWER, DriveConstants.AUTO_POWER);
        }
    }

    @Override
    public void end(boolean interrupted) {
        drive.setPower(0, 0);
    }

    @Override
    public boolean isFinished() {
        return shootTimer.hasElapsed(10.0);
    }
}
