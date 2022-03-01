package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Constants.OIConstants;

public class OI {
    private static OI oi;

    private final Joystick leftStick = new Joystick(OIConstants.LEFT_STICK_PORT);
    private final Joystick rightStick = new Joystick(OIConstants.RIGHT_STICK_PORT);

    public double getLeftY() { return leftStick.getY(); }
    public double getRightY() { return rightStick.getY(); }

    /**
     * Returns the value of left joystick with values within deadband truncated
     * 
     * @return double value of joystick
     */
    public double getLeftYDeadband() {
        double y = getLeftY();

        if (Math.abs(y) < OIConstants.DEADBAND) {
            y = 0;
        }
        
        return y;
    }

    /**
     * Returns the value of left joystick with values within deadband truncated
     * 
     * @return double value of joystick
     */
    public double getRightYDeadband() {
        double y = getRightY();

        if (Math.abs(y) < OIConstants.DEADBAND) {
            y = 0;
        }
        
        return y;
    }

    /**
     * Singleton for getting instance of operator input
     * 
     * @return OI object of self
     */
    public static OI getInstance() {
        if (oi == null) {
            oi = new OI();
        }
        return oi;
    }

    private OI() {
    }
}
