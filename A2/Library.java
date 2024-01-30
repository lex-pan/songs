//Name: Lex Pan
//Student ID: 501166630

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.lang.RuntimeException;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
  //private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  //podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(ArrayList<AudioContent> content)
	{	
		for (int i = 0; i < content.size(); i++) {
			// gets audio content type in order to compare to objects of the same class
			String audioContentType = content.get(i).getType();
			if (audioContentType.equals("SONG")) {
				Song convertToSong = (Song) content.get(i);
				boolean uniqueSong = true;
				// loops through list of songs to check if the song already exists
				// if the song already exists, send err msg, else add to list of songs
				for (int a = 0; a < songs.size(); a++) {
					try {
						if (convertToSong.equals(songs.get(a))) {
							uniqueSong = false;
							throw new AudioContentAlreadyExistsException("Song " + convertToSong.getTitle() + " already downloaded");
						} 
					} catch (AudioContentAlreadyExistsException error) {
						String errorMSG = error.getMessage();
						System.out.println(errorMSG);
					}

				}
				if (uniqueSong) {
					System.out.println("SONG " + content.get(i).getTitle() + " Added to Library");
					songs.add(convertToSong);
				}
			
			} else if (audioContentType.equals("AUDIOBOOK")) {
				AudioBook convertToAudiobook = (AudioBook) content.get(i);
				boolean uniqueAudiobook = true;
				// loops through list of audiobooks to check if the audiobooks already exists
				// if the audiobook already exists, send err msg, else add to list of audiobooks
				for (int a = 0; a < audiobooks.size(); a++) {
					try {
						if (convertToAudiobook.equals(audiobooks.get(a))) {
							uniqueAudiobook = false;
							throw new AudioContentAlreadyExistsException("Audiobook " + convertToAudiobook.getTitle() + " already downloaded");
						} 
					} catch (AudioContentAlreadyExistsException error) {
						String errorMSG = error.getMessage();
						System.out.println(errorMSG);
					}
				}		

				if (uniqueAudiobook) {
					System.out.println("Audiobook " + content.get(i).getTitle() + " Added to Library");
					audiobooks.add(convertToAudiobook);
				}

			}
		}



	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			//get the index position and info of the song, then print them on the same line
			int index = i + 1;
			System.out.println();
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.println();
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();	
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for (int i=0; i < playlists.size(); i++) {
			System.out.println(playlists.get(i).getTitle());
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList<String> uniqueArtists = new ArrayList<String>();
		for (int i = 0; i < songs.size(); i++) {
			if (!uniqueArtists.contains(songs.get(i).getArtist())) {
				uniqueArtists.add(songs.get(i).getArtist());
			}
		}
		
		for (int i = 0; i < uniqueArtists.size(); i++) {
			int position = i + 1;
			System.out.println(position + ". " + uniqueArtists.get(i));
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index)
	{	
		// checks if valid index
		try {
			if (index > songs.size() && index > 0) {
				throw new InvalidIndex(index + " is greater than songs library index");
			}

			Song songToBeRemoved = songs.get(index-1);

			// removes the song from playlists containing the song
			// iterates through the playlists and checks the songs in all playlists
			for (int i = 0; i < playlists.size(); i++) {
				Playlist checkPlaylistContent = playlists.get(i);
				for (int a = 0; a < checkPlaylistContent.getContent().size(); a++) {
					if (songToBeRemoved.equals(checkPlaylistContent.getContent().get(a))) {
						checkPlaylistContent.deleteContent(a+1);
					}
				}
				
			}
	
			// removes the song from the lsit of songs
			songs.remove(index-1);
		} catch (InvalidIndex error) {
			String errorMSG = error.getMessage();
			System.out.println(errorMSG);
		}
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator());
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song songOne, Song songTwo) {
			if  (songOne.getYear() > songTwo.getYear()) {
				return 1;
			}  else if (songOne.getYear() == songTwo.getYear()) {
				return 0;
			}  else {
				return -1;
			}
		}	
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort() 
	 Collections.sort(songs, new SongLengthComparator());
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		// compares length of two songs and returns 1, 0, or -1 depending on comparison
		public int compare(Song songOne, Song songTwo) {
			if  (songOne.getLength() > songTwo.getLength()) {
				return 1;
			}  else if (songOne.getLength() == songTwo.getLength()) {
				return 0;
			}  else {
				return -1;
			} 
		}	
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code

		// need to implement java interfaces
		Collections.sort(songs);
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		try {
			if (index < 1 || index > songs.size())
			{
				throw new AudioContentNotFoundException("Song Not Found");
			}

			songs.get(index-1).play();

		} catch (AudioContentNotFoundException error) {
			String errorMSG = error.getMessage();
			System.out.println(errorMSG);
		}

	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public boolean playPodcast(int index, int season, int episode)
	{
		return false;
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public boolean printPodcastEpisodes(int index, int season)
	{
		return false;
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		try {
			if (index < 1 || index > audiobooks.size()) {
				throw new AudioContentNotFoundException("Audiobook Not Found");
			}
			
			//gets chapter and plays it
			audiobooks.get(index-1).selectChapter(chapter);
			audiobooks.get(index-1).play();

		} catch (AudioContentNotFoundException error) {
			String errorMSG = error.getMessage();
			System.out.println(errorMSG);
		}

	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		try {
		// checks if valid index
		if (index < 1 || index > audiobooks.size())
			{
				throw new AudioContentNotFoundException("Audiobook Not Found");
			}

			audiobooks.get(index-1).printTOC();

		} catch (AudioContentNotFoundException error) {
			String errorMSG = error.getMessage();
			System.out.println(errorMSG);
		}
		// prints table of content
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{
		// for this function, it wouldn't make sense to compare the two objects since an object that already exists
		// may have audiocontent within the playlist rendering the equals function in playlist useless
		boolean uniquePlaylist = true;
		for (int i = 0; i < playlists.size(); i++) {
			try {
				String playListTitle = playlists.get(i).getTitle();
				if (title.equals(playListTitle)) {
					uniquePlaylist = false;
					throw new PlaylistAlreadyExists("Playlist already exists");
				} 
			} catch (PlaylistAlreadyExists error) {
				String errorMSG = error.getMessage();
				System.out.println(errorMSG);
			}

		}

		if (uniquePlaylist) {
			Playlist newPlaylistName = new Playlist(title);
			playlists.add(newPlaylistName);
		}

	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{
		Playlist chosenPlaylist = new Playlist(title);
		boolean playlistFound = false;
		// loops through list of titles finding the right title, and prints the contents within the correct playlist
		for (int i = 0; i < playlists.size(); i++) {
			if (title.equals(playlists.get(i).getTitle())) {
				chosenPlaylist = playlists.get(i);
				chosenPlaylist.printContents();
				playlistFound = true;
			}
		}

		try {
			if (!playlistFound) {
				throw new PlaylistDoesNotExist(title + " playlist does not exist");
			}
		} catch (PlaylistDoesNotExist error) {
			String errorMSG = error.getMessage();
			System.out.println(errorMSG);
		}

	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{
		Playlist chosenPlaylist = new Playlist(playlistTitle);
		boolean playlistExists = false;

		// checks if playlist exists
		for (int i = 0; i < playlists.size(); i++) {
			if (playlistTitle.equals(playlists.get(i).getTitle())) {
				chosenPlaylist = playlists.get(i);
				playlistExists = true;
			}
		}
		
		try {
			if (!playlistExists) {
				throw new PlaylistDoesNotExist(playlistTitle + " playlist does not exist");
			}
			chosenPlaylist.playAll();

		} catch (PlaylistDoesNotExist error) {
			String errorMSG = error.getMessage();
			System.out.println(errorMSG);
		}

		
	}
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		boolean playListTitleExists = false;
		Playlist chosenPlaylist = new Playlist(playlistTitle);

		//checks if playlist exists
		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().equals(playlistTitle)) {
				playListTitleExists = true;
				chosenPlaylist = playlists.get(i);
			}
		}
		try {
			if (!playListTitleExists) {
				throw new PlaylistDoesNotExist(playlistTitle + " playlist does not exist");
			}			
		} catch (PlaylistDoesNotExist error) {
			String errorMSG = error.getMessage();
			System.out.println(errorMSG);
		}

		try {
			if (indexInPL > chosenPlaylist.getContent().size()) {
				throw new InvalidIndex(indexInPL + " is greater than the size of the playlist");
			}
		} catch (InvalidIndex error) {
			String errorMSG = error.getMessage();
			System.out.println(errorMSG);
		}


		chosenPlaylist.play(indexInPL-1);		

	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{	
		Playlist playlistChosen = new Playlist("default");
		
		//checks if valid playlist
		boolean validPlayList = false;
		for (int a = 0; a < playlists.size(); a++) {
			if (playlists.get(a).getTitle().equals(playlistTitle)) {
				playlistChosen = playlists.get(a);
				validPlayList = true;
			}
		}

		try {
			if (!validPlayList) {
				throw new PlaylistDoesNotExist(playlistTitle + " playlist does not exist");
			} 
		} catch (PlaylistDoesNotExist error) {
			String errorMSG = error.getMessage();
			System.out.println(errorMSG);
		}


		//adds to playlist based on audiocontent type
		if (type.equalsIgnoreCase(Song.TYPENAME)) {
			Song songToPlayList = songs.get(index-1);
			playlistChosen.addContent(songToPlayList);
		} else if (type.equalsIgnoreCase(AudioBook.TYPENAME)) {
			AudioBook audiobookToPlaylist = audiobooks.get(index-1);
			playlistChosen.addContent(audiobookToPlaylist);
		} else {
			System.out.println("invalid type");
		}

	}

    // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title)
	{
		boolean playListTitleExists = false;
		Playlist chosenPlaylist = new Playlist(title);

		// checks if valid playlist
		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().equals(title)) {
				playListTitleExists = true;
				chosenPlaylist = playlists.get(i);
			}
		}

		try {
			if (!playListTitleExists) {
				throw new PlaylistDoesNotExist(title + " playlist does not exist");
			}			
		} catch (PlaylistDoesNotExist error) {
			String errorMSG = error.getMessage();
			System.out.println(errorMSG);
		}

		try {
			if (index > chosenPlaylist.getContent().size()) {
				throw new InvalidIndex(index + " is greater than the size of the playlist");
			}
		} catch (InvalidIndex error) {
			String errorMSG = error.getMessage();
			System.out.println(errorMSG);
		}

		chosenPlaylist.deleteContent(index);
	}
	
}

class AudioContentNotFoundException extends RuntimeException {
	public AudioContentNotFoundException() {
		
	}
	public AudioContentNotFoundException(String errorMsg) {
		super(errorMsg);
	}
}

class AudioContentAlreadyExistsException extends RuntimeException {
	public AudioContentAlreadyExistsException() {
	}
	public AudioContentAlreadyExistsException(String errorMsg) {
		super(errorMsg);
	}
}

class PlaylistDoesNotExist extends RuntimeException {
	public PlaylistDoesNotExist() {
	}
	public PlaylistDoesNotExist(String errorMsg) {
		super(errorMsg);
	}
}

class PlaylistAlreadyExists extends RuntimeException {
	public PlaylistAlreadyExists() {
	}
	public PlaylistAlreadyExists(String errorMsg) {
		super(errorMsg);
	}
}

class InvalidIndex extends RuntimeException {
	public InvalidIndex() {
	}

	public InvalidIndex(String errorMsg) {
		super(errorMsg);
	}
}
