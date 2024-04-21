package ru.webitmo.soundstats.statistics

import org.springframework.stereotype.Service
import ru.webitmo.soundstats.playlists.entities.Artist
import ru.webitmo.soundstats.playlists.entities.Track
import ru.webitmo.soundstats.spotify.SpotifyService
import ru.webitmo.soundstats.spotify.dto.ArtistDto
import ru.webitmo.soundstats.spotify.dto.SingleTrackFeaturesDto
import ru.webitmo.soundstats.spotify.dto.TrackDto
import ru.webitmo.soundstats.spotify.dto.TrackFeaturesDto

@Service
class StatisticsService(val spotifyService: SpotifyService) {
    fun getLikedArtists() : List<Artist> {
        return listOf(
            Artist("https://i.scdn.co/image/ab6761610000e5ebae7c78bd69ba795e7f9b7d04", "Battle Tapes", "2TdEIqWbLnZZHYRDWvVj67", ),
            Artist("https://i.scdn.co/image/ab6761610000e5ebc1fded2a185c54df399fe63a", "The Score", "2q3GG88dVwuQPF4FmySr9I"),
            Artist("https://i.scdn.co/image/ab67616d0000b27316052dae7e0f5404597980ec", "いとうかなこ", "2d12dVIZQZk9CKhEsezaoN"),
            Artist("https://i.scdn.co/image/ab67616d0000b273b72c78aa721beab0ddaaa106", "Joel Nielsen", "2VzNhLa3iGSwWttS4n1kbB"),
            Artist("https://i.scdn.co/image/ab6761610000e5eb97d68537c39dc8bcebbd9e6c", "Sabaton", "3o2dn2O0FCVsWDFSh8qxgG")
        )
    }

    fun getLikedTracks() : List<Track> {
        return listOf(
            Track("https://i.scdn.co/image/ab67616d0000b27316cf696d7085df82f77b59f5", "カミイロアワセ", "77spgc3VsjeFMi8ged5cOO", listOf("binaria").joinToString()),
            Track("https://i.scdn.co/image/ab67616d0000b273b7adc833cd0d61833462d082", "Control", "4EsS7kgS3ztITT00pH0KG6", listOf("Battle Tapes").joinToString()),
            Track("https://i.scdn.co/image/ab67616d0000b2732b606483bf61fdfba68ed4dc", "4:30", "0lO8ZTS4kNYa9eOSC1QYQi", listOf("Danger").joinToString()),
            Track("https://i.scdn.co/image/ab67616d0000b273bc8d2b3ac36be53be9a38fe1", "The Last Of The Real Ones", "6e13443Ve7RGcAUScTgYtl", listOf("Fall Out Boy").joinToString()),
            Track("https://i.scdn.co/image/ab67616d0000b2737abc5e6132d2d0868b51aef9", "Rush", "2xrW6sZRmahuSAQMxO4BmJ", listOf("The Score").joinToString())
        )
    }

    suspend fun getTopTracks() : List<TrackDto> {
        return spotifyService.getUserTopTracks().items
    }

    suspend fun getTopArtists() : List<ArtistDto> {
        return spotifyService.getUserTopArtists().items
    }

    suspend fun getTopGenres() : Map<String, Int> {
        val artists = spotifyService.getUserTopArtists().items
        return artists.map { it.genres }.flatten()
            .groupingBy { it }.eachCount()
            .toList().sortedByDescending { it.second }.toMap()
    }

    suspend fun getTopFeatures() : SingleTrackFeaturesDto {
        val tracks = spotifyService.getUserTopTracks().items
        val features = spotifyService.getTracksFeatures(tracks).data
        return SingleTrackFeaturesDto(
            features.map { it.acousticness }.average(),
            features.map { it.danceability }.average(),
            features.map { it.instrumentalness }.average(),
            features.map { it.liveness }.average(),
            features.map { it.loudness }.average(),
            features.map { it.speechiness }.average(),
            features.map { it.tempo }.average(),
            features.map { it.timeSignature }.average().toInt(),
            features.map { it.valence }.average(),
            features.map { it.energy }.average()
        )
    }
}