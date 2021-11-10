package com.Kazesu.AntiMartyrdom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main
{
    public static final String MODID = "antimartyrdom";
    public static final String VERSION = "1.0";
    
    public static int SpawnYLevel = 0;
    public static boolean sent = true;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onChatMessageReceived(ClientChatReceivedEvent event)
    {
    	ChatComponentText message = (ChatComponentText) event.message;
    	String str = message.getUnformattedTextForChat().toLowerCase().replaceAll("\u00a7[\\w]", "");
    	if (str.startsWith("pit! latest update:"))
    	{
    		SpawnYLevel = (int)(Minecraft.getMinecraft().thePlayer.posY - 3);
    		sent = false;
    	}
    }
    
    @SubscribeEvent
    public void onTick(PlayerTickEvent event)
    {
    	if (SpawnYLevel == 0) {return;}
    	if (sent) { return; }
    	boolean hasCreepers = Minecraft.getMinecraft().theWorld.loadedEntityList.stream().anyMatch(e -> Minecraft.getMinecraft().thePlayer.getDistanceSqToEntity(e) <= 225 && e instanceof EntityCreeper);
    	boolean inSpawn = Minecraft.getMinecraft().thePlayer.posY >= SpawnYLevel;
    	if (hasCreepers && inSpawn)
    	{
    		Minecraft.getMinecraft().thePlayer.sendChatMessage("/l");
    		sent = true;
    	}
    }
}
