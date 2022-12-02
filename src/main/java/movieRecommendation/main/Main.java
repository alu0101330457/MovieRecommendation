package movieRecommendation.main;

import movieRecommendation.recommenderrunner.RecommendationRunner;

public class Main {
    public static void main(String[] args) {
        RecommendationRunner recommendationRunner = new RecommendationRunner();
        recommendationRunner.printRecommendationsFor("1");
    }
}
