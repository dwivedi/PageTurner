package net.nightwhistler.pageturner.view.bookview;

import android.text.SpannableStringBuilder;
import net.nightwhistler.htmlspanner.TagNodeHandler;
import org.htmlcleaner.TagNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Many books use
 * <p>
 * and
 * <h1>tags as anchor points. This class harvests those point by wrapping
 * the original handler.
 *
 * @author Alex Kuiper
 *
 */
public class AnchorHandler extends TagNodeHandler {

    private TagNodeHandler wrappedHandler;

    private AnchorCallback callback;

    public AnchorHandler(TagNodeHandler wrappedHandler) {
        this.wrappedHandler = wrappedHandler;
    }

    @Override
    public void beforeChildren(TagNode node, SpannableStringBuilder builder) {
        this.wrappedHandler.beforeChildren(node, builder);
    }

    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder,
                              int start, int end) {

        String id = node.getAttributeByName("id");
        if (id != null) {
            callback.registerAnchor(id, start);
        }

        wrappedHandler.handleTagNode(node, builder, start, end);
    }

    public void setCallback( AnchorCallback callback ) {
        this.callback = callback;
    }

    public static interface AnchorCallback {
        void registerAnchor( String anchor, int position );
    }
}