package org.duckdns.fossilfind.scifi.util.helpers;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class KeyboardHelpers
{
	private static final long WINDOW = Minecraft.getInstance().getWindow().getWindow();
	
	@OnlyIn(Dist.CLIENT)
	public static boolean shift()
	{
		return InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_SHIFT) || InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_SHIFT);
	}
}