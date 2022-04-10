package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.Feeder;
import edu.wpi.first.wpilibj.Timer;

public class AutoIntake extends CommandBase {
    private final Intake intake;
    private final Feeder feeder;

    final Timer feedTimer = new Timer();

    public AutoIntake(Intake intake, Feeder feeder) {
        this.intake = intake;
        this.feeder = feeder;

        addRequirements(intake, feeder);
    }

    @Override
    public void initialize() {
        feedTimer.reset();
        feedTimer.start();
    }

    @Override
    public void execute() {
        intake.setWrist(IntakeConstants.WRIST_POWER);
        intake.setIntake(IntakeConstants.INTAKE_POWER);
        feeder.holdTower(FeederConstants.HOLD_TOWER_POWER);
    }

    @Override
    public void end(boolean interrupted) {
        intake.setWrist(0);
        intake.setIntake(0);
        feeder.setTower(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
