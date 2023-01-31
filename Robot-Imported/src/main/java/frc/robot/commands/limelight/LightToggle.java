package frc.robot.commands.limelight;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Limelight;

public class LightToggle extends InstantCommand {
	private final Limelight limelight;

	public LightToggle(Limelight limelight) {
		this.limelight = limelight;
		addRequirements(limelight);
	}

	@Override
	public void initialize() {
        limelight.setLight(!limelight.getLightStatus());
	}
}