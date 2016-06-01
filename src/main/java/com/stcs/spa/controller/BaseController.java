/**
 *
 */
package com.stcs.spa.controller;

/**
 * @author Muhammad
 */

import com.stcs.spa.services.SPServices;
import com.stcs.spa.services.exception.EventProcessingException;
import com.stcs.spa.util.Config;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class BaseController {

    @Autowired
    @Qualifier("defaultSPServices")
    SPServices spServices;


    /**
     * @param spServices the spServices to set
     */
    public void setSpServices(SPServices spServices) {
        this.spServices = spServices;
    }

    Config config = Config.getInstance();
    private static final Logger logger = Logger.getLogger(BaseController.class);
    private static final String VIEW_HOME = "home";

    @ResponseBody
    @RequestMapping(value = "/bluvalt_listener", method = RequestMethod.POST)
    String handleEvent(@RequestBody(required = true) String eventStr,
                       ModelMap model, HttpServletRequest request) {
        String headerSig = request.getHeader("X-Bluvalt-Signature");
        if (headerSig != null) {
            logger.debug("X-Bluvalt-Signature Value:" + headerSig);
            try {
                Mac mac = Mac.getInstance("HmacSHA256");
                mac.init(new SecretKeySpec(config.properties.getProperty("webhook_key").getBytes(), "HmacSHA256"));
                String output = Hex.encodeHexString(mac.doFinal(eventStr.getBytes()));
                logger.debug("output:" + output);
                if (MessageDigest.isEqual(headerSig.getBytes(), output.getBytes())) {
                    logger.debug("Sig Verified:Y");
                    logger.debug("Bluvalt Event JSON:" + eventStr);
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(new Runnable() {
                        public void run() {
                            logger.debug("Calling handleEvent Asynchronously. ");
                            try {
                                spServices.handleEvent(eventStr);
                            } catch (EventProcessingException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    executorService.shutdown();
                } else {
                    logger.debug("Rejecting Sig Not Matching. ");
                }
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                throw new AssertionError(e);
            }


        } else {
            logger.debug("Rejecting No Sign ");
        }
        return "received";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String adminHome(ModelMap model, HttpServletRequest request) {
        return VIEW_HOME;
    }

}
