package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.auto.TaxiShoot;
import frc.robot.commands.collector.SetFeederPower;
import frc.robot.commands.collector.SetIntakePower;
import frc.robot.commands.drive.Flip;
import frc.robot.commands.drive.SetSpeed;
import frc.robot.commands.shooter.SetShooterPower;
import frc.robot.commands.shooter.ShooterSpeedAdvisory;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
    private final Drive drive = new Drive();
    private final Feeder feeder = new Feeder();
    private final Shooter shooter = new Shooter();
    private final OI oi = OI.getInstance();

    public RobotContainer() {
        drive.setDefaultCommand(new SetSpeed(drive));
        Intake intake = new Intake();
        intake.setDefaultCommand(new SetIntakePower(intake));
        feeder.setDefaultCommand(new SetFeederPower(feeder, shooter));
        shooter.setDefaultCommand(new SetShooterPower(shooter));

        CommandScheduler.getInstance().schedule(new ShooterSpeedAdvisory(shooter));

        configureButtonBindings();
    }

    private void configureButtonBindings() {
        new JoystickButton(oi.leftStick, Constants.OIConstants.FLIP_BUTTON).whenPressed(new Flip(drive));
        new JoystickButton(oi.rightStick, Constants.OIConstants.HALF_SPEED_BUTTON).whenPressed(() -> drive.setMaxPower(0.5)).whenReleased(() -> drive.setMaxPower(1.0));
    }

    public Command getAutonomousCommand() {
        return new TaxiShoot(feeder, shooter, drive);
    }
}
