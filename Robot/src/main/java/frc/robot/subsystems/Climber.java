package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.Constants.ClimberConstants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
    private final CANSparkMax leftClimber = createClimberController(ClimberConstants.LEFT_CLIMBER_PORT, ClimberConstants.INVERT_LEFT_CLIMBER);
    private final CANSparkMax rightClimber = createClimberController(ClimberConstants.RIGHT_CLIMBER_PORT, ClimberConstants.INVERT_RIGHT_CLIMBER);

    public Climber() {

    }

    /**
     * Sets left climber power to output power
     * 
     * @param power double power between -1 and 1
     */
    public void setLeftClimberPower(double power) {
        leftClimber.set(power);
    }

    /**
     * Sets right climber power to output power
     * 
     * @param power double power between -1 and 1
     */
    public void setRightClimberPower(double power) {
        rightClimber.set(power);
    }

    @Override
    public void periodic() {
        if (Constants.DIAGNOSTICS) {
            SmartDashboard.putNumber("Climber/Left Current", leftClimber.getOutputCurrent());
            SmartDashboard.putNumber("Climber/Right Current", rightClimber.getOutputCurrent());
        }
    }

    /**
     * Creates a CANSparkMax controller with preferred settings
     *
     * @param port       applicable CAN port
     * @param isInverted boolean inversion flag
     * @return CANSparkMax controller
     */
    private CANSparkMax createClimberController(int port, boolean isInverted) {
        CANSparkMax controller = new CANSparkMax(port, MotorType.kBrushless);
        controller.restoreFactoryDefaults();

        controller.enableVoltageCompensation(ClimberConstants.VOLTAGE_COMPENSATION);
        controller.setIdleMode(ClimberConstants.IDLE_MODE);
        controller.setOpenLoopRampRate(ClimberConstants.RAMP_RATE);
        controller.setClosedLoopRampRate(ClimberConstants.RAMP_RATE);

        controller.setSmartCurrentLimit(ClimberConstants.CURRENT_LIMIT);
        controller.setSecondaryCurrentLimit(100);

        controller.setInverted(isInverted);
        return controller;
    }

}
