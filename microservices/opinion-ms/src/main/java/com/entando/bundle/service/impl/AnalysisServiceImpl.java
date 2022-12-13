package com.entando.bundle.service.impl;

import com.entando.bundle.domain.enumeration.Sentiment;
import com.entando.bundle.service.AnalysisService;
import com.entando.bundle.service.dto.OpinionDTO;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;


@Service
public class AnalysisServiceImpl implements AnalysisService {

    private final Logger log = LoggerFactory.getLogger(AnalysisServiceImpl.class);

    private StanfordCoreNLP pipeline;

    @PostConstruct
    protected void init() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
//        props.setProperty("tokenize.language", "it");
        pipeline = new StanfordCoreNLP(props);
        log.info("Sentiment analysis service started");
    }

    @Override
    public int getSentences(OpinionDTO opinion) {
        int sentences = 0;

        if (opinion != null) {
            sentences = getSentences(opinion.getText());
            opinion.setSentences(sentences);
        }
        return sentences;
    }

    protected int getSentences(String text) {
        int num = 0;

        try {
            if (StringUtils.isNotBlank(text)) {
                Annotation annotation = pipeline.process(text);
                List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
                num = sentences.size();
            }
        } catch (Throwable t) {
            log.error("error in sentence count", t);
            throw t;
        }
        return num;
    }

    @Override
    public float getSentenceSentiment(OpinionDTO opinion) {
        float sentimentScore = -1f;

        if (StringUtils.isNotBlank(opinion.getLangcode())
            && StringUtils.isNotBlank(opinion.getText())) {
            // LANGUAGE IGNORED
            Annotation annotation = pipeline.process(opinion.getText());
            CoreMap sentence = annotation.get(CoreAnnotations.SentencesAnnotation.class).get(0);
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            sentimentScore = Float.valueOf(RNNCoreAnnotations.getPredictedClass(tree));

            opinion.setScore(sentimentScore);
            String sentimentName = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            opinion.setResult(Sentiment.fromString(sentimentName));
            opinion.setSentences(1);
        }
        return sentimentScore;
    }

    @Override
    public float getReviewSentiment(OpinionDTO opinion, Float weight) {
        float sentimentScore = 0f;

        if (opinion != null
            && StringUtils.isNotBlank(opinion.getText())) {
            int sentenceSentiment;
            int reviewSentimentAverageSum = 0;
            int reviewSentimentWeightedSum = 0;

            boolean wighted = true;

            if (weight == null) {
                log.debug("Will use LINEAR average for sentiment score");
                wighted = false;
                weight = 0f;
            }

            Annotation annotation = pipeline.process(opinion.getText());
            List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
            int numOfSentences = sentences.size();
            int factor = Math.round(numOfSentences * weight);
            if (factor == 0) {
                log.debug("text too small, scaling to linear average");
                factor = 1;
            }
            log.debug("Using factor {} with {} sentences", factor, numOfSentences);
            int divisorLinear = numOfSentences;
            int divisorWeighted = 0;

            for (int idx = 0; idx < numOfSentences; idx++) {
                Tree tree = sentences.get(idx).get(SentimentAnnotatedTree.class);

                sentenceSentiment = RNNCoreAnnotations.getPredictedClass(tree);
                reviewSentimentAverageSum += sentenceSentiment;

                if (idx == 0 || idx == (numOfSentences - 1)) {
                    reviewSentimentWeightedSum += sentenceSentiment * factor;
                    divisorWeighted += factor;
                } else {
                    reviewSentimentWeightedSum += sentenceSentiment;
                    divisorWeighted += 1;
                }
            }

            if (wighted) {
                log.debug("weighted divisor {}", divisorWeighted);
                sentimentScore = reviewSentimentWeightedSum / (float) divisorWeighted;
            } else {
                log.debug("linear divisor {}", divisorLinear);
                sentimentScore = reviewSentimentWeightedSum / (float) divisorLinear;
            }
            opinion.setScore(sentimentScore);
            opinion.setResult(Sentiment.fromInt(Math.round(sentimentScore)));
            opinion.setSentences(numOfSentences);
            log.debug("\n\ntext\n{}", opinion.getText());
            log.debug("setting score {}", sentimentScore);
            log.debug("setting result {}\n", opinion.getResult());
        }
        return sentimentScore;
    }

}
