package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
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
    public double getRightX() {
        return rightStick.getX();
    }

    public double getRightY() {
        return rightStick.getY();
    }

    /**
     * Returns power for left climber 
     * 1 or 1 if stick is past deadband in both directions
     * 0 if stick is within deadband
     * 
     * @return double controller left joystick power
     */
    public double getLeftClimberPower() {
        double power = controller.getRawAxis(1);

        if (power > OIConstants.DEADBAND * 2) {
            return 1;
        } else if (power < OIConstants.DEADBAND * -2) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Returns power for right climber with deadband
     * 1 or 1 if stick is past deadband in both directions
     * 0 if stick is within deadband
     * 
     * @return double controller left joystick power
     */
    public double getRightClimberPower() {
        double power = controller.getRawAxis(5);

        if (power > OIConstants.DEADBAND * 2) {
            return 1;
        } else if (power < OIConstants.DEADBAND * -2) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Returns state of intake binding as a double
     
     * @return int 1 if outtake is pressed, else 0
     */
    public int getOuttake() {
        if (controller.getAButton()) {
            return 1;
        }
        return 0;
    }

    /**
     * Returns state of outtake binding as a bool 
     * 
     * @return int 1 if intake is pressed else, 0
     */
    public int getIntake() {
        return (controller.getYButton() /*|| controller.getXButton() || controller.getBButton()*/) ? 1 : 0;
    }

    /**
     * Returns state of wrist up binding as a double
     *
     * @return double 1 if wrist up is pressed, else 0
     */
    public double getWristUp() {
        if (controller.getRawAxis(OIConstants.WRIST_UP_AXIS) > 0.5) {
            return 1;
        }
        return 0;
    }

    /**
     * Returns state of wrist down binding as a double
     *
     * @return double 1 if wrist down is pressed, else 0
     */
    public double getWristDown() {
        if (controller.getRawAxis(OIConstants.WRIST_DOWN_AXIS) > 0.5) {
            return 1;
        }
        return 0;
    }

    /**
     * Returns feeder up bind
     * 
     * @return double 1 if feeder up is pushed
     */
    public double getFeederUp() {
        if (controller.getPOV() == 0) {
            return 1;
        }
        return 0;
    }

    /**
     * Returns feeder down bind
     * 
     * @return double 1 if feeder down is pushed
     */
    public double getFeederDown() {
        if (controller.getPOV() == 180) {
            return 1;
        }
        return 0;
    }

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
    public double getRightXDeadband() {
        double rightX = getRightX();
        if (Math.abs(rightX) < OIConstants.DEADBAND) {
            return 0;
        }

        return rightX;
    }

    public double getRightYDeadband() {
        double rightY = getRightY();
        if (Math.abs(rightY) < OIConstants.DEADBAND) {
            return 0;
        }

        return rightY;
    }


    /**
     * Sets rumble value of controller to specified intensity
     * 
     * @param intensity double value to set up to 1
     */
    public void setRumble(double intensity) {
        controller.setRumble(RumbleType.kLeftRumble, intensity);
        controller.setRumble(RumbleType.kRightRumble, intensity);
    }

}
