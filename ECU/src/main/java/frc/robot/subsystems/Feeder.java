package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FeederConstants;

public class Feeder extends SubsystemBase {
    private final WPI_TalonSRX feederMotor = createFeederController(FeederConstants.FEEDER_ID, FeederConstants.INVERT_FEEDER);
    private final WPI_TalonSRX tower1Motor = createFeederController(FeederConstants.TOWER_1_ID, FeederConstants.INVERT_TOWER_1);
    private final WPI_TalonSRX tower2Motor = createFeederController(FeederConstants.TOWER_2_ID, FeederConstants.INVERT_TOWER_2);

    public Feeder() {
        tower2Motor.follow(tower1Motor);
    }

    public void setTower(double speed) {
        tower1Motor.set(speed);
    }

    public void setFeeder(double speed) {
        feederMotor.set(speed);
    }

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
    }

}
