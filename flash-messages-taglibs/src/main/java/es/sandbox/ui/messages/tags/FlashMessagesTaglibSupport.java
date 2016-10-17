package es.sandbox.ui.messages.tags;

import es.sandbox.ui.messages.Context;
import es.sandbox.ui.messages.Level;
import es.sandbox.ui.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;


public class FlashMessagesTaglibSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlashMessagesTaglibSupport.class);


    /**
     * Private constructor to prevent instances
     *
     * @throws UnsupportedOperationException
     */
    private FlashMessagesTaglibSupport() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * @param level
     * @param request
     * @return
     */
    public static Collection<Message> levelMessages(Level level, HttpServletRequest request) {
        return levelMessagesFromContext(level, context(request), request);
    }

    private static Collection<Message> levelMessagesFromContext(Level level, Context context, HttpServletRequest request) {
        if ((context != null) && (level != null)) {
            return context.levelMessages(level, request);
        }

        LOGGER.warn("[{}] level messages can't be accessed!", level);
        return new ArrayList<Message>();
    }

    private static Context context(HttpServletRequest request) {
        return request == null ? null : (Context) request.getAttribute(Context.FLASH_MESSAGES_CONTEXT_PARAMETER);
    }

    /**
     * @param request
     * @return
     */
    public static List<Level> levels(HttpServletRequest request) {

        final Context context = context(request);
        if (context == null) {
            LOGGER.warn("MessageContext can't be accessed!");
            return new ArrayList<Level>();
        }
        return context.levels();
    }

    /**
     * @param level
     * @param request
     * @return
     */
    public static String levelCssClass(Level level, HttpServletRequest request) {

        final Context context = context(request);
        if (context == null) {
            LOGGER.warn("MessageContext can't be accessed!");
            return "";
        }
        return trimToEmpty(context.getLevelCssClass(level));
    }
}
