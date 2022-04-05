package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.auto.TaxiShoot;
import frc.robot.commands.auto.TrajectoryFollower;
import frc.robot.commands.collector.SetFeederPower;
import frc.robot.commands.collector.SetIntakePower;
import frc.robot.commands.drive.Flip;
import frc.robot.commands.drive.SetSpeed;
import frc.robot.commands.SetClimberPower;
import frc.robot.commands.limelight.LimelightDisplay;
import frc.robot.commands.shooter.SetShooterPower;
import frc.robot.subsystems.*;

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
        WaitCommand, BlueLeft, BlueRight, BlueMiddle, RedLeft, RedRight, RedMiddle
    }

    public RobotContainer() {
        drive.setDefaultCommand(new SetSpeed(drive));
        intake.setDefaultCommand(new SetIntakePower(intake));
        feeder.setDefaultCommand(new SetFeederPower(feeder));
        limelight.setDefaultCommand(new LimelightDisplay(limelight));
        climber.setDefaultCommand(new SetClimberPower(climber));
        // for testing only
        shooter.setDefaultCommand(new SetShooterPower(shooter));

        chooser.addOption("Wait command (placeholder)", AutoRoutine.WaitCommand);
        chooser.addOption("2 ball auto blue left", AutoRoutine.BlueLeft);
        chooser.addOption("2 ball autos blue middle", AutoRoutine.BlueMiddle);
        chooser.addOption("2 ball auto blue right", AutoRoutine.BlueRight);
        chooser.addOption("2 ball autos red left", AutoRoutine.RedLeft);
        chooser.addOption("2 ball autos red middle", AutoRoutine.RedMiddle);
        chooser.addOption("2 ball autos red right", AutoRoutine.RedRight);

        chooser.setDefaultOption("Wait command (placeholder)", AutoRoutine.WaitCommand);
        configureButtonBindings();

        Shuffleboard.getTab("Autonomous Selection").add(chooser);
    }

    private void configureButtonBindings() {
        new JoystickButton(oi.leftStick, Constants.OIConstants.FLIP_BUTTON).whenPressed(new Flip(drive));
        new JoystickButton(oi.rightStick, Constants.OIConstants.HALF_SPEED_BUTTON)
                .whenPressed(() -> drive.setMaxPower(0.5)).whenReleased(() -> drive.setMaxPower(1.0));
    }

    public Command getAutonomousCommand() {
        AutoRoutine choice = chooser.getSelected();
        Command selected;

        switch (choice) {
            case BlueLeft:
                selected = TrajectoryFollower.getPath("output/BlueLeft.wpilib.json", drive, true);    
            case BlueMiddle:
                selected = TrajectoryFollower.getPath("output/BlueMiddle.wpilib.json", drive, true);
            case BlueRight:
                selected = TrajectoryFollower.getPath("output/BlueRight.wpilib.json", drive, true);
            case RedLeft:
                selected = TrajectoryFollower.getPath("output/RedLeft.wpilib.json", drive, true);
            case RedMiddle:
                selected = TrajectoryFollower.getPath("output/RedMiddle.wpilib.json", drive, true);
            case RedRight:
                selected = TrajectoryFollower.getPath("output/RedRight.wpilib.json", drive, true);
            case WaitCommand:
                WaitCommand waitCommand2 = new WaitCommand();
            default:
                selected = new TaxiShoot(feeder, shooter, drive);
        }
        if (selected == null) {
            return new TaxiShoot(feeder, shooter, drive);
        }
    }
}
