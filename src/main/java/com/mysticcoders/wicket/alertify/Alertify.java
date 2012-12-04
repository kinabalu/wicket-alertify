package com.mysticcoders.wicket.alertify;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Binding for alertify.js
 *
 * from: https://github.com/fabien-d/alertify.js
 *
 * @author Andrew Lombardi
 */
public class Alertify extends Behavior {
    private static final long serialVersionUID = 1L;

    /**
     * Render to the web response whatever the component wants to contribute to the head section.
     *
     * @param component component this behavior is attached to
     * @param response Response object
     */
    public void renderHead(final Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        if(useDefaultCss()) {
            response.render(CssHeaderItem.forReference(new CssResourceReference(Alertify.class, "alertify.core.css")));
            response.render(CssHeaderItem.forReference(new CssResourceReference(Alertify.class, "alertify.default.css")));
        }
        response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(Alertify.class, "alertify.min.js")));

    }

    public static final String OK = "OK";
    public static final String CANCEL = "Cancel";

    private String okLabel;
    private String cancelLabel;

    /**
     * Sets the okay and cancel labels
     *
     * @param okLabel okay label
     * @param cancelLabel cancel label
     */
    public void labels(String okLabel, String cancelLabel) {
        this.okLabel = okLabel;
        this.cancelLabel = cancelLabel;
    }

    /**
     * Clear the label text to use defaults
     */
    public void clearLabels() {
        this.okLabel = null;
        this.cancelLabel = null;
    }

    /**
     * Use the default CSS provided by a!ertify
     *
     * @return true if you want to include the a!ertify CSS
     */
    protected boolean useDefaultCss() {
        return true;
    }


    /**
     * Preps alertify StringBuilder and sets the ok / cancel methods just in case
     *
     * @return initialized StringBuilder
     */
    private StringBuilder prepAlertify() {
        StringBuilder sb = new StringBuilder();

        if(okLabel!=null) {
            sb.append("alertify.labels.ok = \"").append(okLabel).append("\";");
        }
        if(cancelLabel!=null) {
            sb.append("alertify.labels.cancel = \"").append(cancelLabel).append("\";");
        }
        return sb;
    }

    /**
     * Show an alert with message
     *
     * @param message the message
     * @return the javascript
     */
    public CharSequence alert(String message) {
        return alert(message, "");
    }

    /**
     * Show an alert with message and use supplied javascript after success
     *
     * @param message the message
     * @param javascript the javascript to execute after success
     * @return the javascript
     */
    public CharSequence alert(String message, CharSequence javascript) {
        StringBuilder sb = prepAlertify();
        sb.append("alertify.alert(")
                .append("'")
                .append(message)
                .append("'")
                .append(", function() {").append(javascript).append("});");
        return sb.toString();
    }

    /**
     * Show an alert with message and use supplied AbstractDefaultAjaxBehavior after success
     *
     * @param message the message
     * @param success the AbstractDefaultAjaxBehavior to execute after success
     * @return the javascript
     */
    public CharSequence alert(String message, AbstractDefaultAjaxBehavior success) {
        return alert(message, success.getCallbackScript());
    }

    /**
     * Show confirmation message
     *
     * @param message the message
     * @param successJs the javascript to execute after success
     * @return the javascript
     */
    public CharSequence confirm(String message, CharSequence successJs) {
        return confirm(message, successJs, "");
    }

    /**
     * Show confirmation message
     *
     * @param message the message
     * @param successJs the javascript to execute after success
     * @param cancelJs the javascript to execute after cancel
     * @return the javascript
     */
    public CharSequence confirm(String message, CharSequence successJs, CharSequence cancelJs) {
        StringBuilder sb = prepAlertify();
        sb.append("alertify.confirm(")
                .append("'")
                .append(message)
                .append("'")
                .append(", function (e) {")
                .append("if (e) {")
                .append(successJs)
                .append("} else {")
                .append(cancelJs)
                .append("}")
                .append(" });")

        ;
        return sb.toString();

    }

    /**
     * Show confirmation message
     *
     * @param message the message
     * @param success the AbstractDefaultAjaxBehavior to execute after success
     * @param cancel the AbstractDefaultAjaxBehavior to execute after cancel
     * @return the javascript
     */
    public CharSequence confirm(String message, AbstractDefaultAjaxBehavior success, AbstractDefaultAjaxBehavior cancel) {
        return confirm(message, success.getCallbackScript(), cancel.getCallbackScript());
    }

    /**
     * Show prompt message
     *
     * @param message the message
     * @param successJs the javascript to execute after success
     * @param cancelJs the javascript to execute after cancel
     * @return the javascript
     */
    public CharSequence prompt(String message, CharSequence successJs, CharSequence cancelJs) {
        StringBuilder sb = prepAlertify();
        sb.append("alertify.prompt(")
                .append("'")
                .append(message)
                .append("'")
                .append(", function (e, str) {")
                .append("if (e) {")
                .append(successJs)
                .append("} else {")
                .append(cancelJs)
                .append("}")
                .append(" });");
        return sb.toString();
    }

    /**
     * Show prompt message
     *
     * @param message the message
     * @param success the AbstractDefaultAjaxBehavior to execute after success
     * @param cancel the AbstractDefaultAjaxBehavior to execute after cancel
     * @return the javascript
     */
    public CharSequence prompt(String message, AbstractDefaultAjaxBehavior success, AbstractDefaultAjaxBehavior cancel) {
        return prompt(message, success.getCallbackScript(), cancel.getCallbackScript());
    }

    /**
     * Log a message to be shown at bottom-right of screen to user
     *
     * @param message the message
     * @param type the CSS class @see success, error
     * @return the javascript
     */
    public CharSequence log(String message, String type) {
        StringBuilder sb = new StringBuilder();
        sb.append("alertify.log(")
                .append("'")
                .append(message)
                .append("', '")
                .append(type)
                .append("');");
        return sb.toString();
    }

    /**
     * Log a success message to be shown at bottom-right of screen to user
     *
     * @param message the message
     * @return the javascript
     */
    public CharSequence success(String message) {
        return log(message, "success");
    }

    /**
     * Log an error message to be shown at bottom-right of screen to user
     *
     * @param message the message
     * @return the javascript
     */
    public CharSequence error(String message) {
        return log(message, "error");
    }

}