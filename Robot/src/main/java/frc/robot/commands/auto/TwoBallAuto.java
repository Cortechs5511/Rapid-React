package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake;

public class TwoBallAuto extends CommandBase {
  private final Intake intake;
  private final Feeder feeder;
  private final Shooter shooter;
  private final Drive drive;

  final Timer shootTimer = new Timer();

  /** Creates a new TwoBallAuto. */
  public TwoBallAuto(Intake intake, Feeder feeder, Shooter shooter, Drive drive) {
    this.intake = intake;
    this.feeder = feeder;
    this.shooter = shooter;
    this.drive = drive;

    addRequirements(intake, feeder, shooter, drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shootTimer.reset();
    shootTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double time = shootTimer.get();
    // TO DO: FIX CONSTANTS FOR TIME
    if (time < AutoConstants.TAXISHOOT_SHOOTER_WINDUP_TIME) {
      // Accelerate shooter
      shooter.setBottomPower(ShooterConstants.BOTTOM_SHOOTER_POWER);
      shooter.setTopPower(ShooterConstants.TOP_SHOOTER_POWER);
    } else if (time < AutoConstants.TAXISHOOT_FEED_TIME) {
      // Feed
      feeder.setTower(0.6);
    } else if (time < AutoConstants.TWOBALL_AUTO_DRIVE_TIME) {
      // Drive back
      drive.setPower(AutoConstants.TWOBALL_DRIVE_POWER, AutoConstants.TWOBALL_DRIVE_POWER);
    } else if (time < AutoConstants.TWOBALL_INTAKE_TIME) {
      // Stop driving and intake
      drive.setPower(0, 0);
      intake.setWrist(IntakeConstants.WRIST_POWER);
      intake.setIntake(IntakeConstants.INTAKE_POWER);
    } else if (time < AutoConstants.TWOBALL_WRIST_TIME) {
      // Wrist up
      intake.setWrist(-1 * IntakeConstants.WRIST_DOWN_POWER);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.setPower(0, 0);
    intake.setWrist(0);
    intake.setIntake(0);
    feeder.setTower(0);
    shooter.setBottomPower(0);
    shooter.setTopPower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return shootTimer.hasElapsed(AutoConstants.AUTO_TIMEOUT);
  }
}
