package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.ShooterConstants;
import frc.robot.OI;
import frc.robot.subsystems.*;


public class Launch extends CommandBase {
    private final Shooter shooter;
    private final OI oi = OI.getInstance();

    private double targetSpeed;
    private final Timer timeout = new Timer();
    private final Timer feedCount = new Timer();

    public Launch(Shooter shooter) {
        this.shooter = shooter;

        addRequirements(this.shooter);
    }

    @Override
    public void initialize() {
        timeout.reset();
        feedCount.reset();

        targetSpeed = shooter.getBottomSpeed();

        shooter.setBottomPower(shooter.bottomSpeedChanging);
        shooter.setTopPower(shooter.topSpeedChanging);

        // System.out.println("Launched");
    }

    @Override
    public void execute() {
        // If the top sensor is empty, begin timeout for command halt
        // TODO: Remove sensors from code if they are not added to robot

        shooter.setBottomPower(shooter.bottomSpeedChanging);
        shooter.setTopPower(shooter.topSpeedChanging);

        // If RPM within tolerance, begin timeout for feeding
        if (Math.abs(shooter.getBottomSpeed() - targetSpeed) < ShooterConstants.SHOOTER_RPM_TOLERANCE) {
            feedCount.start();
        } else {
            feedCount.reset();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
        //return (timeout.get() > ShooterConstants.SHOOTER_TIMEOUT);
    }

    @Override
    public void end(boolean interrupted) {
        timeout.reset();
        feedCount.reset();

        shooter.setBottomPower(0);
        shooter.setRampRate(ShooterConstants.LONG_RAMP_RATE);
    }
}
