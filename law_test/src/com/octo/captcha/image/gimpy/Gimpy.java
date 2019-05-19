//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.octo.captcha.image.gimpy;

import com.octo.captcha.image.ImageCaptcha;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Gimpy extends ImageCaptcha implements Serializable {
    public String response;
    private boolean caseSensitive;

    Gimpy(String question, BufferedImage challenge, String response, boolean caseSensitive) {
        super(question, challenge);
        this.caseSensitive = true;
        this.response = response;
        this.caseSensitive = caseSensitive;
    }

    Gimpy(String question, BufferedImage challenge, String response) {
        this(question, challenge, response, true);
    }

    public final Boolean validateResponse(Object response) {
        return null != response && response instanceof String?this.validateResponse((String)response):Boolean.FALSE;
    }

    private final Boolean validateResponse(String response) {
        return Boolean.valueOf(this.caseSensitive?response.equals(this.response):response.equalsIgnoreCase(this.response));
    }
}
