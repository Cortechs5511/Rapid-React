package frc.robot.commands.collector;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.IntakeConstants;
import frc.robot.OI;
import frc.robot.subsystems.Intake;


public class SetIntakePower extends CommandBase {
    private final Intake intake;
    private final OI oi = OI.getInstance();

    public SetIntakePower(Intake intake) {
        this.intake = intake;
        addRequirements(this.intake);
    }

    @Override
    public void initialize() {
        intake.setIntake(0);
        intake.setWrist(0);
    }

    @Override
    public void execute() {
        intake.setIntake((oi.getIntake() - oi.getOuttake()) * IntakeConstants.INTAKE_POWER);
        
        if (intake.getIntakeCurrent() < IntakeConstants.WRIST_BUMP_THRESHOLD) {
            intake.setWrist((oi.getWristUp() - oi.getWristDown()) * IntakeConstants.WRIST_POWER + IntakeConstants.WRIST_DOWN_POWER);
        } else {
            intake.setWrist((oi.getWristUp() - oi.getWristDown()) * IntakeConstants.WRIST_POWER);
        }
        if (intake.getWristCurrent() > IntakeConstants.WRIST_CURRENT_LIMIT) { 
            intake.setWrist(0);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        intake.setIntake(0);
        intake.setWrist(0);
    }
}
