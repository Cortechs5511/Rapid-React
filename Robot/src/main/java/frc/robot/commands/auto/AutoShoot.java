package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Feeder;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.FeederConstants;

public class AutoShoot extends CommandBase {
    private final Shooter shooter;
    private final Feeder feeder;

    public AutoShoot(Shooter shooter, Feeder feeder) {
        this.shooter = shooter;
        this.feeder = feeder;
        addRequirements(shooter, feeder);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        shooter.setBottomPower(AutoConstants.SHOOTER_BOTTOM_WHEEL_SPEED);
        shooter.setTopPower(AutoConstants.SHOOTER_TOP_WHEEL_SPEED);
        feeder.setTower(FeederConstants.TOWER_POWER);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setBottomPower(0);
        shooter.setTopPower(0);
        feeder.setTower(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
