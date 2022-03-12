package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Shooter;

public class LowerShooterSpeed extends CommandBase {
    Shooter shooter;
    public int tries = 0;

    public LowerShooterSpeed(Shooter shooter) {
        this.shooter = shooter;
        addRequirements((Subsystem) this.shooter);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (tries == 0) {
            shooter.lowerShooterSpeed();
            tries += 1;
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
