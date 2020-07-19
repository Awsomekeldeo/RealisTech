package com.awsome.techmod.util.worldgen;

import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class VanillaGenOverride {
	
	@SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
	public void onOrePopulate(OreGenEvent.GenerateMinable event) {
		if (event.getType() == OreGenEvent.GenerateMinable.EventType.IRON) {
            event.setResult(Event.Result.DENY);
        }
        if (event.getType() == OreGenEvent.GenerateMinable.EventType.GOLD) {
            event.setResult(Event.Result.DENY);
        }
        if (event.getType() == OreGenEvent.GenerateMinable.EventType.DIAMOND) {
            event.setResult(Event.Result.DENY);
        }
        if (event.getType() == OreGenEvent.GenerateMinable.EventType.COAL) {
            event.setResult(Event.Result.DENY);
        }
        if (event.getType() == OreGenEvent.GenerateMinable.EventType.REDSTONE) {
            event.setResult(Event.Result.DENY);
        }
        if (event.getType() == OreGenEvent.GenerateMinable.EventType.LAPIS) {
            event.setResult(Event.Result.DENY);
        }
        if (event.getType() == OreGenEvent.GenerateMinable.EventType.QUARTZ) {
            event.setResult(Event.Result.DENY);
        }
        if (event.getType() == OreGenEvent.GenerateMinable.EventType.EMERALD) {
            event.setResult(Event.Result.DENY);
        }
	}
	
}