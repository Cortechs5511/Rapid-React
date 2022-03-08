package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.auto.TrajectoryFollower;
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
        drive.setDefaultCommand(new SetSpeed(drive));
        intake.setDefaultCommand(new SetIntakePower(intake));
        feeder.setDefaultCommand(new SetFeederPower(feeder));

        // for testing only
        shooter.setDefaultCommand(new SetShooterPower(shooter));

        chooser.addOption("Wait command (placeholder)", AutoRoutine.WaitCommand);
        chooser.addOption("Auto command (placeholder)", AutoRoutine.AutoCommand);
        chooser.addOption("3 ball auto", AutoRoutine.threeBall);
        chooser.addOption("2 ball autos", AutoRoutine.twoBall);

        chooser.setDefaultOption("Wait command (placeholder)", AutoRoutine.WaitCommand);
        configureButtonBindings();

        Shuffleboard.getTab("Autonomous Selection").add(chooser);
    }

    private void configureButtonBindings() {
        new JoystickButton(oi.controller, Constants.OIConstants.SHOOT_BUTTON).whenPressed(new ShootCommandGroup(drive, feeder, intake, limelight, shooter));
        new JoystickButton(oi.controller, Constants.OIConstants.STOP_SHOOT_BUTTON).whenReleased(new StopShooter(drive, feeder, intake, shooter));
        new JoystickButton(oi.controller, Constants.OIConstants.LIGHTS_TOGGLE_BUTTON).whenPressed(new LightToggle(limelight));

        new JoystickButton(oi.leftStick, Constants.OIConstants.FLIP_BUTTON).whenPressed(new Flip(drive));
        new JoystickButton(oi.rightStick, Constants.OIConstants.HALF_SPEED_BUTTON).whenPressed(() -> drive.setMaxPower(0.5)).whenReleased(() -> drive.setMaxPower(1.0));
    }

    public Command getAutonomousCommand(autonMode choice) {
        switch (choice) {
            case WaitCommand:
                return new WaitCommand(2.0);
            case AutoCommand:
                return new WaitCommand(2.0);
                // return new TrajectoryFollower(drive);
            case threeBall:
                return TrajectoryFollower.getPath("output/3ball1.wpilib.json", m_drive, true).andThen(stop())
                .andThen(TrajectoryFollower.getPath("output/3ball1red.wpilib.json", m_drive, true));
            case twoBall:
                return TrajectoryFollower.getPath("output/2ball1.wpilib.json", m_drive, true)
                        .andThen(TrajectoryFollower.getPath("output/2ball2.wpilib.json", m_drive, true))
                        .andThen(TrajectoryFollower.getPath("output/2ball3.wpilib.json", m_drive, true))
                        .andThen(TrajectoryFollower.getPath("output/2ball1red.wpilib.json", m_drive, true))
                        .andThen(TrajectoryFollower.getPath("output/2ball2red.wpilib.json", m_drive, true))
                        .andThen(TrajectoryFollower.getPath("output/2ball3red.wpilib.json", m_drive, true));
            default:
                return new WaitCommand(1.0);
        }
    }

    enum AutoRoutine {
        WaitCommand, AutoCommand, threeBall, twoBall
    }
}
