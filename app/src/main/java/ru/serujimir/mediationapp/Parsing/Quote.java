package ru.serujimir.mediationapp.Parsing;

import android.graphics.drawable.Drawable;

public class Quote {
    public Quote(String quoteHeader, String quoteDescription, String quoteIcon) {
        this.quoteHeader = quoteHeader;
        this.quoteDescription = quoteDescription;
        this.quoteIcon = quoteIcon;
    }

    public String getQuoteHeader() {
        return quoteHeader;
    }

    public void setQuoteHeader(String quoteHeader) {
        this.quoteHeader = quoteHeader;
    }

    public String getQuoteDescription() {
        return quoteDescription;
    }

    public void setQuoteDescription(String quoteDescription) {
        this.quoteDescription = quoteDescription;
    }

    public String getQuoteIcon() {
        return quoteIcon;
    }

    public void setQuoteIcon(String quoteIcon) {
        this.quoteIcon = quoteIcon;
    }

    String quoteHeader;
    String quoteDescription;
    String quoteIcon;
}
