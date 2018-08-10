package water;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class WaterCanvas extends Canvas
{
	private WaterSimulation sim;
	private BufferedImage img;
	
	public WaterCanvas(WaterSimulation waterSim)
	{
		sim = waterSim;
		
		img = new BufferedImage(
			sim.getWidth(),
			sim.getHeight(),
			BufferedImage.TYPE_INT_ARGB);
		sim.drawToImage(img);
		
		WaterMouseListener mouseListener = new WaterMouseListener();
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
		
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}
	
	public void advance()
	{
		sim.advance();
		sim.drawToImage(img);
		repaint();
	}
	
	@Override
	public void paint(Graphics g)
	{
		BufferStrategy bs = getBufferStrategy();
		if (bs == null)
		{
			createBufferStrategy(2);
			bs = getBufferStrategy();
		}
		
		do
		{
			do
			{
				Graphics gBuffer = bs.getDrawGraphics();
				int width = getWidth();
				int height = getHeight();
				gBuffer.drawImage(img, 0, 0, width, height, Color.MAGENTA, null);
				gBuffer.dispose();
			} while (bs.contentsRestored());
			
			bs.show();
		} while (bs.contentsLost());
	}
	
	@Override
	public void update(Graphics g)
	{
		paint(g);
	}
	
	private class WaterMouseListener extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent e)
		{
			createRippleAtCursor(e);
		}
		
		@Override
		public void mouseDragged(MouseEvent e)
		{
			createRippleAtCursor(e);
		}
		
		private void createRippleAtCursor(MouseEvent e)
		{
			float xNorm = (float)e.getX() / getWidth();
			float yNorm = (float)e.getY() / getHeight();
			int x = (int)(xNorm * sim.getWidth());
			int y = (int)(yNorm * sim.getHeight());
			
			if (SwingUtilities.isLeftMouseButton(e))
				sim.setHeightAt(x, y, 1.0f);
			else if (SwingUtilities.isRightMouseButton(e))
				sim.setVelocityAt(x, y, 1.0f);
			else if (SwingUtilities.isMiddleMouseButton(e))
				sim.setHeightAt(x, y, Float.NaN);
		}
	}
}
