package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.auto.TaxiShoot;
import frc.robot.commands.collector.SetFeederPower;
import frc.robot.commands.collector.SetIntakePower;
import frc.robot.commands.drive.Flip;
import frc.robot.commands.drive.SetSpeed;
import frc.robot.commands.shooter.LightToggle;
import frc.robot.commands.shooter.SetShooterPower;
import frc.robot.commands.shooter.ShootCommandGroup;
import frc.robot.commands.shooter.StopShooter;
import frc.robot.subsystems.*;

public class RobotContainer {
    final SendableChooser<AutoRoutine> chooser = new SendableChooser<>();
    private final Drive drive = new Drive();
    private final Intake intake = new Intake();
    private final Feeder feeder = new Feeder();
    private final Shooter shooter = new Shooter();
    private final Limelight limelight = new Limelight();
    private final OI oi = OI.getInstance();

    public RobotContainer() {
        Intake intake = new Intake();
        drive.setDefaultCommand(new SetSpeed(drive));
        intake.setDefaultCommand(new SetIntakePower(intake));
        feeder.setDefaultCommand(new SetFeederPower(feeder, shooter));

        // for testing only
        shooter.setDefaultCommand(new SetShooterPower(shooter));

        chooser.addOption("Wait command (placeholder)", AutoRoutine.WaitCommand);
        chooser.addOption("3 ball auto", AutoRoutine.ThreeCargoBlue);
        chooser.addOption("2 ball autos", AutoRoutine.TwoCargoBlue);

        chooser.setDefaultOption("Wait command (placeholder)", AutoRoutine.WaitCommand);
        configureButtonBindings();

        Shuffleboard.getTab("Autonomous Selection").add(chooser);
    }

    private void configureButtonBindings() {
        new JoystickButton(oi.leftStick, Constants.OIConstants.FLIP_BUTTON).whenPressed(new Flip(drive));
        new JoystickButton(oi.rightStick, Constants.OIConstants.HALF_SPEED_BUTTON).whenPressed(() -> drive.setMaxPower(0.5)).whenReleased(() -> drive.setMaxPower(1.0));
    }

    public Command getAutonomousCommand() {
        return new TaxiShoot(feeder, shooter, drive);
    }

    enum AutoRoutine {
        WaitCommand, ThreeCargoBlue, ThreeCargoRed, TwoCargoBlue, TwoCargoRed
    }
}


