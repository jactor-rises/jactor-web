package com.github.jactor.rises.web.html;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("The Text Viewer")
class TextViewerTest {

    @DisplayName("should create html line breaks")
    @Test
    void willConvertLineBreaksIntoHtmlLineBreaks() {
        String converted = TextViewer.convertTextToHtml("\n\nsome.text");
        assertThat(converted).isEqualTo("<br><br>some.text");
    }

    @DisplayName("can be overridden")
    @Test
    void canBeOverridden() {
        new TextViewer() {
            {
                setInstance(this);
            }

            @Override
            protected String convertLineBreaks(String textToConvert) {
                setInstance(new TextViewer());
                return "is overridden";
            }
        };

        String overridden = TextViewer.convertTextToHtml(null);

        assertThat(overridden).isEqualTo("is overridden");
    }

    @DisplayName("should convert tabulators into three html no-break-spaces")
    @Test
    void willConvertTabulatorsIntoThreeHtmlNoBreakSpaces() {
        String converted = TextViewer.convertTextToHtml("\t\tsome.text");
        assertThat(converted).isEqualTo("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;some.text");
    }

    @DisplayName("should not fail when the text to convert is null")
    @Test
    void willNotFailIfTheTextToViewIsNull() {
        String converted = TextViewer.convertTextToHtml(null);
        assertThat(converted).isNull();
    }
}
