package es.sandbox.ui.messages.thymeleaf;

import es.sandbox.ui.messages.Context;
import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.WebEngineContext;
import org.thymeleaf.model.AttributeValueQuotes;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * Created by jeslopalo on 19/12/16.
 */
public class FlashMessagesElementTagProcessor extends AbstractElementTagProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(FlashMessagesElementTagProcessor.class);

    private static final String THYMELEAF_TAG_NAME = "messages";
    private static final int PRECEDENCE = 1000;

    private static final String HTML_CONTAINER_TAG = "div";
    private static final String HTML_MULTI_MESSAGE_CONTAINER = "ul";
    private static final String HTML_MULTI_MESSAGE_ELEMENT = "li";
    private static final String HTML_SINGLE_MESSAGE = "span";

    /**
     * @param dialectPrefix
     */
    public FlashMessagesElementTagProcessor(final String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, THYMELEAF_TAG_NAME, true, null, false, PRECEDENCE);
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, IElementTagStructureHandler structureHandler) {

        if (!(context instanceof WebEngineContext)) {
            throw new IllegalArgumentException(String.format("Context is not a WebEngineContext instance [%s]", context.getClass()));
        }
        final WebEngineContext webEngineContext = (WebEngineContext) context;

        final IModelFactory modelFactory = context.getModelFactory();
        final IModel model = modelFactory.createModel();

        final HttpServletRequest request = webEngineContext.getRequest();

        final Context flashMessagesContext = context(request);

        for (Level level : flashMessagesContext.levels()) {
            final Collection<Message> messages = flashMessagesContext.levelMessages(level, request);
            if (!messages.isEmpty()) {
                model.addModel(levelMessages(level, messages, request, modelFactory));
            }
        }

        /*
         * Instruct the engine to replace this entire element with the specified model.
         */
        structureHandler.replaceWith(model, false);
    }

//      <div data-level="${level}" class="alert alert-${level}">
//          <c:choose>
//              <c:when test="${fn:length(messages) gt 1}">
//              <ul>
//                  <c:forEach var="message" items="${messages}">
//                      <li data-timestamp="${message.timestamp}"><c:out value="${message.text}" escapeXml="false"/></li>
//                  </c:forEach>
//              </ul>
//              </c:when>
//              <c:otherwise>
//                  <c:forEach var="message" items="${messages}">
//                      <span data-timestamp="${message.timestamp}"><c:out value="${message.text}" escapeXml="false"/></span>
//                  </c:forEach>
//              </c:otherwise>
//          </c:choose>
//      </div>

    private IModel levelMessages(Level level, Collection<Message> messages, HttpServletRequest request, IModelFactory modelFactory) {
        final IModel model = modelFactory.createModel();

        model.add(modelFactory.createOpenElementTag(HTML_CONTAINER_TAG, levelMessagesContainerAttributes(level, request), AttributeValueQuotes.DOUBLE, false));
        renderMessages(messages, modelFactory, model);
        model.add(modelFactory.createCloseElementTag(HTML_CONTAINER_TAG));

        return model;
    }

    private void renderMessages(Collection<Message> messages, IModelFactory modelFactory, IModel model) {
        final boolean severalMessages = messages.size() > 1;
        final String tagName = severalMessages ? HTML_MULTI_MESSAGE_ELEMENT : HTML_SINGLE_MESSAGE;
        if (severalMessages) {
            model.add(modelFactory.createOpenElementTag(HTML_MULTI_MESSAGE_CONTAINER));
        }

        for (Message message : messages) {
            model.add(modelFactory.createOpenElementTag(tagName, "data-timestamp", message.getTimestamp().toString()));
            model.add(modelFactory.createText(message.getText()));
            model.add(modelFactory.createCloseElementTag(tagName));
        }

        if (severalMessages) {
            model.add(modelFactory.createCloseElementTag(HTML_MULTI_MESSAGE_CONTAINER));
        }
    }

    private Map<String, String> levelMessagesContainerAttributes(Level level, HttpServletRequest request) {
        final Map<String, String> attributes = new HashMap<>();
        attributes.put("data-level", level.name());
        attributes.put("class", levelCssClass(level, request));

        return attributes;
    }

    private static Context context(HttpServletRequest request) {
        return request == null ? null : (Context) request.getAttribute(Context.FLASH_MESSAGES_CONTEXT_PARAMETER);
    }

    /**
     * @param level
     * @param request
     * @return
     */
    private String levelCssClass(Level level, HttpServletRequest request) {

        final Context context = context(request);
        if (context == null) {
            LOG.warn("MessageContext can't be accessed!");
            return "";
        }
        return trimToEmpty(context.getLevelCssClass(level));
    }
}
