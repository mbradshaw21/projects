import csv
import random
import re
import requests
import xml.etree.ElementTree as ET

#dictionary that has the song name as the key and the artist as the value
libraryDict = {}

#Initializes lists
songs = []
artists = []
betterArtists = []
newInfo = []

artistNames = []
specificIndices = []

chosenIndices = []

playlist = {}

def main():
	#parseXML('./Library.xml')
	getSongs()
	printLibrary()
	
	numSongs = input("\nHow many songs would you like in your playlist?	")
	
	try:
		numSongs = int(numSongs)
	except ValueError:
		print("Invalid Input: Please enter an int")
		return
	
	if(numSongs < 1 or numSongs > len(songs)):
		print("Invalid input: You do not have that many songs")
		return
	else:
		artistRequest = input("\nDo you want a specific artist for the playlist?\nType YES or NO	")
		songRequest = input("\nDo you want a specific song to be included in this playlist?\nType YES or NO	")
		
		if(artistRequest == "YES" and songRequest == "YES"):
			artistName = input("\nEnter the artist(s) you would like: ")
			if "," in artistName:
				artistNames = artistName.split(", ")
				specificArtist(artistNames)
			else:
				specificArtist(artistName)
			
			songName = input("\nEnter the songs you would like: ")
			if "," in songName:
				songNames = songName.split(", ")
				print(numSongs)
				numSongs = specificSong(songNames, numSongs)
			else:
				numSongs = specificSong(songName, numSongs)
			
			makeSpecificPlaylist(numSongs)
			printPlaylist()
			return
		
		elif(artistRequest == "YES" and songRequest == "NO"):
			artistName = input("\nEnter the artist(s) you would like: ")
			if "," in artistName:
				artistNames = artistName.split(", ")
				specificArtist(artistNames)
			else:
				specificArtist(artistName)
			
			makeSpecificPlaylist(numSongs)
			printPlaylist()
			return
		
		elif(artistRequest == "NO" and songRequest == "YES"):
			songName = input("\nEnter the songs you would like: ")
			if "," in songName:
				songNames = songName.split(", ")
				numSongs = specificSong(songNames, numSongs)
			else:
				numSongs = specificSong(songName, numSongs)
			
			makePlaylist(numSongs)
			printPlaylist()
			return
			
		elif(artistRequest == "NO" and songRequest == "NO"):
			makePlaylist(numSongs)
			printPlaylist()
			return
		else:
			print("Invalid Input: Please enter YES or NO for both answers")
			return			
		

#if you imported an entire iTunes library, it would be handled with this
def parseXML(xmlLibrary):
	tree = ET.parse(xmlLibrary)
    
	###################################################
	# Making a text file of everything in the library #
	###################################################
	dicts = tree.findall("/dict/dict/dict")
	for d in dicts:
		for key in d:
			f = open("newLibrary.txt", "a")
			if(key.text != None):
				f.write(key.text)
				f.write("\n")

#creates a dictionary from a music library		
def getSongs():

	#Opens and reads the txt file containing the music library
	with open("Music.txt", "rb") as f:
		content = f.read()
	
	#print(content)
	
	
	#Creates a list of every bit of information about the song after decoding it
	infoList = []
	infoList = re.split('\t+', content.decode('utf-16'))
	
	artistIndices = []
	
	i = 0
	for s in infoList:			#for each element in infoList
		s = s.splitlines()		#split the element at any \r or \n or both
		
		if(len(s) == 1):		#if the element was NOT split
			newInfo.append(s)
			i += 1
			
		if(len(s) == 2):		#if the element was split, the second element
			newInfo.append(s[0])
			i += 1
			newInfo.append(s[1])		
			i += 1
			artistIndices.append(i)
			songs.append(s[1])
	
	#Makes a list of lists (of length 1) of the artists
	for index in artistIndices:
		artists.append(newInfo[index])
	
	#Makes a list of artists as strings, rather than lists
	for listArtist in artists:
		for string in listArtist:
			betterArtists.append(string)
	
	#Creates the dictionary was key = song, value = artist
	for x in range(len(songs)):
		libraryDict[songs[x]] = betterArtists[x]
	
def printLibrary():
	print("Here is your library in alphabetical order:")
	for key in libraryDict:
		print("Song: " + key + " - Artist: " + libraryDict.get(key))
		
def makePlaylist(numSongs):
	possibleIndices = []
	
	#Creates a list of possible indices (number of songs in library [0, # of songs-1])
	for i in range(len(songs)):
		if(i not in chosenIndices):
			possibleIndices.append(i)
	
	#Picks a random index from the list of possible indices, then removes that choice
	#from the list of possible indices to avoid duplicates
	for x in range(numSongs):
		r = random.choice(possibleIndices)
		chosenIndices.append(r)
		possibleIndices.remove(r)
	
	#Creates the random playlist as a dictionary
	for index in chosenIndices:
		playlist[songs[index]] = betterArtists[index]

def makeSpecificPlaylist(numSongs):
	if(numSongs > len(specificIndices)):
		print("You do not have enough songs by this/these artist/artists")
		return
	
	#Picks a random index from the list of possible indices, then removes that choice
	#from the list of possible indices to avoid duplicates
	for x in range(numSongs):
		r = random.choice(specificIndices)
		chosenIndices.append(r)
		specificIndices.remove(r)
	
	#Creates the random playlist as a dictionary
	for index in chosenIndices:
		playlist[songs[index]] = betterArtists[index]
	
def printPlaylist():
	print("\nHere is your playlist:")
	for key in playlist:
		print("Song: " + key + " - Artist: " + playlist.get(key))
	print("\n")
	
def specificArtist(artistName):
	if(isinstance(artistName, list)):
		for target in artistName:
			index = 0
			for artist in betterArtists:
				if(target in artist):
					specificIndices.append(index)
				index += 1
	else:
		index = 0
		for artist in betterArtists:
			if(artist == artistName):
				specificIndices.append(index)
			index += 1

def specificSong(targetSong, numSongs):
	if(isinstance(targetSong, list)):
		for target in targetSong:
			index = 0
			for song in songs:
				if(target == song):
					chosenIndices.append(index)
					numSongs -= 1
				index += 1
	else:
		index = 0
		for song in songs:
			if(song == targetSong):
				chosenIndices.append(index)
				numSongs -= 1
			index += 1
			
	return numSongs
	
def exportPlaylist():
	return

main()
