package frc.robot;

import frc.robot.commands.SetSpeed;
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;;

public class RobotContainer {
    private final Drive m_drive = new Drive();

    enum AutoRoutine {
        WaitCommand, AutoCommand
    }

    SendableChooser<AutoRoutine> chooser = new SendableChooser<>();

    public RobotContainer() {
        m_drive.setDefaultCommand(new SetSpeed(m_drive));

        chooser.addOption("Wait command (placeholder)", AutoRoutine.WaitCommand);
        chooser.addOption("Auto command (placeholder)", AutoRoutine.AutoCommand);

        chooser.setDefaultOption("Wait command (placeholder)", AutoRoutine.WaitCommand);
        configureButtonBindings();

        Shuffleboard.getTab("Autonomous Selection").add(chooser);
    }

    private void configureButtonBindings() {
    }

    public Command getAutonomousCommand() {
        return new WaitCommand(1.0);
    }
}
