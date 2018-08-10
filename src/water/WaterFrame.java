package water;

import java.awt.*;
import javax.swing.*;

public class WaterFrame extends JFrame
{
	private final WaterCanvas waterCanvas;
	private final Timer simTimer;
	
	private static final int SIM_INTERVAL = 1000 / 30;
	
	public WaterFrame()
	{
		super("Water Simulation");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		waterCanvas = new WaterCanvas(new WaterSimulation(512, 512));
		add(waterCanvas);
		
		simTimer = new Timer(SIM_INTERVAL, e -> waterCanvas.advance());
		simTimer.start();
		
		setSize(512, 512);
	}
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(() -> new WaterFrame().setVisible(true));
	}
}
