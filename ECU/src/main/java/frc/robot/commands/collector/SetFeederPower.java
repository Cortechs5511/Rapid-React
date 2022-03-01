package frc.robot.commands.collector;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.FeederConstants;
import frc.robot.subsystems.Feeder;


public class SetFeederPower extends CommandBase {
    private final Feeder feeder;

    public SetFeederPower(Feeder feeder) {
        this.feeder = feeder;
        addRequirements(this.feeder);
    }

    @Override
    public void initialize() {
        feeder.setFeeder(0);
        feeder.setTower(0);
    }

    @Override
    public void execute() {
        boolean bottomSensor = feeder.getBottomSensor();
        boolean topSensor = feeder.getTopSensor();

        double feederPower = 0;
        double towerPower = 0;

        // Index cargo based off of progress in feeder
        if (topSensor) {
            feederPower = 0;
            towerPower = 0;
        } else if (bottomSensor) {
            feederPower = FeederConstants.FEEDER_POWER;
            towerPower = FeederConstants.TOWER_POWER;
        }

        feeder.setFeeder(feederPower);
        feeder.setTower(towerPower);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        feeder.setFeeder(0);
        feeder.setTower(0);
    }
}
