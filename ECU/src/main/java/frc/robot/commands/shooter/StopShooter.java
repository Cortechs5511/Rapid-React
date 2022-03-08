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
    private final Drive drive;
    private final Feeder feeder;
    private final Intake intake;
    private final Shooter shooter;
    private final OI oi = OI.getInstance();

    private final Timer timeout = new Timer();

    public StopShooter(Drive drive, Feeder feeder, Intake intake, Shooter shooter) {
        this.drive = drive;
        this.feeder = feeder;
        this.intake = intake;
        this.shooter = shooter;

        addRequirements(this.drive, this.feeder, this.intake, (Subsystem) this.shooter);
    }

    @Override
    public void initialize() {
        feeder.setTower(0);
        intake.setIntake(0);
        shooter.setBottomPower(0);

        timeout.reset();
        timeout.start();
    }

    @Override
    public void execute() {
        drive.setPower(oi.getLeftYDeadband(), oi.getRightYDeadband());
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return timeout.hasElapsed(Constants.DWELL_PERIOD);
    }

    @Override
    public void end(boolean interrupted) {
        drive.setPower(0, 0);
    }
}
