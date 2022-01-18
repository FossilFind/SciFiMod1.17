package org.duckdns.fossilfind.scifi.client.gui.screens.inventory;

import org.duckdns.fossilfind.scifi.SciFi;
import org.duckdns.fossilfind.scifi.inventory.RefineryMenu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class RefineryScreen extends AbstractContainerScreen<RefineryMenu>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(SciFi.MODID, "textures/gui/refinery.png");
	
	public RefineryScreen(RefineryMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title);
	}
	
	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks)
	{
		renderBackground(stack);
		super.render(stack, mouseX, mouseY, partialTicks);
		renderTooltip(stack, mouseX, mouseY);
	}
	
	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY)
	{
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.setShaderTexture(0, TEXTURE);
		
		blit(stack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
		
		if(menu.isLit())
			blit(stack, leftPos + 56, topPos + 48 - menu.getLitProgress(), 176, 12 - menu.getBurnProgress(), 14, menu.getBurnProgress() + 1);
		
		blit(stack, leftPos + 79, topPos + 34, 176, 14, menu.getBurnProgress() + 1, 16);
	}
}