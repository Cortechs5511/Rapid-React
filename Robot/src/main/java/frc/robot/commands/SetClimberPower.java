package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Constants.ClimberConstants;
import frc.robot.subsystems.Climber;

public class SetClimberPower extends CommandBase {
    private final Climber climber;
    private final OI oi = OI.getInstance();

    public SetClimberPower(Climber climber) {
        this.climber = climber;

        addRequirements(climber);
    }

    @Override
    public void initialize() {
        climber.setLeftClimberPower(0);
        climber.setRightClimberPower(0);

        SmartDashboard.putNumber("Debug/Climber Power", 0.3);
    }

    @Override
    public void execute() {

        climber.setLeftClimberPower(oi.getLeftClimberPower() * ClimberConstants.CLIMB_POWER);
        climber.setRightClimberPower(oi.getRightClimberPower() * ClimberConstants.CLIMB_POWER);
    }

    @Override
    public void end(boolean interrupted) {
        climber.setLeftClimberPower(0);
        climber.setRightClimberPower(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
