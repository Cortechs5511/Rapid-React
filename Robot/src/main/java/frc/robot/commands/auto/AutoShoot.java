// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Feeder;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.FeederConstants;

public class AutoShoot extends CommandBase {
  private final Shooter shooter;
  private final Feeder feeder;

  /** Creates a new AutoShoot. */
  public AutoShoot(Shooter shooter, Feeder feeder) {
    this.shooter = shooter;
    this.feeder = feeder;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter, feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.setBottomPower(AutoConstants.SHOOTER_BOTTOM_WHEEL_SPEED);
    shooter.setTopPower(AutoConstants.SHOOTER_TOP_WHEEL_SPEED);
    feeder.setTower(FeederConstants.TOWER_POWER);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.setBottomPower(0);
    shooter.setTopPower(0);
    feeder.setTower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
