package frc.robot;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.collector.Eject;
import frc.robot.commands.collector.SetFeederPower;
import frc.robot.commands.collector.SetIntakePower;
import frc.robot.commands.drive.Flip;
import frc.robot.commands.drive.SetSpeed;
import frc.robot.commands.shooter.LightToggle;
import frc.robot.commands.shooter.ShootCommandGroup;
import frc.robot.commands.shooter.StopShooter;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class RobotContainer {
    private final Drive drive = new Drive();
    private final Intake intake = new Intake();
    private final Feeder feeder = new Feeder();
    private final Shooter shooter = new Shooter();
    private final Limelight limelight = new Limelight();
    private final OI oi = OI.getInstance();

    enum AutoRoutine {
        WaitCommand, AutoCommand
    }

    final SendableChooser<AutoRoutine> chooser = new SendableChooser<>();

    public RobotContainer() {
        drive.setDefaultCommand(new SetSpeed(drive));
        intake.setDefaultCommand(new SetIntakePower(intake));
        feeder.setDefaultCommand(new SetFeederPower(feeder));

        chooser.addOption("Wait command (placeholder)", AutoRoutine.WaitCommand);
        chooser.addOption("Auto command (placeholder)", AutoRoutine.AutoCommand);

        chooser.setDefaultOption("Wait command (placeholder)", AutoRoutine.WaitCommand);
        configureButtonBindings();

        Shuffleboard.getTab("Autonomous Selection").add(chooser);
    }

    private void configureButtonBindings() {
        new JoystickButton(oi.controller, Constants.OIConstants.OUTTAKE_BUTTON).whenPressed(new Eject(feeder, intake));
        new JoystickButton(oi.controller, Constants.OIConstants.SHOOT_BUTTON).whenPressed(new ShootCommandGroup(drive, feeder, intake, limelight, shooter));
        new JoystickButton(oi.controller, Constants.OIConstants.STOP_SHOOT_BUTTON).whenReleased(new StopShooter(drive, feeder, intake, shooter));
        new JoystickButton(oi.controller, Constants.OIConstants.LIGHTS_TOGGLE_BUTTON).whenPressed(new LightToggle(limelight));

        new JoystickButton(oi.leftStick, Constants.OIConstants.FLIP_BUTTON).whenPressed(new Flip(drive));
    }

    public Command getAutonomousCommand() {
        return new WaitCommand(1.0);
    }
}
