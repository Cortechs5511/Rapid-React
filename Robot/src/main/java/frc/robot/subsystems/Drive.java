package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;

public class Drive extends SubsystemBase {
    private final CANSparkMax leftLeader = createDriveController(DriveConstants.LEFT_LEADER_ID, false);
    private final CANSparkMax leftFollower = createDriveController(DriveConstants.LEFT_FOLLOWER_ID, false);

    private final CANSparkMax rightLeader = createDriveController(DriveConstants.RIGHT_LEADER_ID, true);
    private final CANSparkMax rightFollower = createDriveController(DriveConstants.RIGHT_FOLLOWER_ID, true);

    private final RelativeEncoder leftEncoder = createEncoder(leftFollower);
    private final RelativeEncoder rightEncoder = createEncoder(rightFollower);

    private final AHRS gyro = new AHRS(Port.kMXP);
    private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(new Rotation2d(0));

    private boolean inverted = false;
    private double maxPower = 1.0;

    public Drive() {
        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);

        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);

        gyro.reset();
    }

    /**
     * Get position of left encoder
     * 
     * @return double encoder sensed position, meters
     */
    public double getLeftPosition() {
        return leftEncoder.getPosition();
    }

    /**
     * Get position of right encoder
     * 
     * @return double encoder sensed position, meters
     */
    public double getRightPosition() {
        return rightEncoder.getPosition();
    }

    /**
     * Get velocity of left encoder
     * Note that this has a ~100ms phase lag
     * 
     * @return double encoder velocity, meters per second
     */
    public double getLeftVelocity() {
        return leftEncoder.getVelocity();
    }

    /**
     * Get velocity of right encoder
     * Note that this has a ~100ms phase lag
     * 
     * @return double encoder velocity, meters per second
     */
    public double getRightVelocity() {
        return rightEncoder.getVelocity();
    }

    /**
     * Returns wheel speeds for autonomous
     *
     * @return DifferentialDriveWheelSpeeds
     */
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(leftEncoder.getVelocity(), rightEncoder.getVelocity());
    }

    /**
     * Resets odometry for auto
     */
    public void resetOdometry() {
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
        odometry.resetPosition(new Pose2d(), Rotation2d.fromDegrees(gyro.getYaw()));
    }

    /**
     * Returns Pose2D of robot odometry
     * 
     * @return Pose2D robot position (x, y) in meters
     */
    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    /**
     * Sets drivetrain inversion flag to opposite of existing value
     */
    public void flip() {
        inverted = !inverted;
    }

    /**
     * Sets drivetrain set() limitation interpolation value for both motors
     *
     * @param power double max power value out of 1
     */
    public void setMaxPower(double power) {
        maxPower = power;
    }

    @Override
    public void simulationPeriodic() {
    }

    /**
     * Commands setpoint value out of 1 (voltage compensated) on both motors
     *
     * @param leftSpeed  double left motor setpoint out of maxPower
     * @param rightSpeed double right motor setpoint out of maxPower
     */
    public void setPower(double leftSpeed, double rightSpeed) {
        if (!inverted) {
            leftLeader.set(leftSpeed * maxPower);
            rightLeader.set(rightSpeed * maxPower);
        } else {
            rightLeader.set(-leftSpeed * maxPower);
            leftLeader.set(-rightSpeed * maxPower);
        }
    }

    /**
     * Sets desired output voltage for drivetrain
     * 
     * @param leftVolts  double volts for left side
     * @param rightVolts double volts for right side
     */
    public void setVolts(double leftVolts, double rightVolts) {
        leftLeader.setVoltage(leftVolts);
        rightLeader.setVoltage(rightVolts);
    }

    /**
     * Creates a CANSparkMax controller with preferred settings
     *
     * @param port       applicable CAN port
     * @param isInverted boolean inversion flag
     * @return CANSparkMax controller
     */
    private CANSparkMax createDriveController(int port, boolean isInverted) {
        CANSparkMax controller = new CANSparkMax(port, MotorType.kBrushless);
        controller.restoreFactoryDefaults();

        controller.enableVoltageCompensation(DriveConstants.VOLTAGE_COMPENSATION);
        controller.setIdleMode(DriveConstants.IDLE_MODE);
        controller.setOpenLoopRampRate(DriveConstants.RAMP_RATE);
        controller.setClosedLoopRampRate(DriveConstants.RAMP_RATE);

        controller.setSmartCurrentLimit(DriveConstants.CURRENT_LIMIT);
        controller.setSecondaryCurrentLimit(100);

        controller.setInverted(isInverted);
        return controller;
    }

    /**
     * Creates an encoder object from NEO with preferred settings
     *
     * @param controller CANSparkMax controller
     * @return RelativeEncoder
     */
    private RelativeEncoder createEncoder(CANSparkMax controller) {
        RelativeEncoder encoder = controller.getEncoder();

        encoder.setPositionConversionFactor(DriveConstants.ENCODER_TO_METERS);
        encoder.setVelocityConversionFactor(DriveConstants.ENCODER_TO_METERS);

        return encoder;
    }

    @Override
    public void periodic() {
        odometry.update(new Rotation2d(Math.IEEEremainder(gyro.getYaw(), 360)), leftEncoder.getPosition(),
                rightEncoder.getPosition());

        if (Constants.DIAGNOSTICS) {
            SmartDashboard.putNumber("Drivetrain/Left Position", getLeftPosition());
            SmartDashboard.putNumber("Drivetrain/Right Position", getRightPosition());

            SmartDashboard.putNumber("Drivetrain/Left Velocity", getLeftVelocity());
            SmartDashboard.putNumber("Drivetrain/Right Velocity", getRightVelocity());

            SmartDashboard.putNumber("Drivetrain/Left Temp", leftLeader.getMotorTemperature());
            SmartDashboard.putNumber("Drivetrain/Right Temp", rightLeader.getMotorTemperature());

            SmartDashboard.putNumber("Drivetrain/Left Current", leftLeader.getOutputCurrent());
            SmartDashboard.putNumber("Drivetrain/Right Current", rightLeader.getOutputCurrent());

            SmartDashboard.putNumber("Drivetrain/Yaw", gyro.getYaw());
        }
    }

}
