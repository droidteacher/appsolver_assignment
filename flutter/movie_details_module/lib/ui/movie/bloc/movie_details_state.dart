import 'package:movie_details_module/data/remote/model.dart';

abstract class MovieDetailsState {}

class FetchingData extends MovieDetailsState {}

class GotMovieDetails extends MovieDetailsState {
  final MovieDetails details;
  final bool initialFavoriteState;

  GotMovieDetails({required this.details, required this.initialFavoriteState});
}

class HumanReadableMessage extends MovieDetailsState {
  final String message;
  final bool isProcessing;
  final bool isError;

  HumanReadableMessage({required this.message, this.isProcessing = true, this.isError = false});
}

class FavoriteCheckResult extends MovieDetailsState {
  final bool selected;

  FavoriteCheckResult({required this.selected});

}