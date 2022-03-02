package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.OIConstants;

public class OI {
    private static OI oi;

    private final Joystick leftStick = new Joystick(OIConstants.LEFT_STICK_PORT);
    private final Joystick rightStick = new Joystick(OIConstants.RIGHT_STICK_PORT);
    private final XboxController controller = new XboxController(OIConstants.XBOX_CONTROLLER_PORT);

    /**
     * Returns number [-1.0, 1.0] of left joystick input
     *
     * @return double left stick Y axis
     */
    public double getLeftY() {
        return leftStick.getY();
    }

    /**
     * Returns number [-1.0, 1.0] of right joystick input
     *
     * @return double right stick Y axis
     */
    public double getRightY() {
        return rightStick.getY();
    }

    /**
     * Returns state of intake binding as a double
     *
     * @return double 1 if intake is pressed, else 0
     */
    public double getIntake() {
        return controller.getRawButtonPressed(OIConstants.INTAKE_BUTTON) ? 1 : 0;
    }

    /**
     * Returns state of wrist up binding as a double
     *
     * @return double 1 if wrist up is pressed, else 0
     */
    public double getWristUp() {
        return controller.getRawButtonPressed(OIConstants.WRIST_UP_BUTTON) ? 1 : 0;
    }

    /**
     * Returns state of wrist down binding as a double
     *
     * @return double 1 if wrist down is pressed, else 0
     */
    public double getWristDown() {
        return controller.getRawButtonPressed(OIConstants.WRIST_DOWN_BUTTON) ? 1 : 0;
    }

    /**
     * Returns shooter priority bind, forcing driver control during shooting
     *
     * @return true if both triggers are actuated
     */
    public boolean getShooterPriority() {
        return leftStick.getTrigger() && rightStick.getTrigger();
    }

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
