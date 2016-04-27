JMusicMan
=========
JMusicMan is a music manager written in Java. It's artist-oriented, this means that it prefers to organize the directories of your music along with this schema:

 artist/album name/xx - Song title
 
where xx is the track number, all accordingly to the ID3 tag. 

Initially JMusicMan was thought to manage and syncronize the music in your mobile device that supports USB mass storage. 
JMusicMan search for a blank file named ".is_audio_player" in the root directory of your mobile device, if it exists, JMusicMan "knows" that this is a device to manage; later search in the /Music folder tha database file "JMusicManLibrary.xml" which it will compared with the one stored in your Music folder of your PC,m determining which files are going to be copied and which files are going to be deleted. 

Actually, JMusicMan supports only mp3 and flac files and it is in an alpha version. 
