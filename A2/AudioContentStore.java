//Name: Lex Pan
//Student ID: 501166630
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.SourceVersion;

import java.util.ArrayList;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		private ArrayList<AudioContent> contents; 
		private Map<String, Integer> audioContentTitleHashMap;
		private Map<String, ArrayList<Integer>> audioContentCreatorHashMap;
		private Map<Song.Genre, ArrayList<Integer>> genreHashMap;

		public AudioContentStore()
		{
			contents = new ArrayList<AudioContent>();
			
			try {
				File storeFile = new File("Store.txt");
				Scanner in = new Scanner(storeFile);
					
				while (in.hasNextLine()) {
					String audioContentType = in.nextLine();

					// if type song, then get song parameters and create new song and add to contents
					if (audioContentType.equals("SONG")) {
						String id = in.nextLine();
						String title = in.nextLine();
						int year = in.nextInt();
						int length = in.nextInt();
						in.nextLine();
						String artist = in.nextLine();
						String composer = in.nextLine();
						
						String genre = in.nextLine();
						Song.Genre songGenre = Song.Genre.POP;
						if (genre.equals("POP")) {
							songGenre = Song.Genre.POP;
						} else if (genre.equals("ROCK")) {
							songGenre = Song.Genre.ROCK;
						} else if (genre.equals("JAZZ")) {
							songGenre = Song.Genre.JAZZ;
						} else if (genre.equals("HIPHOP")) {
							songGenre = Song.Genre.HIPHOP;
						} else if (genre.equals("RAP")) {
							songGenre = Song.Genre.RAP;
						} else if (genre.equals("CLASSICAL")) {
							songGenre = Song.Genre.CLASSICAL;
						} else {
							throw new IllegalArgumentException();
						}

						String lyrics = "";
						int numOfLyrics = in.nextInt();
						in.nextLine();
						for (int i = 0; i < numOfLyrics; i++) {
							String lyricLine = in.nextLine();
							lyrics += lyricLine + "\n";
						
						}

						contents.add(new Song(title, year, id, AudioBook.TYPENAME, lyrics, length, artist, composer, songGenre, lyrics));
						System.out.println("LOADED SONG");
					}

					// else it's type audiobook, get audiobook parameters and create new audiobook and add to contents
					 else {
						String id = in.nextLine();
						String title = in.nextLine();
						int year = in.nextInt();
						int length = in.nextInt();
						in.nextLine();
						String author = in.nextLine();
						String narrator = in.nextLine();
						ArrayList<String> chapterTitles = new ArrayList<String>();
						ArrayList<String> chapters = new ArrayList<String>();
						int numOfChapters = in.nextInt();
						in.nextLine();
						for (int i = 0; i < numOfChapters; i++) {
							chapterTitles.add(in.nextLine());
						}

						for (int b = 0; b < numOfChapters; b++) {
							int numOfChapterContents = in.nextInt();
							in.nextLine();
							for (int a = 0; a < numOfChapterContents; a++) {
								chapters.add(in.nextLine());
							}
						}
						

						contents.add(new AudioBook(title, year, id, AudioBook.TYPENAME, "", length, author, narrator, chapterTitles, chapters));
						System.out.println("LOADED AUDIOBOOK");
					}

				}
				in.close();

			} catch (FileNotFoundException e) {
				System.out.println("Store file not found");
			} 

			// adds audiocontent title index to hash map
			audioContentTitleHashMap = new HashMap<String, Integer>(); 
			for (int i = 0; i < contents.size(); i++) {
				String audioContentName = contents.get(i).getTitle();
				audioContentTitleHashMap.put(audioContentName, i);
			}

			audioContentCreatorHashMap = new HashMap<String, ArrayList<Integer>>(); 
			for (int i = 0; i < contents.size(); i++) {
				String audioContentType = contents.get(i).getType();

				// adds artist songs index to hash map
				if (audioContentType.equals("SONG")) {
					Song songContent = (Song) contents.get(i);
					String artistName = songContent.getArtist();
					if (audioContentCreatorHashMap.get(artistName) != null) {
						ArrayList<Integer> artistAudioContents = audioContentCreatorHashMap.get(artistName);
						artistAudioContents.add(i);
					} else {
						audioContentCreatorHashMap.put(artistName, new ArrayList<Integer>());
						ArrayList<Integer> artistAudioContents = audioContentCreatorHashMap.get(artistName);
						artistAudioContents.add(i);
					}
				
				// adds author audiobooks index to hash map
				} else if (audioContentType.equals("AUDIOBOOK")) {
					AudioBook songContent = (AudioBook) contents.get(i);
					String authorName = songContent.getAuthor();
					if (audioContentCreatorHashMap.get(authorName) != null) {
						ArrayList<Integer> authorAudioContents = audioContentCreatorHashMap.get(authorName);
						authorAudioContents.add(i);
					} else {
						audioContentCreatorHashMap.put(authorName, new ArrayList<Integer>());
						ArrayList<Integer> authorAudioContents = audioContentCreatorHashMap.get(authorName);
						authorAudioContents.add(i);
					}
					
				}
			}

			// adds songs index based on genre to hash map
			genreHashMap = new HashMap<Song.Genre, ArrayList<Integer>>(); 
			for (int i = 0; i < contents.size(); i++) {
				String audioContentType = contents.get(i).getType();
				if (audioContentType.equals("SONG")) {
					Song songContent = (Song) contents.get(i);
					Song.Genre songGenre = songContent.getGenre();
					if (genreHashMap.get(songGenre) != null) {
						ArrayList<Integer> genreAudioContents = genreHashMap.get(songGenre);
						genreAudioContents.add(i);	
					} else {
						genreHashMap.put(songGenre, new ArrayList<Integer>());
						ArrayList<Integer> genreAudioContents = genreHashMap.get(songGenre);
						genreAudioContents.add(i);	
					}
					
				} 
			}		
		}
		
		public void printAudioContentByTitle(String title) {
			if (audioContentTitleHashMap.get(title) != null) {
				int audioContentIndex = audioContentTitleHashMap.get(title);
				System.out.print(audioContentIndex+1 + ". ");
				contents.get(audioContentIndex).printInfo();
			} else {
				 System.out.println("No matches for " + title);
			}
		}

		public void printAudioContentByArtist(String artist) {
			if (audioContentCreatorHashMap.get(artist) != null) {
				ArrayList<Integer> artistContentPrintIndexes = audioContentCreatorHashMap.get(artist);
				for (int i = 0; i < artistContentPrintIndexes.size();i++){
					int index = artistContentPrintIndexes.get(i);
					System.out.print(index+1  + ". ");
					contents.get(index).printInfo();
					System.out.println("\n");
				}
			} else {
				 System.out.println("No matches for " + artist);
			}
		}

		public void printAudioContentByGenre(Song.Genre genre) {
			if (genreHashMap.get(genre) != null) {
				ArrayList<Integer> genrePrintIndexes = genreHashMap.get(genre);
				for (int i = 0; i < genrePrintIndexes.size();i++){
					int index = genrePrintIndexes.get(i);
					System.out.print(index+1  + ". ");
					contents.get(index).printInfo();
					System.out.println("\n");
				}
			} else {
				 System.out.println("Does not exist");
			}
		}

		public ArrayList<AudioContent> getArtistContent(String artistName) {
			if (audioContentCreatorHashMap.get(artistName) != null) {
				ArrayList<Integer> artistSongIndexes = audioContentCreatorHashMap.get(artistName);
				ArrayList<AudioContent> artistSongs = new ArrayList<AudioContent>();
				for (int i = 0; i < artistSongIndexes.size(); i++) {
					int index = artistSongIndexes.get(i);
					AudioContent artistAudiocontent = contents.get(index);
					artistSongs.add(artistAudiocontent);
				}
				return artistSongs;
			} else {
				return null;
			}
		} 

		public ArrayList<AudioContent> getGenreContent(Song.Genre genre) {
			if (genreHashMap.get(genre) != null) {
				ArrayList<Integer> genreIndexes = genreHashMap.get(genre);
				ArrayList<AudioContent> genreSongs = new ArrayList<AudioContent>();
				for (int i = 0; i < genreIndexes.size(); i++) {
					int index = genreIndexes.get(i);
					AudioContent artistAudiocontent = contents.get(index);
					genreSongs.add(artistAudiocontent);
				}
				return genreSongs;
			} else {
				return null;
			}
		} 

		public ArrayList<AudioContent> getContent(int index, int indexTwo)
		{
			if ((index < 1 || index > contents.size()) || (indexTwo < 1 || indexTwo > contents.size()) || (index > indexTwo))
			{
				return null;
			}
			ArrayList<AudioContent> retrievedContents = new ArrayList<AudioContent>();
			while (index <= indexTwo) {
				retrievedContents.add(contents.get(index-1));
				index++;
			}
			return retrievedContents;
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.println();
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}
		
		private ArrayList<String> makeHPChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("The Riddle House");
			titles.add("The Scar");
			titles.add("The Invitation");
			titles.add("Back to The Burrow");
			return titles;
		}
		
		private ArrayList<String> makeHPChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("In which we learn of the mysterious murders\r\n"
					+ " in the Riddle House fifty years ago, \r\n"
					+ "how Frank Bryce was accused but released for lack of evidence, \r\n"
					+ "and how the Riddle House fell into disrepair. ");
			chapters.add("In which Harry awakens from a bad dream, \r\n"
					+ "his scar burning, we recap Harry's previous adventures, \r\n"
					+ "and he writes a letter to his godfather.");
			chapters.add("In which Dudley and the rest of the Dursleys are on a diet,\r\n"
					+ " and the Dursleys get letter from Mrs. Weasley inviting Harry to stay\r\n"
					+ " with her family and attend the World Quidditch Cup finals.");
			chapters.add("In which Harry awaits the arrival of the Weasleys, \r\n"
					+ "who come by Floo Powder and get trapped in the blocked-off fireplace\r\n"
					+ ", blast it open, send Fred and George after Harry's trunk,\r\n"
					+ " then Floo back to the Burrow. Just as Harry is about to leave, \r\n"
					+ "Dudley eats a magical toffee dropped by Fred and grows a huge purple tongue. ");
			return chapters;
		}
		
		private ArrayList<String> makeMDChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("Loomings.");
			titles.add("The Carpet-Bag.");
			titles.add("The Spouter-Inn.");
			return titles;
		}
		private ArrayList<String> makeMDChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("Call me Ishmael. Some years ago never mind how long precisely having little\r\n"
					+ " or no money in my purse, and nothing particular to interest me on shore,\r\n"
					+ " I thought I would sail about a little and see the watery part of the world.");
			chapters.add("stuffed a shirt or two into my old carpet-bag, tucked it under my arm, \r\n"
					+ "and started for Cape Horn and the Pacific. Quitting the good city of old Manhatto, \r\n"
					+ "I duly arrived in New Bedford. It was a Saturday night in December.");
			chapters.add("Entering that gable-ended Spouter-Inn, you found yourself in a wide, \r\n"
					+ "low, straggling entry with old-fashioned wainscots, \r\n"
					+ "reminding one of the bulwarks of some condemned old craft.");
			return chapters;
		}
		
		private ArrayList<String> makeSHChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("Prologue");
			titles.add("Chapter 1");
			titles.add("Chapter 2");
			titles.add("Chapter 3");
			return titles;
		}
		
		private ArrayList<String> makeSHChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("The gale tore at him and he felt its bite deep within\r\n"
					+ "and he knew that if they did not make landfall in three days they would all be dead");
			chapters.add("Blackthorne was suddenly awake. For a moment he thought he was dreaming\r\n"
					+ "because he was ashore and the room unbelieveable");
			chapters.add("The daimyo, Kasigi Yabu, Lord of Izu, wants to know who you are,\r\n"
					+ "where you come from, how ou got here, and what acts of piracy you have committed.");
			chapters.add("Yabu lay in the hot bath, more content, more confident than he had ever been in his life.");
			return chapters;
		}
		
		// Podcast Seasons
		/*
		private ArrayList<Season> makeSeasons()
		{
			ArrayList<Season> seasons = new ArrayList<Season>();
		  Season s1 = new Season();
		  s1.episodeTitles.add("Bay Blanket");
		  s1.episodeTitles.add("You Don't Want to Sleep Here");
		  s1.episodeTitles.add("The Gold Rush");
		  s1.episodeFiles.add("The Bay Blanket. These warm blankets are as iconic as Mariah Carey's \r\n"
		  		+ "lip-syncing, but some people believe they were used to spread\r\n"
		  		+ " smallpox and decimate entire Indigenous communities. \r\n"
		  		+ "We dive into the history of The Hudson's Bay Company and unpack the\r\n"
		  		+ " very complicated story of the iconic striped blanket.");
		  s1.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeFiles.add("here is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeLengths.add(31);
		  s1.episodeLengths.add(32);
		  s1.episodeLengths.add(45);
		  seasons.add(s1);
		  Season s2 = new Season();
		  s2.episodeTitles.add("Toronto vs Everyone");
		  s2.episodeTitles.add("Water");
		  s2.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s2.episodeFiles.add("Can the foundation of Canada be traced back to Indigenous trade routes?\r\n"
		  		+ " In this episode Falen and Leah take a trip across the Great Lakes, they talk corn\r\n"
		  		+ " and vampires, and discuss some big concerns currently facing Canada's water."); 
		  s2.episodeLengths.add(45);
		  s2.episodeLengths.add(50);
		 
		  seasons.add(s2);
		  return seasons;
		}
		*/
}
