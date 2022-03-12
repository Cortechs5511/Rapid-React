package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.auto.DriveTimed;
import frc.robot.commands.auto.TrajectoryFollower;
import frc.robot.commands.collector.SetFeederPower;
import frc.robot.commands.collector.SetIntakePower;
import frc.robot.commands.drive.Flip;
import frc.robot.commands.drive.SetSpeed;
import frc.robot.commands.shooter.AutoShootCommandGroup;
import frc.robot.commands.shooter.DefaultShooterSpeed;
import frc.robot.commands.shooter.LightToggle;
import frc.robot.commands.shooter.LowerShooterSpeed;
import frc.robot.commands.shooter.RaiseShooterSpeed;
import frc.robot.commands.shooter.SetShooterPower;
import frc.robot.commands.shooter.ShootCommandGroup;
import frc.robot.commands.shooter.StopShooter;
import frc.robot.commands.auto.DriveTimed;
import frc.robot.subsystems.*;

public class RobotContainer {
    final SendableChooser<AutoRoutine> chooser = new SendableChooser<>();
    private final Drive drive = new Drive();
    private final Intake intake = new Intake();
    private final Feeder feeder = new Feeder();
    private final Shooter shooter = new Shooter();
    // private final Limelight limelight = new Limelight();
    private final OI oi = OI.getInstance();
    private final DriveTimed driveTimedCommand = new DriveTimed(drive, 2, -0.4);

    public RobotContainer() {
        drive.setDefaultCommand(new SetSpeed(drive));
        intake.setDefaultCommand(new SetIntakePower(intake));
        feeder.setDefaultCommand(new SetFeederPower(feeder));

        // for testing only
        shooter.setDefaultCommand(new SetShooterPower(shooter));

        chooser.addOption("Wait command (placeholder)", AutoRoutine.WaitCommand);
        chooser.addOption("3 ball auto", AutoRoutine.ThreeCargoBlue);
        chooser.addOption("2 ball autos", AutoRoutine.TwoCargoBlue);
        chooser.addOption("ECU Simple", AutoRoutine.ECUSimple);

        chooser.setDefaultOption("Wait command (placeholder)", AutoRoutine.WaitCommand);
        configureButtonBindings();

        Shuffleboard.getTab("Autonomous Selection").add(chooser);
    }

    private void configureButtonBindings() {
        new JoystickButton(oi.rightStick, Constants.OIConstants.SHOOT_SPEED_DOWN).whenPressed(new LowerShooterSpeed(shooter));
        new JoystickButton(oi.rightStick, Constants.OIConstants.SHOOT_SPEED_UP).whenPressed(new RaiseShooterSpeed(shooter));
        new JoystickButton(oi.rightStick, Constants.OIConstants.SHOOT_SPEED_RESET).whenPressed(new DefaultShooterSpeed(shooter));

        new JoystickButton(oi.rightStick, Constants.OIConstants.SHOOT_BUTTON).whenPressed(new ShootCommandGroup(feeder, intake, shooter));
        new JoystickButton(oi.rightStick, Constants.OIConstants.STOP_SHOOT_BUTTON).whenReleased(new StopShooter(shooter));

        new JoystickButton(oi.leftStick, Constants.OIConstants.FLIP_BUTTON).whenPressed(new Flip(drive));
        new JoystickButton(oi.rightStick, Constants.OIConstants.HALF_SPEED_BUTTON).whenPressed(() -> drive.setMaxPower(0.5)).whenReleased(() -> drive.setMaxPower(1.0));
    }

    public Command getAutonomousCommand() {
        // Command autoCommand;

        // switch (chooser.getSelected()) {
        //     case WaitCommand:
        //         autoCommand = new WaitCommand(2.0);
        //         break;
        //     case ECUSimple:
        //         autoCommand = new AutoShootCommandGroup(drive, limelight, shooter).andThen(driveTimedCommand);
        //     case ThreeCargoBlue:
        //         autoCommand = TrajectoryFollower.getPath("output/3ball1.wpilib.json", drive, true);
        //         break;
        //     case TwoCargoBlue:
        //         autoCommand = TrajectoryFollower.getPath("output/2ball1.wpilib.json", drive, true);
        //         break;
        //     case ThreeCargoRed:
        //         autoCommand = TrajectoryFollower.getPath("output/3ball1red.wpilib.json", drive, true);
        //         break;
        //     case TwoCargoRed:
        //         autoCommand = TrajectoryFollower.getPath("output/2ball1red.wpilib.json", drive, true);
        //         break;
        //     default:
        //         autoCommand = new WaitCommand(1.0);
        //         break;
        // }

        // assert autoCommand != null;

        Command autoCommand = driveTimedCommand;
        return autoCommand.andThen(new RunCommand(() -> drive.setPower(0, 0)));
    }

    enum AutoRoutine {
        WaitCommand, ECUSimple, ThreeCargoBlue, ThreeCargoRed, TwoCargoBlue, TwoCargoRed
    }
}
