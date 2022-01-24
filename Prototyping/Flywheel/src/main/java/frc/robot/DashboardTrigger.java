package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class DashboardTrigger extends Trigger {
    @Override
    public boolean get() {
        return SmartDashboard.getBoolean("Shooter/RPM Mode", false);
    }
}
