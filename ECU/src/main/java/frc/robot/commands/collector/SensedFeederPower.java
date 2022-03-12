package frc.robot.commands.collector;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Feeder;

public class SensedFeederPower extends CommandBase {
    private final Feeder feeder;
    private final OI oi = OI.getInstance();

    public SensedFeederPower(Feeder feeder) {
        this.feeder = feeder;
        addRequirements(this.feeder);
    }

    @Override
    public void initialize() {
        feeder.setTower(0);
    }

    @Override
    public void execute() {
        // SmartDashboard.putBoolean("Feeder/Sensor", feeder.getSensor())
        // put sensor in here
        // if (feeder.getSensor()) {
            // make it work during shooting
            // if oi.
            // feeder.setTower(0);
        // }
        // feeder.setTower((oi.getFeederUp() - oi.getFeederDown()) * FeederConstants.TOWER_POWER);
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
