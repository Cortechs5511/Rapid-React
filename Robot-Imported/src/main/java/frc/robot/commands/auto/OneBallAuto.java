package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;

public class OneBallAuto extends CommandBase {
    private final Feeder feeder;
    private final Shooter shooter;
    private final Drive drive;

    final Timer shootTimer = new Timer();

    public OneBallAuto(Feeder feeder, Shooter shooter, Drive drive) {
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

        if (time < AutoConstants.TAXISHOOT_SHOOTER_WINDUP_TIME) {
            shooter.setBottomPower(ShooterConstants.BOTTOM_SHOOTER_POWER);
            shooter.setTopPower(ShooterConstants.TOP_SHOOTER_POWER);
        } else if (time < AutoConstants.TAXISHOOT_FEED_TIME + AutoConstants.TAXISHOOT_SHOOTER_WINDUP_TIME) {
            feeder.setTower(0.6);
        } else {
            feeder.setTower(0);
            shooter.setBottomPower(0.0);
            shooter.setTopPower(0.0);
            drive.setPower(AutoConstants.AUTO_POWER, AutoConstants.AUTO_POWER);
        }
    }

    @Override
    public void end(boolean interrupted) {
        drive.setPower(0, 0);
    }

    @Override
    public boolean isFinished() {
        return shootTimer.hasElapsed(AutoConstants.AUTO_TIMEOUT);
    }
}
