import java.util.ArrayList;

// Base abstract class for all audio content
abstract class AudioContent {
    protected String title;
    protected String artist;
    protected int duration; // in seconds
    protected int playCount;
    
    public AudioContent(String title, String artist, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.playCount = 0;
    }
    
    // Abstract method to be implemented by subclasses
    public abstract String getContentType();
    
    // Method that will be overridden (polymorphism)
    public String getInfo() {
        return title + " by " + artist + " (" + duration + " seconds)";
    }
    
    // Getters and setters
    public String getTitle() {
        return title;
    }
    
    public String getArtist() {
        return artist;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public int getPlayCount() {
        return playCount;
    }
    
    public void incrementPlayCount() {
        playCount++;
    }
}

// First subclass that inherits from AudioContent (inheritance)
class Song extends AudioContent {
    private String album;
    private String genre;
    
    public Song(String title, String artist, int duration, String album, String genre) {
        super(title, artist, duration);
        this.album = album;
        this.genre = genre;
    }
    
    @Override
    public String getContentType() {
        return "Song";
    }
    
    // Method overriding (polymorphism)
    @Override
    public String getInfo() {
        return super.getInfo() + " - Album: " + album + ", Genre: " + genre;
    }
    
    // Getters
    public String getAlbum() {
        return album;
    }
    
    public String getGenre() {
        return genre;
    }
}

// Second subclass that inherits from AudioContent (inheritance)
class Podcast extends AudioContent {
    private String host;
    private int episodeNumber;
    
    public Podcast(String title, String artist, int duration, String host, int episodeNumber) {
        super(title, artist, duration);
        this.host = host;
        this.episodeNumber = episodeNumber;
    }
    
    @Override
    public String getContentType() {
        return "Podcast";
    }
    
    // Method overriding (polymorphism)
    @Override
    public String getInfo() {
        return super.getInfo() + " - Host: " + host + ", Episode: " + episodeNumber;
    }
    
    // Getters
    public String getHost() {
        return host;
    }
    
    public int getEpisodeNumber() {
        return episodeNumber;
    }
}

// Interface definition
interface Playable {
    void play();
    void pause();
    void stop();
    int getRemainingTime();
}

// Class implementing the Playable interface
class MediaPlayer implements Playable {
    private AudioContent currentContent;
    private boolean isPlaying;
    private int currentPosition; // in seconds
    
    public MediaPlayer() {
        this.isPlaying = false;
        this.currentPosition = 0;
    }
    
    public void loadContent(AudioContent content) {
        this.currentContent = content;
        this.currentPosition = 0;
    }
    
    // Interface method implementations
    @Override
    public void play() {
        if (currentContent != null) {
            isPlaying = true;
            System.out.println("Playing: " + currentContent.getInfo());
            currentContent.incrementPlayCount();
        } else {
            System.out.println("No content loaded");
        }
    }
    
    @Override
    public void pause() {
        if (isPlaying) {
            isPlaying = false;
            System.out.println("Paused at " + currentPosition + " seconds");
        }
    }
    
    @Override
    public void stop() {
        isPlaying = false;
        currentPosition = 0;
        System.out.println("Stopped playback");
    }
    
    @Override
    public int getRemainingTime() {
        if (currentContent == null) {
            return 0;
        }
        return currentContent.getDuration() - currentPosition;
    }
    
    // Method overloading (polymorphism)
    public void seekTo(int seconds) {
        if (currentContent != null && seconds >= 0 && seconds <= currentContent.getDuration()) {
            currentPosition = seconds;
            System.out.println("Seeking to " + seconds + " seconds");
        }
    }
    
    // Method overloading (polymorphism)
    public void seekTo(double percentage) {
        if (currentContent != null && percentage >= 0 && percentage <= 100) {
            int seconds = (int)(percentage / 100 * currentContent.getDuration());
            currentPosition = seconds;
            System.out.println("Seeking to " + percentage + "% (" + seconds + " seconds)");
        }
    }
    
    // Data coupling example - primitive data type parameter
    public boolean canSkipForward(int seconds) {
        if (currentContent == null) {
            return false;
        }
        return (currentPosition + seconds) < currentContent.getDuration();
    }
}

// User class for the system
class User {
    private String username;
    private String email;
    private boolean isPremium;
    
    public User(String username, String email, boolean isPremium) {
        this.username = username;
        this.email = email;
        this.isPremium = isPremium;
    }
    
    // Getters
    public String getUsername() {
        return username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public boolean isPremium() {
        return isPremium;
    }
    
    // Stamp coupling example - object parameter
    public void addToPlaylist(Playlist playlist, AudioContent content) {
        playlist.addContent(content);
        System.out.println(username + " added \"" + content.getTitle() + "\" to playlist: " + playlist.getName());
    }
}

// Playlist class for organizing audio content
class Playlist {
    private String name;
    private ArrayList<AudioContent> contents;
    
    public Playlist(String name) {
        this.name = name;
        this.contents = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public void addContent(AudioContent content) {
        contents.add(content);
    }
    
    public void removeContent(AudioContent content) {
        contents.remove(content);
    }
    
    public ArrayList<AudioContent> getContents() {
        return contents;
    }
    
    public int getTotalDuration() {
        int total = 0;
        for (AudioContent content : contents) {
            total += content.getDuration();
        }
        return total;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Playlist: ").append(name).append("\n");
        result.append("Total duration: ").append(getTotalDuration()).append(" seconds\n");
        result.append("Contents:\n");
        
        for (int i = 0; i < contents.size(); i++) {
            result.append(i + 1).append(". ").append(contents.get(i).getInfo()).append("\n");
        }
        
        return result.toString();
    }
}

// Main class to demonstrate functionality
public class MusicStreamingSystem {
    public static void main(String[] args) {
        // Create songs
        Song song1 = new Song("Bohemian Rhapsody", "Queen", 354, "A Night at the Opera", "Rock");
        Song song2 = new Song("Shape of You", "Ed Sheeran", 233, "÷", "Pop");
        
        // Create podcasts
        Podcast podcast1 = new Podcast("The Science of Learning", "Educational Media", 1800, "Dr. Smith", 42);
        
        // Create users
        User user1 = new User("musicLover", "music@example.com", true);
        
        // Create playlist
        Playlist myFavorites = new Playlist("My Favorites");
        
        // Add content to playlist using stamp coupling
        user1.addToPlaylist(myFavorites, song1);
        user1.addToPlaylist(myFavorites, song2);
        user1.addToPlaylist(myFavorites, podcast1);
        
        // Display playlist
        System.out.println(myFavorites);
        
        // Create media player to play content
        MediaPlayer player = new MediaPlayer();
        
        // Load and play a song
        player.loadContent(song1);
        player.play();
        
        // Demonstrate method overloading (polymorphism)
        player.seekTo(120); // Seek to specific second
        player.seekTo(50.0); // Seek to percentage
        
        // Demonstrate data coupling
        boolean canSkip = player.canSkipForward(300);
        System.out.println("Can skip forward 300 seconds? " + canSkip);
        
        // Pause and stop
        player.pause();
        player.stop();
        
        // Switch to podcast
        player.loadContent(podcast1);
        player.play();
    }
}