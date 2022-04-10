package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;

public class TwoBallAutoAlt extends CommandBase {
    private final Intake intake;
    private final Feeder feeder;
    private final Shooter shooter;
    private final Drive drive;
    private final Limelight limelight;

    final Timer shootTimer = new Timer();

    public TwoBallAutoAlt(Intake intake, Feeder feeder, Shooter shooter, Drive drive, Limelight limelight) {
        this.intake = intake;
        this.feeder = feeder;
        this.shooter = shooter;
        this.drive = drive;
        this.limelight = limelight;

        addRequirements(intake, feeder, shooter, drive);
    }

    @Override
    public void initialize() {
        shootTimer.reset();
        shootTimer.start();

        limelight.setLight(false);
    }

    @Override
    public void execute() {
        double time = shootTimer.get();

        if (time < AutoConstants.TAXISHOOT_SHOOTER_WINDUP_TIME) {
            // Accelerate shooter
            shooter.setBottomPower(ShooterConstants.BOTTOM_SHOOTER_POWER);
            shooter.setTopPower(ShooterConstants.TOP_SHOOTER_POWER);
        } else if (time < AutoConstants.TAXISHOOT_FEED_TIME) {
            // Feed
            feeder.setTower(FeederConstants.TOWER_POWER);
        } else if (time < AutoConstants.TWOBALL_WRIST_DOWN_TIME) {
            // Drive back, put down wrist, turn on lights, stop feeding
            drive.setPower(AutoConstants.TWOBALL_DRIVE_POWER, AutoConstants.TWOBALL_DRIVE_POWER);
            intake.setWrist(-1 * IntakeConstants.WRIST_POWER);
            feeder.holdTower(FeederConstants.HOLD_TOWER_POWER);
        } else if (time < AutoConstants.TWOBALL_AUTO_DRIVE_TIME) {
            // Stop wrist, keep driving keep intaking
            intake.setWrist(0);
            drive.setPower(AutoConstants.TWOBALL_DRIVE_POWER, AutoConstants.TWOBALL_DRIVE_POWER);
            intake.setIntake(IntakeConstants.INTAKE_POWER);
        } else if (time < AutoConstants.TWOBALL_INTAKE_TIME) {
            // Stop driving, keep intaking
            drive.setPower(0, 0);
        } else if (time < AutoConstants.TWOBALL_DRIVE_BACK_TIME) {
            drive.setPower(-AutoConstants.TWOBALL_DRIVE_POWER, -AutoConstants.TWOBALL_DRIVE_POWER);
        } else {
            // Stop and shoot
            drive.setPower(0, 0);
            feeder.setTower(FeederConstants.TOWER_POWER);
        }
    }

    @Override
    public void end(boolean interrupted) {
        drive.setPower(0, 0);
        intake.setWrist(0);
        intake.setIntake(0);
        feeder.setTower(0);
        shooter.setBottomPower(0);
        shooter.setTopPower(0);
        limelight.setLight(false);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
