package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.OI;
import frc.robot.subsystems.Shooter;

public class SetShooterPower extends CommandBase {
    private final Shooter shooter;
    private final OI oi = OI.getInstance();
    boolean shooting = false;
    int tempCount = 0;

    public SetShooterPower(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(this.shooter);
    }

    @Override
    public void initialize() {
        shooter.setBottomPower(0);

        SmartDashboard.putNumber("Shooter/Bottom Shooter Power", 0.45);
        SmartDashboard.putNumber("Shooter/Top Shooter Power", 0.0);
    }

    @Override
    public void execute() {
        if (shooting) {
            shooter.setBottomPower(SmartDashboard.getNumber("Shooter/Bottom Shooter Power", 0.45));
            shooter.setTopPower(SmartDashboard.getNumber("Shooter/Top Shooter Power", 0.0));    
        } else {
            shooter.setBottomPower(0);
            shooter.setTopPower(0);
        }

        if (oi.leftStick.getRawButtonPressed(4)) {
            tempCount++;
        } else if (oi.leftStick.getRawButtonPressed(3)) {
            tempCount--;
        } else {
            tempCount = 0;
        }

        if (tempCount > 20) {
            SmartDashboard.putNumber("Shooter/Bottom Shooter Power", SmartDashboard.getNumber("Shooter/Bottom Shooter Power", 0) + 0.01);
        } else if (tempCount < -20) {
            SmartDashboard.putNumber("Shooter/Bottom Shooter Power", SmartDashboard.getNumber("Shooter/Bottom Shooter Power", 0) - 0.01);
        }

        if (oi.rightStick.getRawButton(1)) {
            shooting = true;
        } else if (oi.rightStick.getRawButton(3)) {
            shooting = false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setBottomPower(0);
        shooter.setTopPower(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
