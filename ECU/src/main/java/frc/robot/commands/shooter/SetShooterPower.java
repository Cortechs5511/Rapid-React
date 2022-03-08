package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Shooter;

public class SetShooterPower extends CommandBase {
    Shooter shooter;

    public SetShooterPower(Shooter shooter) {
        this.shooter = shooter;
        addRequirements((Subsystem) this.shooter);
    }

    @Override
    public void initialize() {
        shooter.setBottomPower(0);

        SmartDashboard.putNumber("Shooter/Bottom Shooter Power", 0.0);
        SmartDashboard.putNumber("Shooter/Top Shooter Power", 0.0);
    }

    @Override
    public void execute() {
        shooter.setBottomPower(SmartDashboard.getNumber("Shooter/Bottom Shooter Power", 0.0));
        shooter.setTopPower(SmartDashboard.getNumber("Shooter/Top Shooter Power", 0.0));
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setBottomPower(0);
        shooter.setTopPower(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}