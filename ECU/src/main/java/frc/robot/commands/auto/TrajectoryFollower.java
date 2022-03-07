package frc.robot.commands.auto;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.subsystems.Drive;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;

public class TrajectoryFollower extends CommandBase {
    public static RamseteCommand getPath(String trajectoryLocation, Drive drive, boolean resetPos) {
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryLocation);
            Trajectory trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);

            if (resetPos) {
                drive.resetOdometry();
            }

            return new RamseteCommand(trajectory, 
                                    drive::getPose, 
                                    new RamseteController(AutoConstants.RAMSETE_BETA, AutoConstants.RAMSETE_ZETA), 
                                    new SimpleMotorFeedforward(DriveConstants.KS, DriveConstants.KV, DriveConstants.KA), 
                                    DriveConstants.DRIVE_KINEMATICS, 
                                    drive::getWheelSpeeds, 
                                    new PIDController(DriveConstants.PID_P, 0, 0), 
                                    new PIDController(DriveConstants.PID_P, 0, 0),
                                    drive::setVolts, 
                                    drive);
                                    

        } catch (IOException exception) {
            System.out.println("Unable to open trajectory " + trajectoryLocation);
        }

        return null;
    }
}