package com.bajookie.echoes_of_the_elders.screen.client;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

@Environment(EnvType.CLIENT)
public class RelicomiconScreen extends Screen {
    public static final Identifier BOOK_TEXTURE = new Identifier(MOD_ID, "textures/gui/relicomicon.png");
    private int pageIndex;
    private PageTurnWidget nextPageButton;
    private PageTurnWidget previousPageButton;

    private TexturedButtonWidget relicsPageButton;

    public RelicomiconScreen(Text title) {
        super(title);
        pageIndex = 0;
    }


    protected void addPageButtons() {
        int i = (this.width - 412) / 2;
        int j = (this.height) / 2;
        this.nextPageButton = this.addDrawableChild(new PageTurnWidget((this.width + 208) / 2, j, true, button -> this.goToNextPage(), true));
        this.previousPageButton = this.addDrawableChild(new PageTurnWidget((this.width - 284) / 2, j, false, button -> this.goToPreviousPage(), true));
        this.relicsPageButton = this.addDrawableChild(new TexturedButtonWidget());

        this.updatePageButtons();
    }

    private void updatePageButtons() {
        this.nextPageButton.visible = this.pageIndex < this.getPageCount() - 1;
        this.previousPageButton.visible = this.pageIndex > 0;
    }

    private int getPageCount() {
        return ModItems.registeredModItems.size();
    }

    protected void goToPreviousPage() {
        if (this.pageIndex > 0) {
            --this.pageIndex;
        }
        this.updatePageButtons();
    }

    protected void goToNextPage() {
        if (this.pageIndex < this.getPageCount() - 1) {
            ++this.pageIndex;
        }
        this.updatePageButtons();
    }

    @Override
    protected void init() {
        this.addPageButtons();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        switch (keyCode) {
            case 266: {
                this.previousPageButton.onPress();
                return true;
            }
            case 267: {
                this.nextPageButton.onPress();
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        if (pageIndex==0){

        } else {
            int left_page = (int) Math.floor((this.width) * 0.35);
            int right_page = (int) Math.floor((this.width) * 0.55);
            int page_header = (int) Math.floor((this.height) * 0.28);
            int page_body = (int) Math.floor((this.height) * 0.33);
            int page_footer = (int) Math.floor((this.height) * 0.48);

            int icon_x = (int) Math.floor((this.width) * 0.43);
            int icon_y = (int) Math.floor((this.height) * 0.28);
            Item item = ModItems.registeredModItems.get(this.pageIndex);

            var tooltip = new ArrayList<Text>();
            item.appendTooltip(item.getDefaultStack(), null, tooltip, new TooltipContext() {
                @Override
                public boolean isAdvanced() {
                    return true;
                }

                @Override
                public boolean isCreative() {
                    return true;
                }
            });
            //Title
            context.drawText(this.textRenderer, item.getName(), left_page, page_header, 0, false);
            //Description
            for(int i=0;i<tooltip.size();i++){
                context.drawText(this.textRenderer, tooltip.get(i), left_page, page_body+(i*10), 0, false);
            }
            //Icon
            var matrix = context.getMatrices();
            matrix.push();
            matrix.translate(icon_x, icon_y, 0);
            matrix.scale(2, 2, 1);
            context.drawItem(item.getDefaultStack(), 0, 0);
            matrix.pop();
        }
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        context.drawTexture(BOOK_TEXTURE, (this.width) / 3, (this.height) / 4, 0, 0, this.width/2, this.height/2, this.width/2, this.height/2);
    }
}
