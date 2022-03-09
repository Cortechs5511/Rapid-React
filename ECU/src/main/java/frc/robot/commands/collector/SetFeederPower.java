package frc.robot.commands.collector;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Constants.FeederConstants;
import frc.robot.subsystems.Feeder;


public class SetFeederPower extends CommandBase {
    private final Feeder feeder;
    private final OI oi = OI.getInstance();

    public SetFeederPower(Feeder feeder) {
        this.feeder = feeder;
        addRequirements(this.feeder);
    }

    @Override
    public void initialize() {
        feeder.setTower(0);
    }

    @Override
    public void execute() {
        boolean bottomSensor = feeder.getBottomSensor();
        boolean topSensor = feeder.getTopSensor();

        double towerPower = 0;

        // Index cargo based off of progress in feeder
        if (topSensor) {
            towerPower = 0;
        } else if (bottomSensor) {
            towerPower = FeederConstants.TOWER_POWER;
        }

        if (oi.getIntake()==1) {
            feeder.setTower(-FeederConstants.TOWER_POWER);
        } else if(oi.getOuttake()==1){
            feeder.setTower(FeederConstants.TOWER_POWER);
        } else {
            feeder.setTower(towerPower);
        }
    
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        feeder.setTower(0);
    }
}
