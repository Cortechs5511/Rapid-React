package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class Drive extends SubsystemBase {
    private final CANSparkMax leftLeader = createController(DriveConstants.LEFT_LEADER_PORT, false);
    private final CANSparkMax leftFollower = createController(DriveConstants.LEFT_FOLLOWER_PORT, false);

    private final CANSparkMax rightLeader = createController(DriveConstants.RIGHT_LEADER_PORT, true);
    private final CANSparkMax rightFollower = createController(DriveConstants.RIGHT_FOLLOWER_PORT, true);

    private final RelativeEncoder leftEncoder = createEncoder(leftFollower);
    private final RelativeEncoder rightEncoder = createEncoder(rightFollower);

    private final SparkMaxPIDController leftPIDController = createPID(leftLeader);
    private final SparkMaxPIDController rightPIDController = createPID(rightLeader);

    private final AHRS gyro = new AHRS(Port.kMXP);
    private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(new Rotation2d(0));

    public double getLeftPosition() { return leftEncoder.getPosition(); }
    public double getRightPosition() { return rightEncoder.getPosition(); }

    public Drive() {
        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);

        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);

        gyro.reset();
    }

    @Override
    public void periodic() {
        odometry.update(new Rotation2d(Math.IEEEremainder(gyro.getYaw(), 360)),
                        leftEncoder.getPosition(), 
                        rightEncoder.getPosition());

        SmartDashboard.putNumber("Drivetrain/Left Position", getLeftPosition());
        SmartDashboard.putNumber("Drivetrain/Right Position", getRightPosition());
    }

    @Override
    public void simulationPeriodic() {
    }

    /**
     * Commands setpoint value out of 1 (voltage compensated) on both motors
     * 
     * @param leftSpeed double left motor setpoint
     * @param rightSpeed double right motor setpoint
     */
    public void setSpeed(double leftSpeed, double rightSpeed) {
        leftLeader.set(leftSpeed);
        rightLeader.set(rightSpeed);
    }

    /**
     * Commands PID reference (m/s) on both motors
     * 
     * @param leftSpeed double left motor setpoint
     * @param rightSpeed double right motor setpoint
     */
    public void setPIDReference(double leftSpeed, double rightSpeed) {
        leftPIDController.setReference(leftSpeed, ControlType.kVelocity);
        rightPIDController.setReference(rightSpeed, ControlType.kVelocity);
    }

    /**
     * Creates a CANSparkMax controller with preferred settings
     * 
     * @param port applicable CAN port
     * @param isInverted boolean inversion flag
     * @return CANSparkMax controller
     */
    private CANSparkMax createController(int port, boolean isInverted) {
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

    /**
     * Creates a PID controller from CANSparkMax
     * 
     * @param controller CANSparkMax controller
     * @return SparkMaxPIDController
     */
    private SparkMaxPIDController createPID(CANSparkMax controller) {
        SparkMaxPIDController pid = controller.getPIDController();

        pid.setP(DriveConstants.PID_P);
        pid.setI(DriveConstants.PID_I);
        pid.setD(DriveConstants.PID_D);
        pid.setFF(DriveConstants.PID_FF);

        pid.setOutputRange(DriveConstants.PID_MIN_OUTPUT, DriveConstants.PID_MAX_OUTPUT);

        return pid;
    }
}
