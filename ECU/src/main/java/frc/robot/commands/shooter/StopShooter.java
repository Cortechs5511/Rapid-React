package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.subsystems.*;

/**
 * The goal of this is to immediately cancel the ejection of any game pieces, and to give back control to the driver
 * without any delay. The shooter, feeder, and intake are all stopped immediately in open loop modes when applicable
 * and operator inputs are handed to the drivetrain in a degraded mode.
 */
public class StopShooter extends CommandBase {
    private final Shooter shooter;
    // private final Limelight limelight;
    private final OI oi = OI.getInstance();

    private final Timer timeout = new Timer();

    public StopShooter(Shooter shooter) {
        // this.limelight = limelight;
        this.shooter = shooter;

        // addRequirements(this.limelight, (Subsystem) this.shooter);
        addRequirements((Subsystem) this.shooter);
    }

    @Override
    public void initialize() {

        timeout.reset();
        timeout.start();
        // if(limelight.getLightStatus()) {
        //     limelight.setLight(false);
        // }
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return timeout.hasElapsed(Constants.DWELL_PERIOD);
    }

    @Override
    public void end(boolean interrupted) {
    }
}
