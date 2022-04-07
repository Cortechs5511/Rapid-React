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

public class TwoBallAutoEncoder extends CommandBase {
  private final Intake intake;
  private final Feeder feeder;
  private final Shooter shooter;
  private final Drive drive;

  final Timer shootTimer = new Timer();

  /** Creates a new TwoBallAuto. */
  public TwoBallAutoEncoder(Intake intake, Feeder feeder, Shooter shooter, Drive drive) {
    this.intake = intake;
    this.feeder = feeder;
    this.shooter = shooter;
    this.drive = drive;

    addRequirements(intake, feeder, shooter, drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
   drive.resetOdometry();
   int stage = 1;
   boolean wristDown = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(stage==1){
        if(intake.getWristCurrent()>AutoConstants.WRIST_CURRENT_LIMIT && !wristDown){
            intake.setWrist(IntakeConstants.WRIST_POWER);
        }else if(wristDown){
            intake.setWrist(0);
            wristDown = true;
            intake.setIntake(IntakeConstants.INTAKE_POWER);
        }else if(drive.getLeftPosition()<AutoConstants.DRIVE_BACK_DISTANCE && drive.getRightPosition()<AutoConstants.DRIVE_BACK_DISTANCE){
            drive.setPower(AutoConstants.TWOBALL_DRIVE_POWER, AutoConstants.TWOBALL_DRIVE_POWER);
        }else{
            drive.setPower(0, 0);
            drive.resetOdometry();
            stage++;
        }
    }else if(stage==2){
        if(drive.getLeftPosition()<AutoConstants.NUDGE_DISTANCE && drive.getRightPosition()<AutoConstants.NUDGE_DISTANCE){
            drive.setPower(-1*AutoConstants.TWOBALL_DRIVE_POWER, -1*AutoConstants.TWOBALL_DRIVE_POWER);
        }else{
            drive.setPower(0, 0);
            drive.resetOdometry();
            stage++;
        }
    }else if(stage==3){
        shooter.setBottomPower(ShooterConstants.BOTTOM_SHOOTER_POWER);
        shooter.setTopPower(ShooterConstants.TOP_SHOOTER_POWER);
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
