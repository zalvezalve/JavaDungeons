package j0sh.javadungeons.gui;

import net.minecraft.client.gui.screen.ingame.*;
import com.mojang.blaze3d.systems.RenderSystem;

import j0sh.javadungeons.JavaDungeons;
import j0sh.javadungeons.container.DungeonsTransformerContainer;
import j0sh.javadungeons.recipe.DungeonsTransformerRecipe;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class DungeonsTransformerScreen extends ContainerScreen<DungeonsTransformerContainer> {
   private static final Identifier TEXTURE = new Identifier(JavaDungeons.MOD_ID, "textures/gui/container/dungeons_transformer.png");
   private float scrollAmount;
   private boolean mouseClicked;
   private int scrollOffset;
   private boolean canCraft;

   public DungeonsTransformerScreen(DungeonsTransformerContainer container, PlayerInventory inventory, Text title) {
      super(container, inventory, title);
      container.setContentsChangedListener(this::onInventoryChange);
   }

   public void render(int mouseX, int mouseY, float delta) {
      super.render(mouseX, mouseY, delta);
      this.drawMouseoverTooltip(mouseX, mouseY);
   }

   protected void drawForeground(int mouseX, int mouseY) {
      this.font.draw(this.title.asFormattedString(), 8.0F, 4.0F, 0xFFFFFF);
      this.font.draw(this.playerInventory.getDisplayName().asFormattedString(), 8.0F, (float)(this.containerHeight - 94), 0xFFFFFF);
   }

   protected void drawBackground(float delta, int mouseX, int mouseY) {
      this.renderBackground();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.minecraft.getTextureManager().bindTexture(TEXTURE);
      int i = this.x;
      int j = this.y;
      this.blit(i, j, 0, 0, this.containerWidth, this.containerHeight);
      int k = (int)(41.0F * this.scrollAmount);
      this.blit(i + 119, j + 15 + k, 176 + (this.shouldScroll() ? 0 : 12), 0, 12, 15);
      int l = this.x + 52;
      int m = this.y + 14;
      int n = this.scrollOffset + 12;
      this.renderRecipeBackground(mouseX, mouseY, l, m, n);
      this.renderRecipeIcons(l, m, n);
   }

   private void renderRecipeBackground(int mouseX, int mouseY, int x, int y, int scrollOffset) {
      for(int i = this.scrollOffset; i < scrollOffset && i < ((DungeonsTransformerContainer)this.container).getAvailableRecipeCount(); ++i) {
         int j = i - this.scrollOffset;
         int k = x + j % 4 * 16;
         int l = j / 4;
         int m = y + l * 18 + 2;
         int n = this.containerHeight;
         if (i == ((DungeonsTransformerContainer)this.container).getSelectedRecipe()) {
            n += 18;
         } else if (mouseX >= k && mouseY >= m && mouseX < k + 16 && mouseY < m + 18) {
            n += 36;
         }

         this.blit(k, m - 1, 0, n, 16, 18);
      }

   }

   private void renderRecipeIcons(int x, int y, int scrollOffset) {
      List<DungeonsTransformerRecipe> list = ((DungeonsTransformerContainer)this.container).getAvailableRecipes();

      for(int i = this.scrollOffset; i < scrollOffset && i < ((DungeonsTransformerContainer)this.container).getAvailableRecipeCount(); ++i) {
         int j = i - this.scrollOffset;
         int k = x + j % 4 * 16;
         int l = j / 4;
         int m = y + l * 18 + 2;
         this.minecraft.getItemRenderer().renderGuiItem(((DungeonsTransformerRecipe)list.get(i)).getOutput(), k, m);
      }

   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      this.mouseClicked = false;
      if (this.canCraft) {
         int i = this.x + 52;
         int j = this.y + 14;
         int k = this.scrollOffset + 12;

         for(int l = this.scrollOffset; l < k; ++l) {
            int m = l - this.scrollOffset;
            double d = mouseX - (double)(i + m % 4 * 16);
            double e = mouseY - (double)(j + m / 4 * 18);
            if (d >= 0.0D && e >= 0.0D && d < 16.0D && e < 18.0D && ((DungeonsTransformerContainer)this.container).onButtonClick(this.minecraft.player, l)) {
               MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
               this.minecraft.interactionManager.clickButton(((DungeonsTransformerContainer)this.container).syncId, l);
               return true;
            }
         }

         i = this.x + 119;
         j = this.y + 9;
         if (mouseX >= (double)i && mouseX < (double)(i + 12) && mouseY >= (double)j && mouseY < (double)(j + 54)) {
            this.mouseClicked = true;
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      if (this.mouseClicked && this.shouldScroll()) {
         int i = this.y + 14;
         int j = i + 54;
         this.scrollAmount = ((float)mouseY - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
         this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0.0F, 1.0F);
         this.scrollOffset = (int)((double)(this.scrollAmount * (float)this.getMaxScroll()) + 0.5D) * 4;
         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
      }
   }

   public boolean mouseScrolled(double d, double e, double amount) {
      if (this.shouldScroll()) {
         int i = this.getMaxScroll();
         this.scrollAmount = (float)((double)this.scrollAmount - amount / (double)i);
         this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0.0F, 1.0F);
         this.scrollOffset = (int)((double)(this.scrollAmount * (float)i) + 0.5D) * 4;
      }

      return true;
   }

   private boolean shouldScroll() {
      return this.canCraft && ((DungeonsTransformerContainer)this.container).getAvailableRecipeCount() > 12;
   }

   protected int getMaxScroll() {
      return (((DungeonsTransformerContainer)this.container).getAvailableRecipeCount() + 4 - 1) / 4 - 3;
   }

   private void onInventoryChange() {
      this.canCraft = ((DungeonsTransformerContainer)this.container).canCraft();
      if (!this.canCraft) {
         this.scrollAmount = 0.0F;
         this.scrollOffset = 0;
      }

   }
}
