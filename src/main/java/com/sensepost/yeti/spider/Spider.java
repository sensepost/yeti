package com.sensepost.yeti.spider;

import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import com.sensepost.yeti.common.Log;

public class Spider {

    private String baseurl = "";
    protected Collection workloadError = new ArrayList(3);
    protected Collection workloadWaiting = new ArrayList(3);
    protected Collection workloadProcessed = new ArrayList(3);
    protected UrlProcessor processor;
    protected boolean cancel = false;

    public Spider(UrlProcessor processor) {
        this.processor = processor;
    }

    public void setBaseURL(String baseUrl) {
        this.baseurl = baseUrl;
    }

    public Collection getWorkloadError() {
        return workloadError;
    }

    public Collection getWorkloadWaiting() {
        return workloadWaiting;
    }

    public Collection getWorkloadProcessed() {
        return workloadProcessed;
    }

    public void clear() {
        getWorkloadError().clear();
        getWorkloadWaiting().clear();
        getWorkloadProcessed().clear();
    }

    public void cancel() {
        cancel = true;
    }

    public void addURL(URL url) {
        if (getWorkloadWaiting().contains(url)) {
            return;
        }
        if (getWorkloadError().contains(url)) {
            return;
        }
        if (getWorkloadProcessed().contains(url)) {
            return;
        }
        getWorkloadWaiting().add(url);
    }

    public void processURL(URL url) {
        try {
            URLConnection connection = url.openConnection();
            if ((connection.getContentType() != null) && !connection.getContentType().toLowerCase().startsWith("text/")) {
                getWorkloadWaiting().remove(url);
                getWorkloadProcessed().add(url);
                log("Not processing because content type is: "
                        + connection.getContentType());
                return;
            }
            InputStream is = connection.getInputStream();
            Reader r = new InputStreamReader(is);
            HTMLEditorKit.Parser parse = new HTMLParse().getParser();
            parse.parse(r, new Parser(url), true);
        } catch (IOException e) {
            getWorkloadWaiting().remove(url);
            getWorkloadError().add(url);
            log("Error: " + url);
            processor.handleUrlError(url, this.baseurl);
            return;
        }
        getWorkloadWaiting().remove(url);
        getWorkloadProcessed().add(url);
    }

    public void begin() {
        cancel = false;
        while (!getWorkloadWaiting().isEmpty() && !cancel) {
            Object list[] = getWorkloadWaiting().toArray();
            for (int i = 0; (i < list.length) && !cancel; i++) {
                processURL((URL) list[i]);
            }

        }
    }

    protected class Parser extends HTMLEditorKit.ParserCallback {

        protected URL base;

        public Parser(URL base) {
            this.base = base;
        }

        public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
            String href = (String) a.getAttribute(HTML.Attribute.HREF);

            if ((href == null) && (t == HTML.Tag.FRAME)) {
                href = (String) a.getAttribute(HTML.Attribute.SRC);
            }

            if ((href == null) && (t == HTML.Tag.FRAMESET)) {
                href = (String) a.getAttribute(HTML.Attribute.SRC);
            }

            if ((href == null) && (t == HTML.Tag.IMG)) {
                href = (String) a.getAttribute(HTML.Attribute.SRC);
            }

            if (href == null) {
                return;
            }

            int i = href.indexOf('#');
            if (i != -1) {
                href = href.substring(0, i);
            }

            if (href.toLowerCase().startsWith("mailto:")) {
                processor.addEmailAddress(href);
                return;
            }

            handleLink(base, href);
        }

        public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
            handleSimpleTag(t, a, pos);    // handle the same way

        }

        protected void handleLink(URL base, String str) {
            try {
                URL url = new URL(base, str);
                processor.addHostFromUrl(str, base.toString());
                Log.debug(url.toString());
            } catch (MalformedURLException e) {
                log("Found malformed URL: " + str);
            }
        }
    }

    public void log(String entry) {
        this.processor.onDebug((new Date()) + ":" + entry);
    }

}
