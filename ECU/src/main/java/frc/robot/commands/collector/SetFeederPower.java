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
        if (oi.getIntake() > 0.5) {
            feeder.holdTower(FeederConstants.HOLD_TOWER_POWER);
        } else {
            feeder.setTower((oi.getFeederUp() - oi.getFeederDown()) * FeederConstants.TOWER_POWER);
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
