package ru.serujimir.mediationapp.Parsing;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.RequestBuilder;

public class Feeling {
    public Feeling(String feelingIcon, String feelingText) {
        this.feelingIcon = feelingIcon;
        this.feelingText = feelingText;
    }

    public String getFeelingIcon() {
        return feelingIcon;
    }

    public void setFeelingIcon(String feelingIcon) {
        this.feelingIcon = feelingIcon;
    }

    public String getFeelingText() {
        return feelingText;
    }

    public void setFeelingText(String feelingText) {
        this.feelingText = feelingText;
    }

    String feelingIcon;
    String feelingText;
}
