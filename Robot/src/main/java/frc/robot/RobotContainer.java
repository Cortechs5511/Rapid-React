package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.auto.TaxiShoot;
import frc.robot.commands.auto.TrajectoryFollower;
import frc.robot.commands.collector.SetFeederPower;
import frc.robot.commands.collector.SetIntakePower;
import frc.robot.commands.drive.Flip;
import frc.robot.commands.drive.SetSpeed;
import frc.robot.commands.SetClimberPower;
import frc.robot.commands.limelight.LightToggle;
import frc.robot.commands.limelight.LimelightDisplay;
import frc.robot.commands.shooter.SetShooterPower;
import frc.robot.subsystems.*;
import frc.robot.commands.auto.TwoBallAuto;

public class RobotContainer {
    private SendableChooser<AutoRoutine> chooser = new SendableChooser<>();

    private final Drive drive = new Drive();
    private final Intake intake = new Intake();
    private final Feeder feeder = new Feeder();
    private final Shooter shooter = new Shooter();
    private final Limelight limelight = new Limelight();
    private final Climber climber = new Climber();
    private final OI oi = OI.getInstance();

    enum AutoRoutine {
        WaitCommand, TwoBallAuto, OneBallAuto
    }

    public RobotContainer() {
        drive.setDefaultCommand(new SetSpeed(drive));
        intake.setDefaultCommand(new SetIntakePower(intake));
        feeder.setDefaultCommand(new SetFeederPower(feeder));
        limelight.setDefaultCommand(new LimelightDisplay(limelight));
        climber.setDefaultCommand(new SetClimberPower(climber));
        shooter.setDefaultCommand(new SetShooterPower(shooter, limelight));

        chooser.addOption("Wait command (placeholder)", AutoRoutine.WaitCommand);
        chooser.addOption("Simple 2 ball auto", AutoRoutine.TwoBallAuto);
        chooser.addOption("1 ball auto", AutoRoutine.OneBallAuto);

        chooser.setDefaultOption("Simple 2 ball auto", AutoRoutine.TwoBallAuto);
        configureButtonBindings();

        Shuffleboard.getTab("Autonomous Selection").add(chooser);
    }

    private void configureButtonBindings() {
        new JoystickButton(oi.leftStick, Constants.OIConstants.FLIP_BUTTON).whenPressed(new Flip(drive));
        new JoystickButton(oi.rightStick, Constants.OIConstants.HALF_SPEED_BUTTON)
                .whenPressed(() -> drive.setMaxPower(0.5)).whenReleased(() -> drive.setMaxPower(1.0));

        new JoystickButton(oi.controller, Constants.OIConstants.LIMELIGHT_TOGGLE_BUTTON).whenPressed(new LightToggle(limelight));
    }

    public Command getAutonomousCommand() {
        AutoRoutine choice = chooser.getSelected();
        Command selected;

        switch (choice) {
            case WaitCommand:
                selected = new WaitCommand(1.0);
                break;
            case TwoBallAuto:
                selected = new TwoBallAuto(intake, feeder, shooter, drive);
                break;
            case OneBallAuto:
                selected = new TaxiShoot(feeder, shooter, drive);
                break;
            default:
                selected = new TwoBallAuto(intake, feeder, shooter, drive);
                break;
        }
        return selected;
    }
}
