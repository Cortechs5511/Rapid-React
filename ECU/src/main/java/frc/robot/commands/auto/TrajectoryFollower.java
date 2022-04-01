package frc.robot.commands.auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drive;

import java.io.IOException;
import java.nio.file.Path;

public class TrajectoryFollower extends CommandBase {
    public static RamseteCommand getPath(String trajectoryJSON, Drive m_drive, boolean reset) {
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
            Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
            
            if (reset) {
                m_drive.resetOdometry();
            }

            return new RamseteCommand(trajectory, m_drive::getPose,
                    new RamseteController(AutoConstants.RAMSETE_BETA, AutoConstants.RAMSETE_ZETA),
                    new SimpleMotorFeedforward(DriveConstants.Ks, DriveConstants.Kv,
                            DriveConstants.Ka),
                    DriveConstants.DRIVE_KINEMATICS, m_drive::getWheelSpeeds,
                    new PIDController(DriveConstants.Kp, 0, 0),
                    new PIDController(DriveConstants.Kp, 0, 0),
                    m_drive::setVolts, m_drive);
        } catch (IOException ex) {
            System.out.println( "Unable to open trajectory: " + trajectoryJSON);
        }
        return null;
    }
}
