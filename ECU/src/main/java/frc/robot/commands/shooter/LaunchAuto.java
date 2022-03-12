package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.ShooterConstants;
import frc.robot.OI;
import frc.robot.subsystems.*;

import edu.wpi.first.wpilibj.Timer;


public class LaunchAuto extends CommandBase {
    private final Drive drive;
    private final Shooter shooter;
    private final OI oi = OI.getInstance();

    private Timer timer = new Timer();
    private double stop;

    private double targetSpeed;
    private final Timer timeout = new Timer();
    private final Timer feedCount = new Timer();

    public LaunchAuto(Drive drive, Limelight limelight, Shooter shooter, double time) {
        this.drive = drive;
        this.shooter = shooter;
        stop = time;

        addRequirements(this.drive, limelight, (Subsystem) this.shooter);
    }

    @Override
    public void initialize() {
        timeout.reset();
        timer.reset();
        timer.start();
        feedCount.reset();

        targetSpeed = shooter.getBottomSpeed();

        shooter.setBottomPower(0.0);
        shooter.setTopPower(0.45);

        // System.out.println("Launched");
    }

    @Override
    public void execute() {
        // If the top sensor is empty, begin timeout for command halt
        // TODO: Remove sensors from code if they are not added to robot

        shooter.setBottomPower(0.35);
        shooter.setTopPower(0.65);

        // If RPM within tolerance, begin timeout for feeding
        if (Math.abs(shooter.getBottomSpeed() - targetSpeed) < ShooterConstants.SHOOTER_RPM_TOLERANCE) {
            feedCount.start();
        } else {
            feedCount.reset();
        }

        // Keep robot stationary unless priority set
        if (oi.getShooterPriority()) {
            drive.setPower(oi.getLeftYDeadband(), oi.getRightYDeadband());
        }
    }

    @Override
    public boolean isFinished() {
        return (timer.get() > this.stop);
        //return (timeout.get() > ShooterConstants.SHOOTER_TIMEOUT);
    }

    @Override
    public void end(boolean interrupted) {
        timeout.reset();
        feedCount.reset();

        shooter.setBottomPower(0);
        shooter.setRampRate(ShooterConstants.LONG_RAMP_RATE);

        drive.setPower(0, 0);
    }
}
