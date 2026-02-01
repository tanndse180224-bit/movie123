package hsf302.myMovie.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MovieID")
    private int id;

    @Nationalized
    @Column(name = "MovieName")
    private String movieName;

    @Nationalized
    @Column(name = "Description")
    private String description;

    @Column(name = "ReleaseYear")
    private int releaseYear;

    @Column(name = "Rating")
    private int rating;

    @Column(name = "MovieURL")
    private String movieURL;


    @Column(name = "ThumbnailURL", length = 500)
    private String thumbnailURL;


    @Column(name="TrailerURL")
    private String trailerURL;

    @OneToMany (mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FavoriteMovie> favoriteMovies = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CountryID")
    private Country country;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MovieGenre> movieGenres = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();


    public Movie() {
    }

    public Movie(String movieName, String description, int releaseYear, int rating, String movieURL, String thumbnailURL, String trailerURL, Country country) {
        this.movieName = movieName;
        this.description = description;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.movieURL = movieURL;
        this.thumbnailURL = thumbnailURL;
        this.trailerURL = trailerURL;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMovieURL() {
        return movieURL;
    }

    public void setMovieURL(String movieURL) {
        this.movieURL = movieURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
