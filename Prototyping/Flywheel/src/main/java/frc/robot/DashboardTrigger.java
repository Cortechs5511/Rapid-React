package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.Shooter;


public class DashboardTrigger extends Trigger {
    private String m_name;
   public DashboardTrigger(String name){
    m_name = name;

 }
    @Override
    public boolean get() {
     //   return SmartDashboard.getBoolean("Shooter/RPM Mode", false);
       return SmartDashboard.getBoolean(m_name, false);
    }

}
