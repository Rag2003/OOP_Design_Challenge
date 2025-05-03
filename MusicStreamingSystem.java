import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Base abstract class for all audio content (no changes needed - already well-designed)
abstract class AudioContent {
    protected final String title;
    protected final String artist;
    protected final int duration; // in seconds
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

// Enhanced Song class with caching
class Song extends AudioContent {
    private final String album;
    private final String genre;
    private String cachedInfo;  // Cached info string for performance
    
    public Song(String title, String artist, int duration, String album, String genre) {
        super(title, artist, duration);
        this.album = album;
        this.genre = genre;
    }
    
    @Override
    public String getContentType() {
        return "Song";
    }
    
    // Method overriding - optimize with caching
    @Override
    public String getInfo() {
        if (cachedInfo == null) {
            cachedInfo = super.getInfo() + " - Album: " + album + ", Genre: " + genre;
        }
        return cachedInfo;
    }
    
    // Getters
    public String getAlbum() {
        return album;
    }
    
    public String getGenre() {
        return genre;
    }
}

// Enhanced Podcast class with caching
class Podcast extends AudioContent {
    private final String host;
    private final int episodeNumber;
    private String cachedInfo;  // Cached info string for performance
    
    public Podcast(String title, String artist, int duration, String host, int episodeNumber) {
        super(title, artist, duration);
        this.host = host;
        this.episodeNumber = episodeNumber;
    }
    
    @Override
    public String getContentType() {
        return "Podcast";
    }
    
    // Method overriding - optimize with caching
    @Override
    public String getInfo() {
        if (cachedInfo == null) {
            cachedInfo = super.getInfo() + " - Host: " + host + ", Episode: " + episodeNumber;
        }
        return cachedInfo;
    }
    
    // Getters
    public String getHost() {
        return host;
    }
    
    public int getEpisodeNumber() {
        return episodeNumber;
    }
}

// Interface definition (no changes needed)
interface Playable {
    void play();
    void pause();
    void stop();
    int getRemainingTime();
}

// Enhanced MediaPlayer with optimization techniques
class MediaPlayer implements Playable {
    private AudioContent currentContent;
    private boolean isPlaying;
    private int currentPosition; // in seconds
    private boolean hasContent;  // Flag to minimize redundant checks
    
    public MediaPlayer() {
        this.isPlaying = false;
        this.currentPosition = 0;
        this.hasContent = false;
    }
    
    public void loadContent(AudioContent content) {
        this.currentContent = content;
        this.currentPosition = 0;
        this.hasContent = (content != null);
    }
    
    // Interface method implementations - optimized
    @Override
    public void play() {
        if (hasContent) {
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
        if (!hasContent) {
            return 0;
        }
        return currentContent.getDuration() - currentPosition;
    }
    
    // Method overloading - optimized input validation
    public void seekTo(int seconds) {
        int duration = hasContent ? currentContent.getDuration() : 0;
        if (hasContent && seconds >= 0 && seconds <= duration) {
            currentPosition = seconds;
            System.out.println("Seeking to " + seconds + " seconds");
        }
    }
    
    // Method overloading - precomputed percentage conversion
    public void seekTo(double percentage) {
        if (hasContent && percentage >= 0 && percentage <= 100) {
            int seconds = (int)(percentage * 0.01 * currentContent.getDuration());
            currentPosition = seconds;
            System.out.println("Seeking to " + percentage + "% (" + seconds + " seconds)");
        }
    }
    
    // Data coupling example - optimized with early return
    public boolean canSkipForward(int seconds) {
        return hasContent && ((currentPosition + seconds) < currentContent.getDuration());
    }
}

// User class - minimal changes needed
class User {
    private final String username;
    private final String email;
    private final boolean isPremium;
    
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
    
    // Stamp coupling - optimized message construction
    public void addToPlaylist(Playlist playlist, AudioContent content) {
        playlist.addContent(content);
        System.out.println(username + " added \"" + content.getTitle() + "\" to playlist: " + playlist.getName());
    }
}

// Enhanced Playlist class with optimization techniques
class Playlist {
    private final String name;
    private final ArrayList<AudioContent> contents;
    private int totalDuration;  // Cached total duration
    private boolean durationCached;  // Flag for cache validity
    
    public Playlist(String name) {
        this.name = name;
        this.contents = new ArrayList<>();
        this.totalDuration = 0;
        this.durationCached = true;
    }
    
    public String getName() {
        return name;
    }
    
    public void addContent(AudioContent content) {
        contents.add(content);
        durationCached = false;  // Invalidate cache
    }
    
    public void removeContent(AudioContent content) {
        contents.remove(content);
        durationCached = false;  // Invalidate cache
    }
    
    public ArrayList<AudioContent> getContents() {
        return contents;
    }
    
    // Optimized with caching to avoid redundant calculations
    public int getTotalDuration() {
        if (!durationCached) {
            totalDuration = 0;
            // Optimize loop to minimize array accesses
            int size = contents.size();
            for (int i = 0; i < size; i++) {
                totalDuration += contents.get(i).getDuration();
            }
            durationCached = true;
        }
        return totalDuration;
    }
    
    // Optimized toString with strength reduction and minimized work
    @Override
    public String toString() {
        // Pre-compute frequently used values
        int size = contents.size();
        int duration = getTotalDuration();
        
        // Use StringBuilder capacity to minimize resizing
        StringBuilder result = new StringBuilder(200);
        result.append("Playlist: ").append(name).append("\n");
        result.append("Total duration: ").append(duration).append(" seconds\n");
        result.append("Contents:\n");
        
        // Optimized loop with no repeated array accesses
        for (int i = 0; i < size; i++) {
            result.append(i + 1).append(". ").append(contents.get(i).getInfo()).append("\n");
        }
        
        return result.toString();
    }
}

// Enhanced main class with performance optimizations
public class MusicStreamingSystem {
    public static void main(String[] args) {
        // Create songs with constant values
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
        
        // Display playlist (optimized toString will be faster)
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