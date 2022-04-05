// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.Feeder;
import edu.wpi.first.wpilibj.Timer;

public class AutoIntake extends CommandBase {
  private final Intake intake;
  private final Feeder feeder;

  final Timer feedTimer = new Timer();

  /** Creates a new AutoIntake. */
  public AutoIntake(Intake intake, Feeder feeder) {
    this.intake = intake;
    this.feeder = feeder;

    addRequirements(intake, feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    feedTimer.reset();
    feedTimer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double time = feedTimer.get();

    intake.setWrist(IntakeConstants.WRIST_POWER);
    intake.setIntake(IntakeConstants.INTAKE_POWER);
    feeder.holdTower(FeederConstants.HOLD_TOWER_POWER);

    // if (time < AutoConstants.FEED_TIME) {
    // feeder.setTower(AutoConstants.FEED_POWER);
    // } else {
    // feeder.holdTower(AutoConstants.FEED_HOLD_POWER);
    // }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.setWrist(0);
    intake.setIntake(0);
    feeder.setTower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
