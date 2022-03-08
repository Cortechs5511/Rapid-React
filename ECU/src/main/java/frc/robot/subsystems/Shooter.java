package frc.robot.subsystems;

/**
 * @deprecated Do not see a need for an interface only used in FalconShooter.java
 */
@Deprecated
public interface Shooter {
    double getBottomSpeed();

    /**
     * Sets the shooter to a certain speed
     *
     * @param speed target velocity, RPM
     */
    void setBottomSpeed(double speed);

    /**
     * Sets shooter to open loop output
     *
     * @param power double speed [0.0, 1.0]
     */
    void setBottomPower(double power);

    // TODO: see comment in falcon shooter
    void setTopPower(double power);

    /**
     * Returns the most recently set PID reference value
     *
     * @return double reference value, RPM
     */

    double getBottomSetpoint();

    /**
     * Sets shooter controllers to adopt given ramp rate
     *
     * @param rate double ramp rate in seconds from neutral to full
     */
    void setRampRate(double rate);
}
