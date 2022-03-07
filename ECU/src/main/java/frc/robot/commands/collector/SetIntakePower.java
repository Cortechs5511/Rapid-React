package frc.robot.commands.collector;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        if (!oi.getOuttake()) {
            intake.setIntake(oi.getIntake() * IntakeConstants.INTAKE_POWER);
            intake.setWrist((oi.getWristUp() - oi.getWristDown()) * IntakeConstants.WRIST_POWER - oi.getIntake() * IntakeConstants.WRIST_DOWN_POWER);
        } else {
            intake.setIntake(IntakeConstants.INTAKE_POWER);
            intake.setWrist((oi.getWristUp() - oi.getWristDown()) * IntakeConstants.WRIST_POWER);
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
