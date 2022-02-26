package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.FeederConstants;

public class Feeder extends SubsystemBase {
    private final WPI_TalonSRX feederMotor = new WPI_TalonSRX(FeederConstants.FEEDER_PORT);
    private final WPI_TalonSRX tower1motor = new WPI_TalonSRX(FeederConstants.TOWER1_PORT);
    private final WPI_TalonSRX tower2motor = new WPI_TalonSRX(FeederConstants.TOWER2_PORT);

    public void setTower(double speed){
        tower1motor.set(speed);
        tower2motor.set(speed);
    }
    public void setFeeder(double speed){
        feederMotor.set(speed);
    }

    public Feeder() {
        feederMotor.setInverted(false);
    }
}
