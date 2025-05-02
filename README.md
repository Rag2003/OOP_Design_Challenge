# Music Streaming System

A demonstration of Object-Oriented Programming principles through a simulated music streaming system.

## Project Overview

This project implements a simplified music streaming system to showcase fundamental OOP concepts including inheritance, interfaces, polymorphism, and different types of coupling. The system allows for management of different types of audio content (songs and podcasts), users, playlists, and media playback.

## OOP Principles Demonstrated

### 1. Inheritance

The project features a class hierarchy for audio content:

- `AudioContent` (abstract base class)
  - `Song` (subclass)
  - `Podcast` (subclass)

All audio content shares common attributes like title, artist, and duration while each subclass adds specialized features.

### 2. Interface Implementation

- `Playable` interface defines the contract for media playback with methods:
  - `play()`
  - `pause()`
  - `stop()`
  - `getRemainingTime()`
- `MediaPlayer` class implements this interface to provide concrete playback functionality

### 3. Polymorphism

Two types of polymorphism are demonstrated:

- **Method Overriding:**
  - Both `Song` and `Podcast` override the `getInfo()` method to provide type-specific information
  - Each implements the abstract `getContentType()` method from the parent class

- **Method Overloading:**
  - `MediaPlayer.seekTo(int seconds)` - seeks to a specific time position
  - `MediaPlayer.seekTo(double percentage)` - seeks to a position based on percentage of total duration

### 4. Data Coupling

Data coupling is demonstrated in the `MediaPlayer` class:
- `canSkipForward(int seconds)` method takes a primitive data type parameter
- It uses this simple data to determine if skipping forward is possible

### 5. Stamp Coupling

Stamp coupling is shown in the `User` class:
- `addToPlaylist(Playlist playlist, AudioContent content)` method passes two complex objects
- The method works with these objects to add content to a user's playlist

## Class Structure

- **AudioContent:** Abstract base class for all playable content
- **Song:** Represents music tracks with album and genre information
- **Podcast:** Represents podcast episodes with host and episode number
- **Playable:** Interface defining media playback operations
- **MediaPlayer:** Handles the actual playback of audio content
- **User:** Represents system users with personal information
- **Playlist:** Maintains collections of audio content
- **MusicStreamingSystem:** Main class with demonstration code



## Assignment Context

This implementation was created as part of SE/CprE 4160 Assignment #1 to demonstrate OOP principles in a cohesive application design.