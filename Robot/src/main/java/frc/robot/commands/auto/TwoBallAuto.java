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

public class TwoBallAuto extends CommandBase {
    private final Intake intake;
    private final Feeder feeder;
    private final Shooter shooter;
    private final Drive drive;
    private final Limelight limelight;

    final Timer shootTimer = new Timer();

    public TwoBallAuto(Intake intake, Feeder feeder, Shooter shooter, Drive drive, Limelight limelight) {
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
            // Drive back, put down wrist
            drive.setPower(AutoConstants.TWOBALL_DRIVE_POWER, AutoConstants.TWOBALL_DRIVE_POWER);
            intake.setWrist(-1 * IntakeConstants.WRIST_POWER);
        } else if (time < AutoConstants.TWOBALL_AUTO_DRIVE_TIME) {
            // Stop wrist, continue driving and intaking, and update shooter speed
            intake.setWrist(0);
            drive.setPower(AutoConstants.TWOBALL_DRIVE_POWER, AutoConstants.TWOBALL_DRIVE_POWER);
            intake.setIntake(IntakeConstants.INTAKE_POWER);
            shooter.setBottomPower(ShooterConstants.BOTTOM_SHOOTER_POWER_2);
            shooter.setTopPower(ShooterConstants.TOP_SHOOTER_POWER_2);
        } else if (time < AutoConstants.TWOBALL_INTAKE_TIME) {
            // Stop driving, keep intaking
            drive.setPower(0, 0);
        } else if (time < AutoConstants.TWOBALL_JERK_FORWARD_TIME) {
            // Bump the robot a little bit
            drive.setPower(-0.65, -0.65);
        } else {
            drive.setPower(0, 0);
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

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
