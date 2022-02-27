package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
    private final CANSparkMax left0 = createController(10, false);
    private final CANSparkMax left1 = createController(11, false);
    private final CANSparkMax right0 = createController(20, false);
    private final CANSparkMax right1 = createController(21, false);

    private final RelativeEncoder leftEncoder = left0.getEncoder();
    private final RelativeEncoder rightEncoder = right0.getEncoder();

    @Override
    public void robotInit() {
        left1.follow(left0);
        right1.follow(right0);
        SmartDashboard.putNumber("leftPower", 0);
        SmartDashboard.putNumber("rightPower", 0);
    }

    @Override
    public void robotPeriodic() {
        SmartDashboard.putNumber("Left Velocity", leftEncoder.getVelocity());
        SmartDashboard.putNumber("Right Velocity", rightEncoder.getVelocity());

        SmartDashboard.putNumber("Left Temperature", left0.getMotorTemperature());
        SmartDashboard.putNumber("Right Temperature", right0.getMotorTemperature());

        SmartDashboard.putNumber("Left Current", left0.getOutputCurrent());
        SmartDashboard.putNumber("Right Current", right0.getOutputCurrent());

        NetworkTableInstance.getDefault().flush();
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        left0.set(0);
        right0.set(0);
    }

    @Override
    public void teleopPeriodic() {
        left0.set(SmartDashboard.getNumber("leftPower", 0));
        right0.set(SmartDashboard.getNumber("rightPower", 0));
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }

    @Override
    public void simulationInit() {
    }

    @Override
    public void simulationPeriodic() {
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

        controller.enableVoltageCompensation(12);
        controller.setIdleMode(IdleMode.kCoast);
        controller.setOpenLoopRampRate(7);
        controller.setClosedLoopRampRate(7);

        controller.setSmartCurrentLimit(60);
        controller.setSecondaryCurrentLimit(100);

        controller.setInverted(isInverted);
        return controller;
    }

}
