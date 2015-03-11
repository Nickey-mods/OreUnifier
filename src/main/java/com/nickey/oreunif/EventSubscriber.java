package com.nickey.oreunif;

import java.util.ArrayList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventSubscriber {
	@SubscribeEvent
	public void onUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase e = event.entityLiving;
		if(e instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer)e;
			InventoryPlayer inv = p.inventory;
			for(int i = 0;i < inv.getSizeInventory();i++)
			{
				ItemStack is = inv.getStackInSlot(i);
				if(is == null)
				{
					 continue;
				}
				int[] oreIDs = OreDictionary.getOreIDs(is);
				int oreID = oreIDs[0];
				ArrayList<ItemStack> rej = OreDictionary.getOres(OreDictionary.getOreName(oreID));
				if(rej.size() > 0)
				{
					ItemStack dest = rej.get(0);
					dest.stackSize = is.stackSize;
					if(isUnifiable(dest))
					{
						inv.setInventorySlotContents(i, dest);
					}
				}
			}

		}
	}
	public boolean isUnifiable(ItemStack is)
	{
		if(OreUnifier.blackList.trim() == "")
		{
			return true;
		}
		String[] blacks = OreUnifier.blackList.split(",");
		for(String Orename : blacks)
		{
			int[] ids = OreDictionary.getOreIDs(is);
			for(int i : ids)
			{
				String name = OreDictionary.getOreName(i);
				if(name.contains(Orename))
				{
					return false;
				}
			}
		}
		return true;
	}
}
