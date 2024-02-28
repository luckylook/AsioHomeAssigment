package com.example.asio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlgorithmSolution {

    public List<List<Double>> getMinimalWays(List<Double> bags){
        bags.sort(Collections.reverseOrder());

        List<List<Double>> tours = new ArrayList<>();

        while(!bags.isEmpty()){
            double limit = 3.0;
            List<Double> tour = new ArrayList<>();

            for(int i = 0; i < bags.size() ;){
                double bag = bags.get(i);
                if(bag <= limit){
                    tour.add(bag);
                    limit -= bag;
                    bags.remove(i);
                }else{
                    i++;
                }
            }
            tours.add(tour);
        }
        return tours;
    }
}
