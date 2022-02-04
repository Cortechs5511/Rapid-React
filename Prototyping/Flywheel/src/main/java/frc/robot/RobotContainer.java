// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.SetPower;
import frc.robot.commands.SetSpeed;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.TopShooter;
import frc.robot.commands.SetTopSpeed;
import frc.robot.commands.SetTopPower;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final Shooter m_shooter = new Shooter();
    private final TopShooter t_shooter = new TopShooter();
    private final Trigger m_trigger = new DashboardTrigger("Shooter/RPM Mode");
    private final Trigger t_trigger = new DashboardTrigger("TopShooter/RPM Mode");
    private final SetPower m_setPower = new SetPower(m_shooter);
    private final SetSpeed m_setSpeed = new SetSpeed(m_shooter);
    private final SetTopPower t_setPower = new SetTopPower(t_shooter);
    private final SetTopSpeed t_setSpeed = new SetTopSpeed(t_shooter);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
       
        SmartDashboard.putBoolean("Shooter/RPM Mode", false);
        
        m_shooter.setDefaultCommand(m_setPower);
        m_trigger.whenActive(m_setSpeed)
                .whenInactive(m_setPower);
        SmartDashboard.putBoolean("TopShooter/RPM Mode", false);
        t_shooter.setDefaultCommand(t_setPower);
        t_trigger.whenActive(t_setSpeed)
                .whenInactive(t_setPower);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {}

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new WaitCommand(1.0);
    }
}
