package org.aksw.defacto.util;


import java.net.SocketException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

/**
 * 
 * @author Daniel Gerber <dgerber@informatik.uni-leipzig.de>
 */
public class JsoupCrawlUtil implements CrawlUtil {

    private static Logger logger = Logger.getLogger(JsoupCrawlUtil.class);
    
    @Override
    public String readPage(String url, int timeout) {

//        throw new RuntimeException("no need to query if we have a cache");
        
        try {
            
            return Jsoup.connect(url).timeout(timeout).get().text();
        }
        catch (Throwable e) {
            
            // we need to do this because the log file is flooded with useless error messages 
            if ( e.getMessage().contains("Unhandled content type") ||
                 e.getMessage().contains("Premature EOF") ||
                 e.getMessage().contains("Read timed out") ||
                 e.getMessage().contains("Connection refused") ||
                 e.getMessage().contains("-1 error loading URL") ||
                 e.getMessage().contains("401 error loading URL") ||
                 e.getMessage().contains("403 error loading URL") ||
                 e.getMessage().contains("404 error loading URL") ||
                 e.getMessage().contains("405 error loading URL") ||
                 e.getMessage().contains("408 error loading URL") ||
                 e.getMessage().contains("410 error loading URL") ||
                 e.getMessage().contains("500 error loading URL") ||
                 e.getMessage().contains("502 error loading URL") ||
                 e.getMessage().contains("503 error loading URL") ||
                 e instanceof UnknownHostException  ||  
                 e instanceof SSLHandshakeException ||
                 e instanceof SocketException ) {
                
                logger.debug(String.format("Error crawling website: %s", url));
            }
            else logger.error(String.format("Error crawling website: %s", url), e);
        }
        
        return "";
    }

}
