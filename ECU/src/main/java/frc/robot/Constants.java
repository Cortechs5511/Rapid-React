package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;

public final class Constants {
    // Change before competition -- inhibits extraneous SmartDashboard logging
    public static final boolean DIAGNOSTICS = true;
    public static final double DWELL_PERIOD = 0.5;

    public static class DriveConstants {
        public static final int LEFT_LEADER_ID = 10;
        public static final int LEFT_FOLLOWER_ID = 11;
        public static final int RIGHT_LEADER_ID = 20;
        public static final int RIGHT_FOLLOWER_ID = 21;

        public static final double VOLTAGE_COMPENSATION = 11;
        public static final IdleMode IDLE_MODE = IdleMode.kBrake;
        public static final double RAMP_RATE = 0.05;
        public static final int CURRENT_LIMIT = 80;

        public static final double PID_MIN_OUTPUT = 0;
        public static final double PID_MAX_OUTPUT = 0;
        public static final double PID_P = 0;
        public static final double PID_I = 0;
        public static final double PID_D = 0;
        public static final double PID_FF = 0;

        public static final double GEARING = (44f / 24f) * (68f / 11f);
        public static final double WHEEL_DIAMETER = Units.inchesToMeters(6);
        public static final double ENCODER_TO_METERS = WHEEL_DIAMETER / GEARING;
        public static final double DRIVE_VELOCITY_TOLERANCE = 1;

        public static final double ANGLE_P = 0.0;
        public static final double ANGLE_I = 0.0;
        public static final double ANGLE_D = 0.0;
        public static final double ANGLE_PID_LIMIT = 0.3;

        public static final double TRACK_WIDTH = 0.6858;
        public static final DifferentialDriveKinematics DRIVE_KINEMATICS = new DifferentialDriveKinematics(TRACK_WIDTH);

        // TODO: characterize
        public static final double KS = 0;
        public static final double KV = 0;
        public static final double KA = 0;
    }

    public static class AutoConstants {

        public static final double RAMSETE_BETA = 0;
        public static final double RAMSETE_ZETA = 0;

    }

    public static class OIConstants {
        public static final int LEFT_STICK_PORT = 0;
        public static final int RIGHT_STICK_PORT = 1;
        public static final int XBOX_CONTROLLER_PORT = 2;

        public static final double DEADBAND = 0.05;

        // TODO: Bind all buttons
        // TODO: Comment which controller each goes on for less confusion (low priority)
        public static final int OUTTAKE_BUTTON = 5; //Not used
        public static final int WRIST_UP_AXIS = 2;
        public static final int WRIST_DOWN_AXIS = 3;
        public static final int INTAKE_BUTTON = 4; //Not used
        public static final int SHOOT_BUTTON = 5;
        public static final int STOP_SHOOT_BUTTON = 6;
        public static final int LIGHTS_TOGGLE_BUTTON = 7;
        public static final int FLIP_BUTTON = 2;
        public static final int HALF_SPEED_BUTTON = 2;
    }

    public static class IntakeConstants {
        public static final int WRIST_ID = 30;
        public static final int INTAKE_ID = 31;

        public static final boolean INVERT_INTAKE = true;
        public static final boolean INVERT_WRIST = false;

        public static final int CURRENT_LIMIT = 40;
        public static final int PEAK_CURRENT_LIMIT = 60;
        public static final int WRIST_CURRENT_LIMIT = 20;
        public static final double RAMP_RATE = 0.25;
        public static final NeutralMode NEUTRAL_MODE = NeutralMode.Brake;

        public static final double INTAKE_POWER = -0.7;
        public static final double WRIST_POWER = 0.5;
        public static final double WRIST_DOWN_POWER = 0.05;
        public static final double WRIST_BUMP_THRESHOLD = -20;
    }

    public static class FeederConstants {
        public static final int TOWER_1_ID = 40;
        public static final int TOWER_2_ID = 41;

        public static final boolean INVERT_TOWER_1 = false;
        public static final boolean INVERT_TOWER_2 = true;

        public static final int CURRENT_LIMIT = 40;
        public static final int PEAK_CURRENT_LIMIT = 60;
        public static final double RAMP_RATE = 0.25;
        public static final NeutralMode NEUTRAL_MODE = NeutralMode.Brake;

        public static final int BOTTOM_SENSOR_PORT = 1;
        public static final int TOP_SENSOR_PORT = 2;

        public static final double TOWER_POWER = 0.5;

        public static final double EJECT_TIMEOUT = 5.0;
        public static final double FEED_TIMEOUT = 0.5;
    }

    public static class ShooterConstants {
        public static final int BOTTOM_SHOOTER_ID = 50;
        public static final int TOP_SHOOTER_ID = 51;

        public static final boolean INVERT_BOTTOM_SHOOTER = false;
        public static final boolean INVERT_TOP_SHOOTER = true;

        public static final double VOLTAGE_COMPENSATION = 11.5;
        public static final double LONG_RAMP_RATE = 1.5;
        public static final double SHORT_RAMP_RATE = 0.01;

        // getSelectedSensorVelocity: units (2048/rotation) per 100ms
        public static final double UNITS_PER_ROTATION = 2048;
        public static final double RPM_TO_UNITS = 600 / UNITS_PER_ROTATION;

        public static final NeutralMode NEUTRAL_MODE = NeutralMode.Coast;
        public static final SupplyCurrentLimitConfiguration CURRENT_LIMIT = new SupplyCurrentLimitConfiguration(true, 80, 0, 0);

        public static final double SHOOTER_RPM_TOLERANCE = 200.0;
        public static final double DWELL_TIME = 0.5;
        public static final double SHOOTER_TIMEOUT = 1.0;

        public static final double PID_P = 0;
        public static final double PID_I = 0;
        public static final double PID_D = 0;
        public static final double PID_FF = 0;
    }

}


