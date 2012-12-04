package com.mysticcoders.wicket.alertify;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
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

    /**
     *
     *
     * @return
     */
    protected boolean useDefaultCss() {
        return true;
    }

}