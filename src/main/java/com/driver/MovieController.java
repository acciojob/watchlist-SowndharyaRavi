package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
//@RequestMapping("movies")
public class MovieController {

    @Autowired
    MovieService movieService;

    @PostMapping("movies/add-movie")
    public ResponseEntity addMovie(@RequestBody Movie movie){
        String response=movieService.addMovie(movie);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("movies/add-director")
    public ResponseEntity addDirector(@RequestBody Director director){
        String response=movieService.addDirector(director);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("movies/add-movie-director-pair")
    public ResponseEntity addMovieDirectorPair(@RequestParam("movie") String movie,@RequestParam("director") String director){
         String response=movieService.addMovieDirectorPair(movie,director);
         if(response.equals("Movie or Director not found")){
             return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
         }
         else if (response.equals("pair already exists")) {
             return new ResponseEntity<>(response,HttpStatus.ALREADY_REPORTED);
         }
         else{
             return new ResponseEntity<>(response,HttpStatus.CREATED);
         }
    }

    @GetMapping("movies/get-movie-by-name/{name}")
    public ResponseEntity getMovieByName(@PathVariable("name") String name){
        Movie movie=movieService.getMovieByName(name);
        if(movie==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(movie,HttpStatus.FOUND);
        }
    }

    @GetMapping("movies/get-director-by-name/{name}")
    public ResponseEntity getDirectorByName(@PathVariable("name") String name){
        Director director=movieService.getDirectorByName(name);
        if(director==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(director,HttpStatus.FOUND);
        }
    }

    @GetMapping("movies/get-movies-by-director-name/{director}")
    public ResponseEntity getMoviesByDirectorName(@PathVariable("director") String name){
        List<String> list=movieService.getMoviesByDirectorName(name);
        if(list.isEmpty()){
            return new ResponseEntity<>(list,HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(list,HttpStatus.FOUND);
        }
    }

    @GetMapping("movies/get-all-movies")
    public ResponseEntity findAllMovies(){
        List<String>list=movieService.findAllMovies();
        return new ResponseEntity<>(list,HttpStatus.FOUND);
    }

    @DeleteMapping("movies/delete-director-by-name")
    public ResponseEntity deleteDirectorByName(@RequestParam("name") String name){
        String response=movieService.deleteDirectorByName(name);
        return new ResponseEntity<>(response,HttpStatus.FOUND);
    }

    @DeleteMapping("movies/delete-all-directors")
    public ResponseEntity deleteAllDirectors(){
        String response=movieService.deleteAllDirectors();
        return new ResponseEntity<>(response,HttpStatus.FOUND);
    }
}
