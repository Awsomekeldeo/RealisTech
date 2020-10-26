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
		}
		
		if (temperature > 1400.0f) {
			return tempBuffer[253];
		}
		
		double tempSquared = Math.pow(temperature, 2);
		
		double j1 = Math.pow(10, 17);
		double j2 = Math.pow(10, 20);
		double j3 = Math.pow(10, 23);
		
		double j4 = Math.pow(10, 13);
		double j5 = Math.pow(10, 16);
		
		double j6 = Math.pow(10, 7);
		
		double k1 = Math.sqrt((1.81061 * j1 * tempSquared) - (2.90679 * j2 * temperature) + (1.20749 * j3));
		
		double l1 = Math.cbrt((42551.3 * k1) - (1.81061 * j4 * temperature) + (1.45339 * j5));
		
		int i = (int) (103.391 - 0.000696046 * l1 + (1.35603 * j6) / l1);
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
