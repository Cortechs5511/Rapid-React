package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.OI;
import frc.robot.subsystems.Drive;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class AutoAlign extends CommandBase {
    private final Drive drive;
    private final OI oi = OI.getInstance();
    private PIDController levelPID = new PIDController(100,0,0);
    private boolean enabled = true;

    public AutoAlign(Drive subsystem) {
        drive = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        levelPID.setSetpoint(0);
    }

    @Override
    public void execute() {
        if (enabled) {
            double angle = drive.getPitch();
            double output = levelPID.calculate(angle);

            if (Math.abs(angle) < 4) {
                drive.setPower(0, 0);
            } else {
                output = Math.max(-0.3, Math.min(0.3, output));
                drive.setPower(output, output);
            }
        } else {
            drive.setPower(0.0, 0.0);
        }

        if (oi.controller.getXButton()) {
            enabled = true;
            SmartDashboard.putString("AutoAlign Activated", "Activated");
        } else if (oi.controller.getBButton()) {
            enabled = false;
            SmartDashboard.putString("AutoAlign Activated", "Not Activated");
        }
    }

    @Override
    public void end(boolean interrupted) {
        drive.setPower(0.0, 0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
