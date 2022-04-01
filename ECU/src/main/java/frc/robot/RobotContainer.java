package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.auto.TaxiShoot;
import frc.robot.commands.auto.TrajectoryFollower;
import frc.robot.commands.collector.SetFeederPower;
import frc.robot.commands.collector.SetIntakePower;
import frc.robot.commands.drive.Flip;
import frc.robot.commands.drive.SetSpeed;
import frc.robot.commands.limelight.LimelightDisplay;
import frc.robot.commands.shooter.SetShooterPower;
import frc.robot.subsystems.*;

public class RobotContainer {
    private SendableChooser<AutoRoutine> chooser = new SendableChooser<>();
    private final Drive m_drive = new Drive();

    private final Drive drive = new Drive();
    private final Intake intake = new Intake();
    private final Feeder feeder = new Feeder();
    private final Shooter shooter = new Shooter();
    private final Limelight limelight = new Limelight();
    private final OI oi = OI.getInstance();

    enum AutoRoutine {
        WaitCommand, ThreeCargoBlue, ThreeCargoRed, TwoCargoBlue, TwoCargoRed
    }

    public RobotContainer() {
        drive.setDefaultCommand(new SetSpeed(drive));
        intake.setDefaultCommand(new SetIntakePower(intake));
        feeder.setDefaultCommand(new SetFeederPower(feeder));
        limelight.setDefaultCommand(new LimelightDisplay(limelight));
        // for testing only
        shooter.setDefaultCommand(new SetShooterPower(shooter));

        chooser.addOption("Wait command (placeholder)", AutoRoutine.WaitCommand);
        chooser.addOption("3 ball auto blue", AutoRoutine.ThreeCargoBlue);
        chooser.addOption("2 ball autos blue", AutoRoutine.TwoCargoBlue);
        chooser.addOption("3 ball auto red", AutoRoutine.ThreeCargoRed);
        chooser.addOption("2 ball autos red", AutoRoutine.TwoCargoRed);

        chooser.setDefaultOption("Wait command (placeholder)", AutoRoutine.WaitCommand);
        configureButtonBindings();

        Shuffleboard.getTab("Autonomous Selection").add(chooser);
    }

    private void configureButtonBindings() {
        new JoystickButton(oi.leftStick, Constants.OIConstants.FLIP_BUTTON).whenPressed(new Flip(drive));
        new JoystickButton(oi.rightStick, Constants.OIConstants.HALF_SPEED_BUTTON)
                .whenPressed(() -> drive.setMaxPower(0.5)).whenReleased(() -> drive.setMaxPower(1.0));
    }

    public Command getAutonomousCommand(AutoRoutine choice) {
        choice = chooser.getSelected();
        switch (choice) {
            case ThreeCargoBlue:
                return TrajectoryFollower.getPath("output/3ball1.wpilib.json", m_drive, true);
            case ThreeCargoRed:
                return TrajectoryFollower.getPath("output/3ball1red.wpilib.json", m_drive, true);
            case TwoCargoBlue:
                return TrajectoryFollower.getPath("output/2ball1.wpilib.json", m_drive, true)
                        .andThen(TrajectoryFollower.getPath("output/2ball2.wpilib.json", m_drive, false))
                        .andThen(TrajectoryFollower.getPath("output/2ball3.wpilib.json", m_drive, false));
            case TwoCargoRed:
                return TrajectoryFollower.getPath("output/2ball1red.wpilib.json", m_drive, true)
                        .andThen(TrajectoryFollower.getPath("output/2ball2red.wpilib.json", m_drive, false))
                        .andThen(TrajectoryFollower.getPath("output/2ball3red.wpilib.json", m_drive, false));
            default:
                return new WaitCommand(1.0);
        }
    }
}
