package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.FeederConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.OI;
import frc.robot.subsystems.*;


public class Launch extends CommandBase {
    private final Drive drive;
    private final Feeder feeder;
    private final Intake intake;
    private final Shooter shooter;
    private final OI oi = OI.getInstance();

    private double targetSpeed;
    private final Timer timeout = new Timer();
    private final Timer feedCount = new Timer();

    public Launch(Drive drive, Feeder feeder, Intake intake, Limelight limelight, Shooter shooter) {
        this.drive = drive;
        this.feeder = feeder;
        this.intake = intake;
        this.shooter = shooter;

        addRequirements(this.drive, this.feeder, this.intake, limelight, (Subsystem) this.shooter);
    }

    @Override
    public void initialize() {
        timeout.reset();
        feedCount.reset();

        targetSpeed = shooter.getBottomSpeed();

        intake.setIntake(IntakeConstants.INTAKE_POWER);
    }

    @Override
    public void execute() {
        // If the top sensor is empty, begin timeout for command halt
        // TODO: Remove sensors from code if they are not added to robot
        feeder.setTower(FeederConstants.TOWER_POWER);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        timeout.reset();
        feedCount.reset();

        intake.setIntake(0);
        feeder.setTower(0);

        shooter.setBottomPower(0);
        shooter.setRampRate(ShooterConstants.LONG_RAMP_RATE);

        drive.setPower(0, 0);
    }
}