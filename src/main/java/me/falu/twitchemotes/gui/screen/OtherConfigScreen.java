package me.falu.twitchemotes.gui.screen;

import me.falu.twitchemotes.TwitchEmotes;
import me.falu.twitchemotes.TwitchEmotesOptions;
import me.falu.twitchemotes.emote.EmoteConstants;
import me.falu.twitchemotes.gui.widget.LimitlessBooleanButtonWidget;
import me.falu.twitchemotes.gui.widget.LimitlessButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class OtherConfigScreen extends Screen {
    private TextFieldWidget channelNameField;
    private boolean changedChannelName = false;

    public OtherConfigScreen() {
        super(new LiteralText("Other Config"));
    }

    @Override
    protected void init() {
        int gap = 10;
        int wideButtonWidth = (int) (this.width * 0.8F);
        int smallButtonWidth = wideButtonWidth / 2 - gap / 2;
        int buttonHeight = this.height / 4;
        int textFieldHeight = 26;

        this.channelNameField = this.addChild(new TextFieldWidget(
                this.textRenderer,
                this.width / 2 - wideButtonWidth / 2 + 2,
                this.height / 2 - buttonHeight - textFieldHeight - gap - 4,
                wideButtonWidth - 4,
                textFieldHeight,
                new LiteralText("")
        ));
        this.channelNameField.write(TwitchEmotesOptions.TWITCH_CHANNEL_NAME.getValue());
        this.channelNameField.setChangedListener(s -> this.changedChannelName = !s.equals(TwitchEmotesOptions.TWITCH_CHANNEL_NAME.getValue()));

        this.addButton(new LimitlessBooleanButtonWidget(
                this.width / 2 - smallButtonWidth - gap / 2,
                this.height / 2 - buttonHeight - gap / 2,
                smallButtonWidth,
                buttonHeight,
                new LiteralText("Show Badges"),
                EmoteConstants.PARASOCIAL,
                TwitchEmotesOptions.SHOW_BADGES
        ));
        this.addButton(new LimitlessBooleanButtonWidget(
                this.width / 2 + gap / 2,
                this.height / 2 - buttonHeight - gap / 2,
                smallButtonWidth,
                buttonHeight,
                new LiteralText("Show Colors"),
                EmoteConstants.PEEPO_DJ,
                TwitchEmotesOptions.SHOW_USER_COLORS
        ));
        this.addButton(new LimitlessBooleanButtonWidget(
                this.width / 2 - smallButtonWidth - gap / 2,
                this.height / 2 + gap / 2,
                smallButtonWidth,
                buttonHeight,
                new LiteralText("ppHop Overlay"),
                EmoteConstants.PP_HOP,
                TwitchEmotesOptions.SHOW_PP_HOP_OVERLAY
        ));
        this.addButton(new LimitlessBooleanButtonWidget(
                this.width / 2 + gap / 2,
                this.height / 2 + gap / 2,
                smallButtonWidth,
                buttonHeight,
                new LiteralText("Chat Back"),
                EmoteConstants.SNIFFA,
                TwitchEmotesOptions.CHAT_BACK
        ));

        this.addButton(new LimitlessButtonWidget(
                this.width / 2 - wideButtonWidth / 2,
                this.height - 20 - 10,
                wideButtonWidth,
                20,
                ScreenTexts.DONE,
                null,
                b -> this.onClose()
        ));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        if (this.channelNameField != null) {
            this.channelNameField.render(matrices, mouseX, mouseY, delta);
            this.drawTextWithShadow(matrices, this.textRenderer, new LiteralText("Channel Name (Optional):"), this.channelNameField.x, this.channelNameField.y - 4 - this.textRenderer.fontHeight, 0xFFFFFF);
        }
    }

    @Override
    public void onClose() {
        if (this.changedChannelName) {
            TwitchEmotesOptions.TWITCH_CHANNEL_NAME.setValue(this.channelNameField.getText());
            TwitchEmotes.reload();
        }
        if (this.client != null) {
            this.client.openScreen(new MenuSelectionScreen());
        }
    }
}
