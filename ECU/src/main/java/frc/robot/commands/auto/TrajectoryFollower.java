package main.java.frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drive;

import java.io.IOException;
import java.nio.file.Path;

public class TrajectoryFollower {
    public static RamseteCommand getPath(String trajectoryJSON, Drive m_drive, boolean reset) {
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
            
            if (reset) {
                m_drive.resetOdometry(trajectory.getInitialPose());
            }

            return new RamseteCommand(trajectory, m_drive::getPose,
                    new RamseteController(AutoConstants.RAMSETE_BETA, AutoConstants.RAMSETE_ZETA),
                    new SimpleMotorFeedforward(DriveConstants.Ks, DriveConstants.Kv,
                            DriveConstants.Ka),
                    DriveConstants.kDriveKinematics, m_drive::getWheelSpeeds,
                    new PIDController(DriveConstants.Kp, 0, 0),
                    new PIDController(DriveConstants.Kp, 0, 0),
                    m_drive::setOutput, m_drive);
        } catch (IOException ex) {
            System.out.println( "Unable to open trajectory: " + trajectoryJSON);
        }
        return null;
    }
}
