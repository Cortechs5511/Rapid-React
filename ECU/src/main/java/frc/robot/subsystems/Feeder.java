package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.FeederConstants;

public class Feeder extends SubsystemBase {
    private final WPI_TalonSRX tower1Motor = createFeederController(FeederConstants.TOWER_1_ID, FeederConstants.INVERT_TOWER_1);

    private final DigitalInput bottomSensor = new DigitalInput(FeederConstants.BOTTOM_SENSOR_PORT);
    private final DigitalInput topSensor = new DigitalInput(FeederConstants.TOP_SENSOR_PORT);

    public boolean getBottomSensor() {
        return bottomSensor.get();
    }
    
    public boolean getTopSensor() {
        return topSensor.get();
    }

    public Feeder() {
        WPI_TalonSRX tower2Motor = createFeederController(FeederConstants.TOWER_2_ID, FeederConstants.INVERT_TOWER_2);
        tower2Motor.follow(tower1Motor);
    }

    /**
     * Sets the two tower motors (polybelt) to open loop power
     *
     * @param power double power
     */
    public void setTower(double power) {
        tower1Motor.set(power);
    }

    /**
     * Creates a TalonSRX controller for feeder with preferred configuration
     *
     * @param id     CAN ID of the controller
     * @param invert boolean flag for inversion
     * @return WPI_TalonSRX
     */
    private WPI_TalonSRX createFeederController(int id, boolean invert) {
        WPI_TalonSRX controller = new WPI_TalonSRX(id);

        controller.configFactoryDefault();

        controller.configContinuousCurrentLimit(FeederConstants.CURRENT_LIMIT);
        controller.configPeakCurrentLimit(FeederConstants.PEAK_CURRENT_LIMIT);

        controller.configOpenloopRamp(FeederConstants.RAMP_RATE);
        controller.configClosedloopRamp(FeederConstants.RAMP_RATE);

        controller.setInverted(invert);
        controller.setNeutralMode(FeederConstants.NEUTRAL_MODE);

        return controller;
    }

    @Override
    public void periodic() {
        if (Constants.DIAGNOSTICS) {
            SmartDashboard.putBoolean("Feeder/Tower Sensor", getBottomSensor());
        }
    }
}
