package com.entando.bundle.service;

import com.entando.bundle.service.dto.OpinionDTO;

public interface AnalysisService {

    /**
     * Get the number of the sentences in a text
     *
     * @param dto@return
     */
    int getSentences(OpinionDTO dto);

    /**
     * Sentiment analysis on a single sentence
     * @param opinion
     * @return the sentiment score
     */
    float getSentenceSentiment(OpinionDTO opinion);

    /**
     * Sentiment analysis on a single sentence
     * @param opinion
     * @param weight if null the score will be the linear average of the single sentences composing the text,
     *               otherwise the first and the last sentence will have grater weight than the others.
     * @return the sentiment score
     */
    float getReviewSentiment(OpinionDTO opinion, Float weight);
}
