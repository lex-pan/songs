//Name: Lex Pan
//Student ID: 501166630

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;



// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				mylibrary.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				mylibrary.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
			{
				mylibrary.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				mylibrary.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				mylibrary.listAllPlaylists(); 
			}
			// Download audiocontent (song/audiobook/podcast) from the store 
			// Specify the index of the content
			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
				int index = 0;
				int indexTwo = 0;
				System.out.print("From Store Content #: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					System.out.print("To Store Content #: ");
					indexTwo = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				ArrayList<AudioContent> content = store.getContent(index, indexTwo);
				if (content == null) {
					System.out.println("Content Not Found in Store");
				}

				mylibrary.download(content);

									
			}
			// Get the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song 
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				// Print error message if the song doesn't exist in the library
				int index = 0;
				
				System.out.print("Song Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}

				mylibrary.playSong(index);

			}
			// Print the table of contents (TOC) of an audiobook that
			// has been downloaded to the library. Get the desired book index
			// from the keyboard - the index is based on the list of books in the library
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
			// Print error message if the book doesn't exist in the library
				int index = 0;
					
				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}

				mylibrary.printAudioBookTOC(index);

			}
			// Similar to playsong above except for audio book
			// In addition to the book index, read the chapter 
			// number from the keyboard - see class Library
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				int bookNumber = 0;
				int chapterNumber = 0;
				
				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					bookNumber = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}

				System.out.print("Chapter: ");
				if (scanner.hasNextInt())
				{
					chapterNumber = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}

				mylibrary.playAudioBook(bookNumber, chapterNumber);

			}
			// Print the episode titles for the given season of the given podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PODTOC")) 
			{
				
			}
			// Similar to playsong above except for podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number and the episode number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPOD")) 
			{
				
			}
			// Specify a playlist title (string) 
			// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				String playlistTitle = "";
				System.out.print("Playlist Title: ");
				if (scanner.hasNext()) {
					playlistTitle = scanner.next();
				}

				mylibrary.playPlaylist(playlistTitle);
			}
			// Specify a playlist title (string) 
			// Read the index of a song/audiobook/podcast in the playist from the keyboard 
			// Play all the audio content 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				String playlistTitle = "";
				int contentNumber = 0;

				System.out.print("Playlist Title: ");
				if (scanner.hasNext()) {
					playlistTitle = scanner.next();
				}

				System.out.print("Content Number: ");
				if (scanner.hasNextInt()) {
					contentNumber = scanner.nextInt();
				}

				mylibrary.playPlaylist(playlistTitle, contentNumber);
			}
			// Delete a song from the list of songs in mylibrary and any play lists it belongs to
			// Read a song index from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				int indexNumber = 0;
				System.out.print("Library Song #: ");

				if (scanner.hasNextInt()) {
					indexNumber = scanner.nextInt();
					mylibrary.deleteSong(indexNumber);
					scanner.nextLine();
				}
			}
			// Read a title string from the keyboard and make a playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{	

				String playListName = "";
				
				System.out.print("Playlist Title: ");
				if (scanner.hasNext()) {
					playListName = scanner.next();
					scanner.nextLine();
				}

				mylibrary.makePlaylist(playListName);

				
			}
			// Print the content information (songs, audiobooks, podcasts) in the playlist
			// Read a playlist title string from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				String playlistName = "";

				System.out.print("Playlist Title: ");
				if (scanner.hasNext()) {
					playlistName = scanner.next();
					scanner.nextLine();
				}

				mylibrary.printPlaylist(playlistName);
			}
			// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
			// Read the playlist title, the type of content ("song" "audiobook" "podcast")
			// and the index of the content (based on song list, audiobook list etc) from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				String playListTitle = "";
				String contentType = "";
				int contentIndexNumber = 0;

				System.out.print("Playlist Title: ");
				if (scanner.hasNext()) {
					playListTitle = scanner.next();
				}

				System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");
				if  (scanner.hasNext()) {
					contentType = scanner.next();
				}

				System.out.print("Library Content #: ");
				if (scanner.hasNextInt()) {
					contentIndexNumber = scanner.nextInt();
				}

				mylibrary.addContentToPlaylist(contentType, contentIndexNumber, playListTitle);

			}
			// Delete content from play list based on index from the playlist
			// Read the playlist title string and the playlist index
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				String playlistTitle = "";
				int contentNumber = 0;

				System.out.print("Playlist Title: ");
				if (scanner.hasNext()) {
					playlistTitle = scanner.next();
				}

				System.out.print("Playlist Content: ");
				if (scanner.hasNextInt()) {
					contentNumber = scanner.nextInt();
				}

				mylibrary.delContentFromPlaylist(contentNumber, playlistTitle);
			}
			
			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			}

			// New Actions
			else if (action.equalsIgnoreCase("SEARCH")) // sort songs by length
			{
				String audioContentTitle = "";
				System.out.print("Title: ");
				if (scanner.hasNextLine()) {
					audioContentTitle = scanner.nextLine();
				}

				store.printAudioContentByTitle(audioContentTitle);
				System.out.println("");

			}

			else if (action.equalsIgnoreCase("SEARCHA")) // sort songs by length
			{
				String artistName = "";
				System.out.print("Artist: ");
				if (scanner.hasNextLine()) {
					artistName = scanner.nextLine();
				}

				store.printAudioContentByArtist(artistName);

			}

			else if (action.equalsIgnoreCase("SEARCHG")) // sort songs by length
			{
				String genreType = "";
				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
				if (scanner.hasNextLine()) {
					genreType = scanner.nextLine();
				}

				Song.Genre songGenre = Song.Genre.POP;
				boolean validEntry = false;

				// checks if valid genre
				if (genreType.equalsIgnoreCase("POP")) {
					songGenre = Song.Genre.POP;
					validEntry = true;
				} else if (genreType.equalsIgnoreCase("ROCK")) {
					songGenre = Song.Genre.ROCK;
					validEntry = true;
				} else if (genreType.equalsIgnoreCase("JAZZ")) {
					songGenre = Song.Genre.JAZZ;
					validEntry = true;
				} else if (genreType.equalsIgnoreCase("HIPHOP")) {
					songGenre = Song.Genre.HIPHOP;
					validEntry = true;
				} else if (genreType.equalsIgnoreCase("RAP")) {
					songGenre = Song.Genre.RAP;
					validEntry = true;
				} else if (genreType.equalsIgnoreCase("CLASSICAL")) {
					songGenre = Song.Genre.CLASSICAL;
					validEntry = true;
				} 

				if (validEntry) {
					store.printAudioContentByGenre(songGenre);
				} else {
					System.out.println("not a valid genre");
				}


			}

			else if (action.equalsIgnoreCase("DOWNLOADA")) // sort songs by length
			{
				String artistName = "";
				System.out.print("Artist Name: ");
				if (scanner.hasNextLine())
				{
					artistName = scanner.nextLine();
				}
				ArrayList<AudioContent> content = store.getArtistContent(artistName);
				if (content == null) {
					System.out.println("Artist Does Not Exist");
				} else {
					mylibrary.download(content);
				}
						
			}

			else if (action.equalsIgnoreCase("DOWNLOADG")) // sort songs by length
			{
				String genreType = "";
				System.out.print("Genre: ");
				if (scanner.hasNextLine())
				{
					genreType = scanner.nextLine();
				}

				Song.Genre songGenre = Song.Genre.POP;
				boolean validEntry = false;

				// checks if valid genre
				if (genreType.equalsIgnoreCase("POP")) {
					songGenre = Song.Genre.POP;
					validEntry = true;
				} else if (genreType.equalsIgnoreCase("ROCK")) {
					songGenre = Song.Genre.ROCK;
					validEntry = true;
				} else if (genreType.equalsIgnoreCase("JAZZ")) {
					songGenre = Song.Genre.JAZZ;
					validEntry = true;
				} else if (genreType.equalsIgnoreCase("HIPHOP")) {
					songGenre = Song.Genre.HIPHOP;
					validEntry = true;
				} else if (genreType.equalsIgnoreCase("RAP")) {
					songGenre = Song.Genre.RAP;
					validEntry = true;
				} else if (genreType.equalsIgnoreCase("CLASSICAL")) {
					songGenre = Song.Genre.CLASSICAL;
					validEntry = true;
				} 

				ArrayList<AudioContent> content;
				if (validEntry) {
					content = store.getGenreContent(songGenre);
					if (content == null ) {
						System.out.println("No songs with this genre");
					}
					else {
						mylibrary.download(content);
					}
				} else{
					System.out.println("Invalid genre");
				}


			}

			System.out.print("\n>");
		}
	}
}
