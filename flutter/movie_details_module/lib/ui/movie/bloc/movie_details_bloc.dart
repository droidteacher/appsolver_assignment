import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:movie_details_module/data/remote/model.dart';
import 'package:movie_details_module/data/remote/movie_datasource.dart';
import 'package:movie_details_module/ui/movie/bloc/movie_details_event.dart';
import 'package:movie_details_module/ui/movie/bloc/movie_details_state.dart';

class MovieDetailsBloc extends Bloc<MovieDetailsEvent, MovieDetailsState> {
  final MovieDatasource datasource;
  final MethodChannel platform;

  bool _isFavorite = false;
  int? _currentMovieId;

  MovieDetailsBloc({required this.datasource, required this.platform})
      : super(HumanReadableMessage(message: 'Fetching data...')) {
    on<PlatformChannelFailure>(_onFailRetrievingId);
    on<UserDidSelectMovie>(_onSelectMovie);
    on<ToggleFavorite>(_onToggleFavorite);

    _retrieveMovieId();
  }

  void _onSelectMovie(
      UserDidSelectMovie event, Emitter<MovieDetailsState> emit) async {
    _currentMovieId = event.movieId;
    _isFavorite = event.locallyFavorite;

    MovieDetails details = await datasource.fetchMovieDetails(event.movieId);

    emit(GotMovieDetails(
        details: details, initialFavoriteState: event.locallyFavorite));
  }

  void _onFailRetrievingId(
      PlatformChannelFailure event, Emitter<MovieDetailsState> emit) {
    emit(HumanReadableMessage(
        message: 'Can\'t get movie id selected',
        isProcessing: false,
        isError: true));
  }

  void _onToggleFavorite(
      ToggleFavorite event, Emitter<MovieDetailsState> emit) {
    debugPrint('5555 in Flutter !!!!!!!!!!! _onToggleFavorite !!!!!!!!!!!');
    _isFavorite = !_isFavorite;

    if (_currentMovieId != null) {
      platform.invokeMethod('toggleFavorite',
          {'movieId': _currentMovieId, 'isFavorite': _isFavorite});
    }

    emit(FavoriteCheckResult(selected: _isFavorite));
  }

  Future<void> _retrieveMovieId() async {
    try {
      Map payload = await platform.invokeMethod('getSelectedMovieId');
      debugPrint('5555 in Flutter !!!!!!!!!!! ::_retrieveMovideId, $payload');
      int movieId = payload['movieId'] as int;
      bool currentlyFavorite = payload['favorite'] as bool;
      add(UserDidSelectMovie(
          movieId: movieId, locallyFavorite: currentlyFavorite));
    } on PlatformException catch (e) {
      debugPrint(e.stacktrace);
      add(PlatformChannelFailure());
    }
  }
}
