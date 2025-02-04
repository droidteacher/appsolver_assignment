abstract class MovieDetailsEvent {}

class UserDidSelectMovie extends MovieDetailsEvent {
  final int movieId;
  final bool locallyFavorite;

  UserDidSelectMovie({required this.movieId, required this.locallyFavorite});
}

class PlatformChannelFailure extends MovieDetailsEvent {}
class ToggleFavorite extends MovieDetailsEvent {}