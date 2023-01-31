package frc.robot.commands.limelight;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;

public class LimelightDisplay extends CommandBase {
    private final Limelight limelight;
    public LimelightDisplay(Limelight limelight) {
        this.limelight = limelight;
        addRequirements(this.limelight);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("Limelight/Limelight X", limelight.getX());
        SmartDashboard.putNumber("Limelight/Limelight Y", limelight.getY());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
