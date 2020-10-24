package awsome.realistech.util;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.concurrent.ThreadTaskExecutor;
import net.minecraft.util.concurrent.TickDelayedTask;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

public class GeneralUtils {
	
	public static final Random RAND = new Random();
	
	private static int[] tempBuffer = new int[256];
	
	public static void setTemperatureColorizer(int[] bufferIn) {
		tempBuffer = bufferIn;
	}
	
	public static int getTempFromColorMap(float temperature) {
		
		if (temperature < 473.0f) {
			return tempBuffer[0];
		}else if (temperature > 1500.0f) {
			return tempBuffer[255];
		}
		
		double i3 = (8.189f * Math.pow(10, -5)) * Math.pow(temperature, 3);
		double i2 = 0.0254d * Math.pow(temperature, 2);
		double i1 = 4.9449d * temperature;
		double iC = 472.4631;
		int i = (int) (i3 - i2 + i1 + iC);
		return tempBuffer[i];
		
	}
	
	public static void addFutureServerTask(World world, Runnable task, boolean forceFuture) {
		LogicalSide side = world.isRemote?LogicalSide.CLIENT: LogicalSide.SERVER;
		ThreadTaskExecutor<? super TickDelayedTask> tmp = LogicalSidedProvider.WORKQUEUE.get(side);
		if(forceFuture)
		{
			int tick;
			if(world.isRemote)
				tick = 0;
			else
				tick = ((MinecraftServer)tmp).getTickCounter();
			tmp.enqueue(new TickDelayedTask(tick, task));
		}
		else
			tmp.deferTask(task);	
	}
	
	public static void addFutureServerTask(World world, Runnable task)
	{
		addFutureServerTask(world, task, false);
	}
	
	public static <T> Set<T> findDuplicates(List<T> listContainingDuplicates)
	{ 
		final Set<T> setToReturn = new HashSet<>(); 
		final Set<T> set1 = new HashSet<>();

		for (T item : listContainingDuplicates)
		{
			if (!set1.add(item))
			{
				setToReturn.add(item);
			}
		}
		
		return setToReturn;
	}
}
