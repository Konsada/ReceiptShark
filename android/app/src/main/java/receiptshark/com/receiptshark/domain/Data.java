package receiptshark.com.receiptshark.domain;

import java.util.List;

/**
 * Created by thomhemenway on 2/3/18.
 */

public class Data {
    List<BoundingText> boundingTexts;

    class BoundingText {
        public List<Integer> boundingBox;
        public String text;
    }
}
