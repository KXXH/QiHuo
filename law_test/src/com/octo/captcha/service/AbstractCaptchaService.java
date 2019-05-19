//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.octo.captcha.service;

import com.octo.captcha.Captcha;
import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.image.gimpy.Gimpy;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.CaptchaStore;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractCaptchaService implements CaptchaService {
    protected CaptchaStore store;
    protected CaptchaEngine engine;
    protected Log logger;

    protected AbstractCaptchaService(CaptchaStore captchaStore, CaptchaEngine captchaEngine) {
        if(captchaEngine != null && captchaStore != null) {
            this.engine = captchaEngine;
            this.store = captchaStore;
            this.logger = LogFactory.getLog(this.getClass());
            this.logger.info("Init " + this.store.getClass().getName());
            this.store.initAndStart();
        } else {
            throw new IllegalArgumentException("Store or gimpy can\'t be null");
        }
    }

    public Object getChallengeForID(String ID) throws CaptchaServiceException {
        return this.getChallengeForID(ID, Locale.getDefault());
    }

    public Object getChallengeForID(String ID, Locale locale) throws CaptchaServiceException {
        Captcha captcha;
        if(!this.store.hasCaptcha(ID)) {
            captcha = this.generateAndStoreCaptcha(locale, ID);
        } else {
            captcha = this.store.getCaptcha(ID);
            if(captcha == null) {
                captcha = this.generateAndStoreCaptcha(locale, ID);
            } else if(captcha.hasGetChalengeBeenCalled().booleanValue()) {
                captcha = this.generateAndStoreCaptcha(locale, ID);
            }
        }

        Object challenge = this.getChallengeClone(captcha);
        captcha.disposeChallenge();
        return challenge;
    }

    public String getQuestionForID(String ID, Locale locale) throws CaptchaServiceException {
        Captcha captcha;
        if(!this.store.hasCaptcha(ID)) {
            captcha = this.generateAndStoreCaptcha(locale, ID);
        } else {
            captcha = this.store.getCaptcha(ID);
            if(captcha == null) {
                captcha = this.generateAndStoreCaptcha(locale, ID);
            } else if(locale != null) {
                Locale storedlocale = this.store.getLocale(ID);
                if(!locale.equals(storedlocale)) {
                    captcha = this.generateAndStoreCaptcha(locale, ID);
                }
            }
        }

        return captcha.getQuestion();
    }

    public String getQuestionForID(String ID) throws CaptchaServiceException {
        return this.getQuestionForID(ID, Locale.getDefault());
    }

    public Boolean validateResponseForID(String ID, Object response) throws CaptchaServiceException {
        if(!this.store.hasCaptcha(ID)) {
            throw new CaptchaServiceException("Invalid ID, could not validate unexisting or already validated captcha");
        } else {
            Boolean valid = this.store.getCaptcha(ID).validateResponse(response);
            System.out.println("正在检查ID为ID"+ID+"的验证码，验证码答案为"+((Gimpy)this.store.getCaptcha(ID)).response+",输入的答案为"+response);
            this.store.removeCaptcha(ID);
            return valid;
        }
    }

    protected Captcha generateAndStoreCaptcha(Locale locale, String ID) {
        Captcha captcha = this.engine.getNextCaptcha(locale);
        this.store.storeCaptcha(ID, captcha, locale);
        return captcha;
    }

    protected abstract Object getChallengeClone(Captcha var1);
}
