//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.octo.captcha.service;

import com.octo.captcha.Captcha;
import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.service.AbstractCaptchaService;
import com.octo.captcha.service.AbstractManageableCaptchaServiceMBean;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.CaptchaStore;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import org.apache.commons.collections.FastHashMap;

public abstract class AbstractManageableCaptchaService extends AbstractCaptchaService implements AbstractManageableCaptchaServiceMBean, CaptchaService {
    private int minGuarantedStorageDelayInSeconds;
    private int captchaStoreMaxSize;
    private int captchaStoreSizeBeforeGarbageCollection;
    private int numberOfGeneratedCaptchas;
    private int numberOfCorrectResponse;
    private int numberOfUncorrectResponse;
    private int numberOfGarbageCollectedCaptcha;
    private FastHashMap times;
    private long oldestCaptcha;

    protected AbstractManageableCaptchaService(CaptchaStore captchaStore, CaptchaEngine captchaEngine, int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize) {
        super(captchaStore, captchaEngine);
        this.numberOfGeneratedCaptchas = 0;
        this.numberOfCorrectResponse = 0;
        this.numberOfUncorrectResponse = 0;
        this.numberOfGarbageCollectedCaptcha = 0;
        this.oldestCaptcha = 0L;
        this.setCaptchaStoreMaxSize(maxCaptchaStoreSize);
        this.setMinGuarantedStorageDelayInSeconds(minGuarantedStorageDelayInSeconds);
        this.setCaptchaStoreSizeBeforeGarbageCollection((int)Math.round(0.8D * (double)maxCaptchaStoreSize));
        this.times = new FastHashMap();
    }

    protected AbstractManageableCaptchaService(CaptchaStore captchaStore, CaptchaEngine captchaEngine, int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize, int captchaStoreLoadBeforeGarbageCollection) {
        this(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize);
        if(maxCaptchaStoreSize < captchaStoreLoadBeforeGarbageCollection) {
            throw new IllegalArgumentException("the max store size can\'t be less than garbage collection size. if you want to disable garbage collection (this is not recommended) you may set them equals (max=garbage)");
        } else {
            this.setCaptchaStoreSizeBeforeGarbageCollection(captchaStoreLoadBeforeGarbageCollection);
        }
    }

    public String getCaptchaEngineClass() {
        return this.engine.getClass().getName();
    }

    public void setCaptchaEngineClass(String theClassName) throws IllegalArgumentException {
        try {
            Object e = Class.forName(theClassName).newInstance();
            if(e instanceof CaptchaEngine) {
                this.engine = (CaptchaEngine)e;
            } else {
                throw new IllegalArgumentException("Class is not instance of CaptchaEngine! " + theClassName);
            }
        } catch (InstantiationException var3) {
            throw new IllegalArgumentException(var3.getMessage());
        } catch (IllegalAccessException var4) {
            throw new IllegalArgumentException(var4.getMessage());
        } catch (ClassNotFoundException var5) {
            throw new IllegalArgumentException(var5.getMessage());
        } catch (RuntimeException var6) {
            throw new IllegalArgumentException(var6.getMessage());
        }
    }

    public CaptchaEngine getEngine() {
        return this.engine;
    }

    public void setCaptchaEngine(CaptchaEngine engine) {
        this.engine = engine;
    }

    public int getMinGuarantedStorageDelayInSeconds() {
        return this.minGuarantedStorageDelayInSeconds;
    }

    public void setMinGuarantedStorageDelayInSeconds(int theMinGuarantedStorageDelayInSeconds) {
        this.minGuarantedStorageDelayInSeconds = theMinGuarantedStorageDelayInSeconds;
    }

    public long getNumberOfGeneratedCaptchas() {
        return (long)this.numberOfGeneratedCaptchas;
    }

    public long getNumberOfCorrectResponses() {
        return (long)this.numberOfCorrectResponse;
    }

    public long getNumberOfUncorrectResponses() {
        return (long)this.numberOfUncorrectResponse;
    }

    public int getCaptchaStoreSize() {
        return this.store.getSize();
    }

    public int getNumberOfGarbageCollectableCaptchas() {
        return this.getGarbageCollectableCaptchaIds(System.currentTimeMillis()).size();
    }

    public long getNumberOfGarbageCollectedCaptcha() {
        return (long)this.numberOfGarbageCollectedCaptcha;
    }

    public int getCaptchaStoreSizeBeforeGarbageCollection() {
        return this.captchaStoreSizeBeforeGarbageCollection;
    }

    public void setCaptchaStoreSizeBeforeGarbageCollection(int captchaStoreSizeBeforeGarbageCollection) {
        if(this.captchaStoreMaxSize < captchaStoreSizeBeforeGarbageCollection) {
            throw new IllegalArgumentException("the max store size can\'t be less than garbage collection size. if you want to disable garbage collection (this is not recommended) you may set them equals (max=garbage)");
        } else {
            this.captchaStoreSizeBeforeGarbageCollection = captchaStoreSizeBeforeGarbageCollection;
        }
    }

    public void setCaptchaStoreMaxSize(int size) {
        if(size < this.captchaStoreSizeBeforeGarbageCollection) {
            throw new IllegalArgumentException("the max store size can\'t be less than garbage collection size. if you want to disable garbage collection (this is not recommended) you may set them equals (max=garbage)");
        } else {
            this.captchaStoreMaxSize = size;
        }
    }

    public int getCaptchaStoreMaxSize() {
        return this.captchaStoreMaxSize;
    }

    protected void garbageCollectCaptchaStore(Iterator garbageCollectableCaptchaIds) {
        long now = System.currentTimeMillis();
        long limit = now - (long)(1000 * this.minGuarantedStorageDelayInSeconds);

        while(garbageCollectableCaptchaIds.hasNext()) {
            String id = garbageCollectableCaptchaIds.next().toString();
            if(((Long)this.times.get(id)).longValue() < limit) {
                this.times.remove(id);
                this.store.removeCaptcha(id);
                ++this.numberOfGarbageCollectedCaptcha;
            }
        }

    }

    public void garbageCollectCaptchaStore() {
        long now = System.currentTimeMillis();
        Collection garbageCollectableCaptchaIds = this.getGarbageCollectableCaptchaIds(now);
        this.garbageCollectCaptchaStore(garbageCollectableCaptchaIds.iterator());
    }

    public void emptyCaptchaStore() {
        this.store.empty();
        this.times = new FastHashMap();
    }

    private Collection getGarbageCollectableCaptchaIds(long now) {
        HashSet garbageCollectableCaptchas = new HashSet();
        long limit = now - (long)(1000 * this.getMinGuarantedStorageDelayInSeconds());
        if(limit > this.oldestCaptcha) {
            Iterator ids = (new HashSet(this.times.keySet())).iterator();

            while(ids.hasNext()) {
                String id = (String)ids.next();
                long captchaDate = ((Long)this.times.get(id)).longValue();
                this.oldestCaptcha = Math.min(captchaDate, this.oldestCaptcha == 0L?captchaDate:this.oldestCaptcha);
                if(captchaDate < limit) {
                    garbageCollectableCaptchas.add(id);
                }
            }
        }

        return garbageCollectableCaptchas;
    }

    protected Captcha generateAndStoreCaptcha(Locale locale, String ID) {
        if(this.isCaptchaStoreFull()) {
            long now = System.currentTimeMillis();
            Collection garbageCollectableCaptchaIds = this.getGarbageCollectableCaptchaIds(now);
            if(garbageCollectableCaptchaIds.size() > 0) {
                this.garbageCollectCaptchaStore(garbageCollectableCaptchaIds.iterator());
                return this.generateAndStoreCaptcha(locale, ID);
            } else {
                throw new CaptchaServiceException("Store is full, try to increase CaptchaStore Size orto decrease time out, or to decrease CaptchaStoreSizeBeforeGrbageCollection");
            }
        } else {
            if(this.isCaptchaStoreQuotaReached()) {
                this.garbageCollectCaptchaStore();
            }

            return this.generateCountTimeStampAndStoreCaptcha(ID, locale);
        }
    }

    private Captcha generateCountTimeStampAndStoreCaptcha(String ID, Locale locale) {
        ++this.numberOfGeneratedCaptchas;
        Long now = new Long(System.currentTimeMillis());
        this.times.put(ID, now);
        Captcha captcha = super.generateAndStoreCaptcha(locale, ID);
        return captcha;
    }

    protected boolean isCaptchaStoreFull() {
        return this.getCaptchaStoreMaxSize() == 0?false:this.getCaptchaStoreSize() >= this.getCaptchaStoreMaxSize();
    }

    protected boolean isCaptchaStoreQuotaReached() {
        return this.getCaptchaStoreSize() >= this.getCaptchaStoreSizeBeforeGarbageCollection();
    }

    public Boolean validateResponseForID(String ID, Object response) throws CaptchaServiceException {
        Boolean valid = super.validateResponseForID(ID, response);
        this.times.remove(ID);
        if(valid.booleanValue()) {
            ++this.numberOfCorrectResponse;
        } else {
            ++this.numberOfUncorrectResponse;
        }

        return valid;
    }
}
