package water;

import java.awt.image.*;
import java.util.concurrent.*;

public class WaterSimulation
{
	private final int width;
	private final int height;
	private final float[][] heights;
	private final float[][] velocities;
	
	public WaterSimulation(int w, int h, InitialHeightGenerator initGen)
	{
		width = w;
		height = h;
		
		heights = new float[height][width];
		velocities = new float[height][width];
		
		for (int y = 0; y < height; ++y)
		{
			float yNorm = (float)y / height;
			for (int x = 0; x < width; ++x)
			{
				float xNorm = (float)x / width;
				heights[y][x] = initGen.generateHeight(xNorm, yNorm);
			}
		}
	}
	
	public WaterSimulation(int w, int h)
	{
		this(w, h,
			(x, y) -> ThreadLocalRandom.current().nextFloat() * 0.5f);
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	private float getHeightAt(int x, int y)
	{
		x = clamp(x, 0, width - 1);
		y = clamp(y, 0, height - 1);
		
		return heights[y][x];
	}
	
	private float getHeightAt(int x, int y, float ifNaN)
	{
		float h = getHeightAt(x, y);
		return !Float.isNaN(h)
			? h
			: ifNaN;
	}
	
	public void setHeightAt(int x, int y, float h)
	{
		x = clamp(x, 0, width - 1);
		y = clamp(y, 0, height - 1);
		
		heights[y][x] = h;
	}
	
	public void setVelocityAt(int x, int y, float v)
	{
		x = clamp(x, 0, width - 1);
		y = clamp(y, 0, height - 1);
		
		velocities[y][x] = v;
	}
	
	public void advance()
	{
		for (int y = 0; y < height; ++y)
		{
			for (int x = 0; x < width; ++x)
			{
				final float h = heights[y][x];
				velocities[y][x] += (getHeightAt(x-1, y, h)
					+ getHeightAt(x+1, y, h)
					+ getHeightAt(x, y-1, h)
					+ getHeightAt(x, y+1, h))
					/ 4 - h;
				velocities[y][x] *= 0.99;
			}
		}
		
		for (int y = 0; y < height; ++y)
		{
			for (int x = 0; x < width; ++x)
			{
				heights[y][x] += velocities[y][x];
			}
		}
	}
	
	public void drawToImage(BufferedImage output)
	{
		for (int y = 0; y < height; ++y)
		{
			for (int x = 0; x < width; ++x)
			{
				int rgb = packRGB(heights[y][x]);
				output.setRGB(x, y, rgb);
			}
		}
	}
	
	private static int clamp(int value, int min, int max)
	{
		if (value < min)
			return min;
		else if (value > max)
			return max;
		else
			return value;
	}
	
	private static int packRGB(float value)
	{
		int intVal = !Float.isNaN(value)
			? (int)(value * 255) & 0xFF
			: 0;
		int rgb = 0xFF000000;
		rgb = rgb | (intVal << 16) | (intVal << 8) | intVal;
		return rgb;
	}
}
