package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

public class SetShooterPower extends CommandBase {
    private final Shooter shooter;
    private final OI oi = OI.getInstance();

    boolean shooting = false;
    int tempCount = 0;

    double bottomPower = ShooterConstants.BOTTOM_SHOOTER_POWER;
    double topPower = ShooterConstants.TOP_SHOOTER_POWER;

    double lastSpeed = 0;

    public SetShooterPower(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(this.shooter);
    }

    @Override
    public void initialize() {
        shooter.setBottomPower(0);

        SmartDashboard.putNumber("Shooter/Bottom Shooter Power", bottomPower);
        SmartDashboard.putNumber("Shooter/Top Shooter Power", topPower);
    }

    @Override
    public void execute() {
        bottomPower = SmartDashboard.getNumber("Shooter/Bottom Shooter Power", bottomPower);
        topPower = SmartDashboard.getNumber("Shooter/Top Shooter Power", topPower);

        // Toglge shooting power based off of status
        if (shooting) {
            shooter.setBottomPower(bottomPower);
            shooter.setTopPower(topPower);    

            // Shooter speed advisory for operator
            double speed = shooter.getBottomSpeed();

            if ((speed - lastSpeed) > ShooterConstants.ALERT_THRESHOLD) {
                oi.setRumble(0.6);
            } else {
                oi.setRumble(0);
            }

            lastSpeed = speed;
        } else {
            shooter.setBottomPower(0);
            shooter.setTopPower(0);
        }

        // Toggle shooting on and off
        if (oi.rightStick.getRawButton(1)) {
            // Set to shoot
            shooting = true;
        } else if (oi.rightStick.getRawButton(3)) {
            // Stop
            shooting = false;
        }
    }


    @Override
    public void end(boolean interrupted) {
        shooter.setBottomPower(0);
        shooter.setTopPower(0);
    }


    /**
     * Puts the shooter power values on the SmartDashboard
     * 
     * @param top double power of top wheel
     * @param bottom double power of bottom wheel
     */
    private void putPowers(double top, double bottom) {
        SmartDashboard.putNumber("Shooter/Bottom Shooter Power", bottom);
        SmartDashboard.putNumber("Shooter/Top Shooter Power", top);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
