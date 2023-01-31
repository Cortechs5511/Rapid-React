package frc.robot.commands.shooter;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.OI;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;


public class Align extends CommandBase {
    private final Drive drive;
    private final Limelight limelight;
    private final Shooter shooter;
    private final OI oi = OI.getInstance();

    private final PIDController anglePID = new PIDController(DriveConstants.ANGLE_P, DriveConstants.ANGLE_I, DriveConstants.ANGLE_D);
    private final Timer count = new Timer();
    private double targetSpeed;

    public Align(Drive drive, Limelight limelight, Shooter shooter) {
        this.drive = drive;
        this.limelight = limelight;
        this.shooter = shooter;

        addRequirements(this.drive, this.limelight, (Subsystem) this.shooter);
    }

    @Override
    public void initialize() {
        shooter.setRampRate(ShooterConstants.LONG_RAMP_RATE);

        count.reset();
    }

    @Override
    public void execute() {
        double x = limelight.getX();
        targetSpeed = limelight.getBottomPower();

        if (!oi.getShooterPriority()) {
            double setpoint = 0.0;
            double output = Math.max(-DriveConstants.ANGLE_PID_LIMIT, Math.min(DriveConstants.ANGLE_PID_LIMIT, anglePID.calculate(x, setpoint)));
            drive.setPower(output, -output);
        } else {
            drive.setPower(oi.getLeftYDeadband() / 3, oi.getRightYDeadband() / 3);
        }

        if (Math.abs(targetSpeed - shooter.getBottomSpeed()) < ShooterConstants.SHOOTER_RPM_TOLERANCE) {
            count.start();
        } else {
            count.reset();
        }
    }

    @Override
    public boolean isFinished() {
        return (count.hasElapsed(ShooterConstants.DWELL_TIME) && Math.abs(targetSpeed - shooter.getBottomSpeed()) < ShooterConstants.SHOOTER_RPM_TOLERANCE && Math.abs(drive.getLeftVelocity()) < DriveConstants.DRIVE_VELOCITY_TOLERANCE && Math.abs(drive.getRightVelocity()) < DriveConstants.DRIVE_VELOCITY_TOLERANCE);
    }

    @Override
    public void end(boolean interrupted) {
        drive.setPower(0, 0);
        shooter.setBottomSpeed(limelight.getBottomPower());
        count.reset();
    }
}