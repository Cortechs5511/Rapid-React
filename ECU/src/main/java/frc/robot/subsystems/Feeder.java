package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FeederConstants;

public class Feeder extends SubsystemBase {
    private final WPI_TalonSRX feederMotor = new WPI_TalonSRX(FeederConstants.FEEDER_ID);
    private final WPI_TalonSRX tower1Motor = new WPI_TalonSRX(FeederConstants.TOWER_1_ID);
    private final WPI_TalonSRX tower2Motor = new WPI_TalonSRX(FeederConstants.TOWER_2_ID);

    public Feeder() {
        feederMotor.setInverted(FeederConstants.INVERT_FEEDER);
        tower1Motor.setInverted(FeederConstants.INVERT_TOWER_1);
        tower2Motor.setInverted(FeederConstants.INVERT_TOWER_2);
    }

    public void setTower(double speed){
        tower1Motor.set(speed);
        tower2Motor.set(speed);
    }

    public void setFeeder(double speed){
        feederMotor.set(speed);
    }

    private WPI_TalonSRX createFeederController(int id, boolean invert) {
}
