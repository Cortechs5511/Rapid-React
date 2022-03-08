package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.OIConstants;

public class OI {
    private static OI oi;

    public final Joystick leftStick = new Joystick(OIConstants.LEFT_STICK_PORT);
    public final Joystick rightStick = new Joystick(OIConstants.RIGHT_STICK_PORT);
    public final XboxController controller = new XboxController(OIConstants.XBOX_CONTROLLER_PORT);

    private OI() {
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
    public int getOuttake() {
        return controller.getXButton() ? 1 : 0;
    }

    /**
     * Returns state of outtake binding as a bool 
     * 
     * @return int 1 if intake is pressed else 0
     */
    public int getIntake() {
        return controller.getYButton() ? 1 : 0;
    }

    /**
     * Returns state of wrist up binding as a double
     *
     * @return int 1 if wrist up is pressed, else 0
     */
    public int getWristUp() {
        return controller.getRawAxis(OIConstants.WRIST_UP_AXIS) > 0.5 ? 1 : 0;
    }

    /**
     * Returns state of wrist down binding as a double
     *
     * @return int 1 if wrist down is pressed, else 0
     */
    public int getWristDown() {
        return controller.getRawAxis(OIConstants.WRIST_DOWN_AXIS) > 0.5 ? 1 : 0;
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
        double leftY = getLeftY();
        if (Math.abs(leftY) < OIConstants.DEADBAND) {
            return 0;
        }

        return leftY;
    }

    /**
     * Returns the value of left joystick with values within deadband truncated
     *
     * @return double value of joystick
     */
    public double getRightYDeadband() {
        double rightY = getRightY();
        if (Math.abs(rightY) < OIConstants.DEADBAND) {
            return 0;
        }

        return rightY;
    }


}
