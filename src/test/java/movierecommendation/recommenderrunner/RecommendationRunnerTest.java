package movierecommendation.recommenderrunner;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.ArrayList;

class RecommendationRunnerTest {


    @DisplayName("Test if we can get the movies to rate")
    @Nested
    class RecommendationRunnerTest0 {
        @DisplayName("Test if we can get the movies to rate")
        @org.junit.jupiter.api.Test
        void test() {
            RecommendationRunner recommendationRunner = new RecommendationRunner();
            recommendationRunner.getItemsToRate();
        }
    }

    @DisplayName("When we call the method getItemsToRate it should return a list of movies")
    @Nested
    class RecommendationRunnerTest1 {
        @DisplayName("When we call the method getItemsToRate it should return a list of movies")
        @org.junit.jupiter.api.Test
        void test() {
            RecommendationRunner recommendationRunner = new RecommendationRunner();
            ArrayList<String> films = recommendationRunner.getItemsToRate();
            // Comprobar si films tiene 10 elementos
            assertEquals(10, films.size());
            // Comprobar si films es un array de Strings
            for (String film : films) {
                assertTrue(film instanceof String);
            }
        }
    }

    @DisplayName("The method printOut is working")
    @Nested

    class RecommendationRunnerTest2 {
        @DisplayName("The method printOut is working")
        @org.junit.jupiter.api.Test
        void test() {
            RecommendationRunner recommendationRunner = new RecommendationRunner();
            recommendationRunner.printOut("0", 0);
        }
    }
    
    @DisplayName("The method printOut returns a String")
    @Nested
    class RecommendationRunnerTest3 {
        @DisplayName("The method printOut returns a String")
        @org.junit.jupiter.api.Test
        void test() {
            RecommendationRunner recommendationRunner = new RecommendationRunner();
            String result = recommendationRunner.printOut("0", 0);
            assertTrue(result instanceof String);
        }
    }   

    @DisplayName("We cant pass a negative number to printRecommendationsFor method")
    @Nested
    class RecommendationRunnerTest4 {
        @DisplayName("We cant pass a negative number to printRecommendationsFor method")
        @org.junit.jupiter.api.Test
        void test() {
            RecommendationRunner recommendationRunner = new RecommendationRunner();
            assertThrows(NullPointerException.class, () -> {
                recommendationRunner.printRecommendationsFor("-1");
            });
        }
    }
  
}