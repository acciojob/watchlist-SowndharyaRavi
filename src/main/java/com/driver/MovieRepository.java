package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MovieRepository {

    Map<String,Movie> movieDB=new HashMap<>();
    Map<String,Director> directorDB=new HashMap<>();
    Map<String,List<String>>pairDB=new HashMap<>();

    //1
    public String addMovie(Movie movie){
        String name=movie.getName();
        movieDB.put(name,movie);
        return "Added Successfully";
    }

    //2
    public String addDirector(Director director){
        String name=director.getName();
        directorDB.put(name,director);
        return "Added Successfully";
    }

    //3
    public String addMovieDirectorPair(String movie,String director){
           if(!movieDB.containsKey(movie) || !directorDB.containsKey(director)){
               return "Movie or Director not found";
           }
           else {
               List<String> list = pairDB.getOrDefault(director,new ArrayList<>());
               if(list.contains(movie)){
                   return "pair already exists";
               }
               else{
                   list.add(movie);
                   pairDB.put(director,list);
                   return "pair added successfully";
               }
           }
    }

    //4
    public Movie getMovieByName(String name){
        if(movieDB.containsKey(name)){
            return movieDB.get(name);
        }
        else{
            return null;
        }
    }

    //5
    public Director getDirectorByName(String name){
        if(directorDB.containsKey(name)){
            return directorDB.get(name);
        }
        else{
            return null;
        }
    }

    //6
    public List<String> getMoviesByDirectorName(String name){
        List<String>list=new ArrayList<>();
        if(pairDB.containsKey(name)){
            for(String s:pairDB.get(name)){
                list.add(s);
            }
            return list;
        }
        else{
            return null;
        }
    }

    //7
    public List<String>findAllMovies(){
        List<String>list=new ArrayList<>();
        for(String s:movieDB.keySet()){
            list.add(s);
        }
        return list;
    }

    //8
    public String deleteDirectorByName(String name){
        List<String>list=new ArrayList<>();
        if(pairDB.containsKey(name)){
            list=pairDB.get(name);
        }
        for(String s:list){
            if(movieDB.containsKey(s)){
                movieDB.remove(s);
            }
        }
        pairDB.remove(name);
        if(directorDB.containsKey(name)){
            directorDB.remove(name);
        }
        return "Successfully deleted";
    }

    //9
    public String deleteAllDirectors(){
        for(String s:pairDB.keySet()){
            List<String>list=new ArrayList<>();
            list=pairDB.get(s);
            for(String temp:list){
                if(movieDB.containsKey(temp)){
                    movieDB.remove(temp);
                }
            }
            pairDB.remove(s);
        }
        for(String s:directorDB.keySet()){
            directorDB.remove(s);
        }
        return "deleted all directors and their movies successfully";
    }
}
