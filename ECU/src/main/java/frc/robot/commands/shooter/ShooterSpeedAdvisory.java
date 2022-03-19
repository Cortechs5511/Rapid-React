package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

public class ShooterSpeedAdvisory extends CommandBase {
    private final Shooter shooter;
    private final OI oi = OI.getInstance();

    double speed = 0;
    double lastSpeed = 0;
    
    public ShooterSpeedAdvisory(Shooter shooter) {
        this.shooter = shooter;
    }

    @Override
    public void initialize() {
        speed = shooter.getBottomSpeed();

        // Time based advisory -- if equilibrium is reached while shooting, disable rumble 
        if (shooter.getShooting() && Math.abs(speed - lastSpeed) < ShooterConstants.ALERT_THRESHOLD) {
            oi.controller.setRumble(RumbleType.kLeftRumble, 0.5);
            oi.controller.setRumble(RumbleType.kRightRumble, 0.5);    
        } else {
            oi.controller.setRumble(RumbleType.kLeftRumble, 0);
            oi.controller.setRumble(RumbleType.kRightRumble, 0);    
        }
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        oi.controller.setRumble(RumbleType.kLeftRumble, 0);
        oi.controller.setRumble(RumbleType.kRightRumble, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
