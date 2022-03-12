// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.collector;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Feeder;

public class AutoFeed extends CommandBase {
  private final Feeder feeder;
  int count = 0;
  /** Creates a new AutoFeed. */
  public AutoFeed(Feeder feeder) {
    this.feeder = feeder;

    addRequirements(feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    feeder.setTower(0.6);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    feeder.setTower(0.6);
    count++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    feeder.setTower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (count > (50 * 6));
  }
}
