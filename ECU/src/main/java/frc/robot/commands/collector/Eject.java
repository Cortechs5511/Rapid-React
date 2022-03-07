package frc.robot.commands.collector;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;


public class Eject extends CommandBase {
    private final Feeder feeder;
    private final Intake intake;

    private final Timer timeout = new Timer();

    public Eject(Feeder feeder, Intake intake) {
        this.feeder = feeder;
        this.intake = intake;

        addRequirements(this.feeder, this.intake);
    }

    @Override
    public void initialize() {
        feeder.setTower(-FeederConstants.TOWER_POWER);
        intake.setIntake(-IntakeConstants.INTAKE_POWER);

        timeout.reset();
        timeout.start();
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return timeout.hasElapsed(FeederConstants.EJECT_TIMEOUT);
    }

    @Override
    public void end(boolean interrupted) {
        feeder.setTower(0.0);
        intake.setIntake(0.0);
    }
}
