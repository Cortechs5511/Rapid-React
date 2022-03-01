package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.FeederConstants;

public class Feeder extends SubsystemBase {
    private final WPI_TalonSRX feederMotor = createFeederController(FeederConstants.FEEDER_ID, FeederConstants.INVERT_FEEDER);
    private final WPI_TalonSRX tower1Motor = createFeederController(FeederConstants.TOWER_1_ID, FeederConstants.INVERT_TOWER_1);
    private final WPI_TalonSRX tower2Motor = createFeederController(FeederConstants.TOWER_2_ID, FeederConstants.INVERT_TOWER_2);

    private final DigitalInput towerSensor = new DigitalInput(FeederConstants.TOWER_SENSOR_PORT);

    public Feeder() {
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
     * Sets the feeder motor to open loop power
     *
     * @param power double power
     */
    public void setFeeder(double power) {
        feederMotor.set(power);
    }

    /**
     * Returns whether a cargo is detected in the tower
     *
     * @return boolean sensor output
     */
    public boolean getTowerSensor() {
        return towerSensor.get();
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

        return controller;
    }

    @Override
    public void periodic() {
        if (Constants.DIAGNOSTICS) {
            SmartDashboard.putBoolean("Feeder/Tower Sensor", getTowerSensor());
        }
    }
}
