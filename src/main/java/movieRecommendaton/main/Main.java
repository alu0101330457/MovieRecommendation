package movieRecommendaton.main;

import movieRecommendaton.recommenderrunner.RecommendationRunner;

public class Main {
    public static void main(String[] args) {
        RecommendationRunner recommendationRunner = new RecommendationRunner();
        recommendationRunner.printRecommendationsFor("1");
    }
}
