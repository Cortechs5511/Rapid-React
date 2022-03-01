package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Limelight;

public class LightToggle extends InstantCommand {
	private final Limelight m_limelight;

	public LightToggle(Limelight limelight) {
		m_limelight = limelight;
		addRequirements(limelight);
	}

	@Override
	public void initialize() {
        m_limelight.setLightStatus(m_limelight.getLightStatus() == 3 ? 1 : 3);
	}
}