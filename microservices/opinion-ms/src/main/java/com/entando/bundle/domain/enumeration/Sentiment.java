package com.entando.bundle.domain.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * The Sentiment enumeration.
 */
public enum Sentiment {
    VERY_NEGATIVE,
    NEGATIVE,
    NEUTRAL,
    POSITIVE,
    VERY_POSITIVE,
    UNDETERMINED;

    public static Sentiment fromString(String str) {
        if (StringUtils.isNotBlank(str)) {
            str = str.toLowerCase();

            if (str.contains("very") && str.contains("positive")) {
                return Sentiment.VERY_POSITIVE;
            } else if (str.contains("very") && str.contains("negative")) {
                return Sentiment.VERY_NEGATIVE;
            } else if (str.contains("positive")) {
                return Sentiment.POSITIVE;
            } else if (str.contains("negative")) {
                return Sentiment.NEGATIVE;
            } else if (str.contains("neutral")) {
                return Sentiment.NEUTRAL;
            }
        }
        return Sentiment.UNDETERMINED;
    }

    public static Sentiment fromInt(int val) {
        switch (val) {
            case 0: return Sentiment.VERY_NEGATIVE;
            case 1: return Sentiment.NEGATIVE;
            case 2: return Sentiment.NEUTRAL;
            case 3: return Sentiment.POSITIVE;
            case 4: return Sentiment.VERY_POSITIVE;
            default: return Sentiment.UNDETERMINED;
        }
    }
}
